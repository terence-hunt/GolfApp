package com.example.terrystest;

import com.example.terrystest.GolfCourseData.GolfCourse;
import com.example.terrystest.GolfCourseData.GolfCourseFactory;
import com.example.terrystest.PlayRoundPackage.HoleInformation;
import com.example.terrystest.PlayRoundPackage.PlayRoundMaster;
import com.example.terrystest.PlayRoundPackage.ScoreInfo;
import com.example.terrystest.PlayRoundPackage.ShotDirection;
import com.example.terrystest.PlayerData.Player;
import com.example.terrystest.PlayerData.PlayerFactory;
import com.example.terrystest.ScoreData.HoleScore;
import com.example.terrystest.ScoreData.Score;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PlayRound extends Activity implements OnItemSelectedListener {

	Spinner shotsSpinner;
	Spinner puttsSpinner;

	GolfCourse golfCourse;
	Player player;
	Score score;
	long holeNumber;
	//the following is what notifies the listeners that the hole number has changed.
	PlayRoundMaster playRoundMaster;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_round);

		score = new Score();
		playRoundMaster = new PlayRoundMaster();
		
		shotsSpinner = (Spinner)findViewById(R.id.shotsSpinner);
		puttsSpinner = (Spinner)findViewById(R.id.puttsSpinner);
		
		shotsSpinner.setOnItemSelectedListener(this);
		puttsSpinner.setOnItemSelectedListener(this);

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

		//create golf course object from the Database
		GolfCourseFactory gcFactory = new GolfCourseFactory();
		Cursor cursor1 = playerDatabase.getCourseInfo(courseID);
		Cursor cursor2 = playerDatabase.getHoleInfo(courseID);
		golfCourse = gcFactory.createGolfCourse(cursor1, cursor2);
		
		initToggleButtonBar();
		holeNumber=1;
		updateView(0);
	}

	private void updateView(int holeDelta) {
		holeNumber += holeDelta;
		if(!score.exists(holeNumber)){
			//if a score for this hole does not exist yet put in default values
			score.addScoreForHole(holeNumber, golfCourse.getHole((int)holeNumber).getPar(), 2);
		}

		HoleScore holeScore = score.getHoleScore(holeNumber);
		shotsSpinner.setSelection((int)holeScore.getShots());
		puttsSpinner.setSelection((int)holeScore.getPutts());

		Button previous = (Button)findViewById(R.id.button1);
		Button next = (Button)findViewById(R.id.button2);

		if(holeNumber == 1){
			previous.setEnabled(false);
		}
		else{
			previous.setEnabled(true);
		}
		if(holeNumber == golfCourse.getNumberOfHoles()){
			next.setEnabled(false);
		}
		else{
			next.setEnabled(true);
		}
		//TODO need to find a way to run this as a separate thread
		playRoundMaster.updateListeners((int)holeNumber);
	}

	private void save() {
		long shots = Integer.parseInt(shotsSpinner.getSelectedItem().toString());
		long putts = Integer.parseInt(puttsSpinner.getSelectedItem().toString());
		score.addScoreForHole(holeNumber,shots,putts);
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
		ToggleButton leftToggleButton = (ToggleButton) findViewById(R.id.leftToggleButton);
		ToggleButton straightToggleButton = (ToggleButton) findViewById(R.id.straightToggleButton);
		ToggleButton rightToggleButton = (ToggleButton) findViewById(R.id.rightToggleButton);
		LinearLayout layout = (LinearLayout)findViewById(R.id.buttonRow);
		new ShotDirection(layout,leftToggleButton, straightToggleButton, rightToggleButton, playRoundMaster, score);
		
		TextView holeNumberTextView = (TextView)findViewById(R.id.holeNumber);
		TextView parTextView = (TextView)findViewById(R.id.par);
		TextView strokeIndexTextView = (TextView)findViewById(R.id.SI);
		new HoleInformation(holeNumberTextView,strokeIndexTextView,parTextView,golfCourse,playRoundMaster);
		
		TextView nettScore = (TextView)findViewById(R.id.nettScore);
		TextView grossScore = (TextView)findViewById(R.id.GrossScore);
		TextView stapleford = (TextView)findViewById(R.id.staplefordScore);
		new ScoreInfo(nettScore, grossScore, stapleford, playRoundMaster, score, golfCourse, (int)player.getHandicap());
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		saveAndMoveToNextHole(0);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}

