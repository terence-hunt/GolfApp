package com.example.terrystest;

import com.example.terrystest.PlayRoundPackage.HoleInformation;
import com.example.terrystest.PlayRoundPackage.PlayRoundMaster;
import com.example.terrystest.PlayRoundPackage.ScoreInfo;
import com.example.terrystest.PlayRoundPackage.ShotDirectionBar;
import com.example.terrystest.PlayRoundPackage.ShotsInfo;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.Date;

import com.example.terrystest.GolfAppConfiguration.GolfAppConfigFactory;
import com.example.terrystest.GolfAppConfiguration.GolfAppConfigScoring;
import com.example.terrystest.GolfRoundData.PlayersScore;
import com.example.terrystest.GolfRoundData.Score;
import com.example.terrystest.GolfRoundData.GolfCourseData.GolfCourse;
import com.example.terrystest.GolfRoundData.GolfCourseData.GolfCourseFactory;
import com.example.terrystest.GolfRoundData.PlayerData.Player;
import com.example.terrystest.GolfRoundData.PlayerData.PlayerFactory;
import com.example.terrystest.GolfRoundData.GolfRound;

public class PlayRound extends Activity implements OnItemSelectedListener {

	Player player;
	long holeNumber = 1;
	//the following is what notifies the listeners that the hole number has changed.
	PlayRoundMaster playRoundMaster;
	GolfRound round;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_round);
		
		round = new GolfRound();
		playRoundMaster = new PlayRoundMaster();

		Bundle extras = getIntent().getExtras();
		Intent intent = getIntent();

		if (!(intent.hasExtra("PLAYER_ID") && intent.hasExtra("COURSE_ID"))){
			throw new RuntimeException("Player or course id not valid");
		}
		long playerID = extras.getLong("PLAYER_ID");
		long courseID = extras.getLong("COURSE_ID");

		PlayerDatabase playerDatabase = new PlayerDatabase(this);

		//create player object from the database
		PlayerFactory factory = new PlayerFactory();
		player = factory.createPlayer(playerDatabase.getPlayer(playerID));
		PlayersScore playersScore = new PlayersScore(player,new Score());
		round.setPlayersScore(playersScore);

		//create golf course object from the Database
		GolfCourseFactory gcFactory = new GolfCourseFactory();
		Cursor cursor1 = playerDatabase.getCourseInfo(courseID);
		Cursor cursor2 = playerDatabase.getHoleInfo(courseID);
		GolfCourse golfCourse = gcFactory.createGolfCourse(cursor1, cursor2);
		round.setGolfCourse(golfCourse);
		round.setStartTime(new Date());
		
		initToggleButtonBar();
		updateView(0);
	}

	private void updateView(int holeDelta) {
		GolfCourse golfCourse = round.getGolfCourse();
		holeNumber += holeDelta;

		Button previous = (Button)findViewById(R.id.button1);
		Button next = (Button)findViewById(R.id.button2);

		if(holeNumber == 1){
			previous.setEnabled(false);
		}
		else{
			previous.setEnabled(true);
		}
		if(holeNumber == golfCourse.getNumberOfHoles()){
			next.setText("End Round");
			next.setOnClickListener(endRound);
		}
		else{
			next.setOnClickListener(nextHole);
			next.setEnabled(true);
			next.setText("Next");
		}
		//TODO need to find a way to run this as a separate thread
		playRoundMaster.updateListeners((int)holeNumber);
	}

	private void save() {
		playRoundMaster.saveListeners((int)holeNumber);
	}

	private void saveAndMoveToNextHole(int deltaHole){
		save();
		updateView(deltaHole);
	}

	public void nextHole(View view){
		saveAndMoveToNextHole(1);
	}
	public void previousHole(View view){
		saveAndMoveToNextHole(-1);
	}

	private void initToggleButtonBar(){
		//initiates both the toggle button bar and the hole information bar
		//shots and putts input bar
		Spinner shots = (Spinner)findViewById(R.id.shotsSpinner);
		Spinner putts = (Spinner)findViewById(R.id.puttsSpinner);
		RelativeLayout layoutRel = (RelativeLayout)findViewById(R.id.shotInfo);
		new ShotsInfo(shots, putts, playRoundMaster, round,this,layoutRel);
		//tee shot direction input
		if(GolfAppConfigFactory.getGolfAppConfigScoring().getConfigurationAttribute(GolfAppConfigScoring.RECORD_TEE_SHOT_DIRECTION)){
			ToggleButton leftToggleButton = (ToggleButton) findViewById(R.id.leftToggleButton);
			ToggleButton straightToggleButton = (ToggleButton) findViewById(R.id.straightToggleButton);
			ToggleButton rightToggleButton = (ToggleButton) findViewById(R.id.rightToggleButton);
			LinearLayout layout = (LinearLayout)findViewById(R.id.buttonRow);
			new ShotDirectionBar(layout,leftToggleButton, straightToggleButton, rightToggleButton, playRoundMaster,round);
		}
		//hole Information panel
		TextView holeNumberTextView = (TextView)findViewById(R.id.holeNumber);
		TextView parTextView = (TextView)findViewById(R.id.par);
		TextView strokeIndexTextView = (TextView)findViewById(R.id.SI);
		new HoleInformation(holeNumberTextView,strokeIndexTextView,parTextView,round,playRoundMaster);
		//score information
		TextView nettScore = (TextView)findViewById(R.id.nettScore);
		TextView grossScore = (TextView)findViewById(R.id.GrossScore);
		TextView stapleford = (TextView)findViewById(R.id.staplefordScore);
		new ScoreInfo(nettScore, grossScore, stapleford, playRoundMaster, round);

		Button previous = (Button)findViewById(R.id.button1);
		previous.setOnClickListener(previousHole);
		Button next = (Button)findViewById(R.id.button2);
		next.setOnClickListener(nextHole);
	}

	@Override
	public void onBackPressed() {
		//make the backbutton to nothing to prevent losing the round part way through.
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		saveAndMoveToNextHole(0);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		//this should never be called.
		throw new RuntimeException("This method should never be called");
	}

	OnClickListener nextHole = new OnClickListener(){
		public void onClick(View v) {
			saveAndMoveToNextHole(+1);
		}
	};
	OnClickListener previousHole = new OnClickListener(){
		public void onClick(View v) {
			saveAndMoveToNextHole(-1);
		}
	};
	OnClickListener endRound = new OnClickListener(){
		public void onClick(View v) {
			saveAndMoveToNextHole(0);
			endRound();
		}
	};
	
	private void endRound() {;
		round.setEndTime(new Date());
        Intent intent = new Intent(this, ViewScoreCard.class);
        
        intent.putExtra("GolfRoundObject", round);
        startActivity(intent);
	}
}

