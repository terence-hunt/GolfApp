package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.PlayRound;
import com.example.terrystest.GolfCourseData.GolfCourse;
import com.example.terrystest.ScoreData.Score;
import android.view.View;
import android.widget.Spinner;
import android.widget.RelativeLayout;

public class ShotsInfo implements PlayRoundListenerInterface{

	Spinner shots;
	Spinner putts;
	PlayRoundMaster playRoundMaster;
	Score score;
	GolfCourse golfCourse;
	PlayRound playRound;

	public ShotsInfo(Spinner shots, Spinner putts, PlayRoundMaster playRoundMaster, Score score, GolfCourse golfCourse,
			PlayRound playRound, RelativeLayout layout){
		layout.setVisibility(View.VISIBLE);
		this.shots = shots;
		this.putts = putts;
		this.playRoundMaster = playRoundMaster;
		this.score = score;
		this.golfCourse = golfCourse;
		this.playRound = playRound;
		registerAsListener();
	}

	@Override
	public void update(int holeNumber) {
		//if the score does not exist yet then add the default values
		if(!score.exists(holeNumber)){
			score.addScoreForHole(holeNumber, golfCourse.getHole(holeNumber).getPar(), 2);
		}
		shots.setOnItemSelectedListener(null);
		putts.setOnItemSelectedListener(null);
		shots.setSelection((int)score.getHoleScore(holeNumber).getShots());
		putts.setSelection((int)score.getHoleScore(holeNumber).getPutts());
		shots.setOnItemSelectedListener(playRound);
		putts.setOnItemSelectedListener(playRound);
	}

	@Override
	public void registerAsListener() {
		playRoundMaster.registerListener(this);
	}

	@Override
	public void save(int holeNumber) {
		score.addScoreForHole(holeNumber, shots.getSelectedItemId(), putts.getSelectedItemId());
	}
}
