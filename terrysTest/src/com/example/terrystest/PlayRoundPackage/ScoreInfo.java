package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.GolfCourseData.GolfCourse;
import com.example.terrystest.ScoreData.Score;

import android.widget.TextView;

public class ScoreInfo implements PlayRoundListenerInterface {

	TextView nettScore;
	TextView grossScore;
	TextView staplefordScore;
	PlayRoundMaster playRoundMaster;
	Score score;
	GolfCourse golfCourse;
	int playerHandicap;

	public ScoreInfo(TextView nettScore, TextView grossScore, TextView staplefordScore, PlayRoundMaster playRoundMaster, Score score,
			GolfCourse golfCourse, int playerHandicap){
		this.nettScore = nettScore;
		this.grossScore = grossScore;
		this.staplefordScore = staplefordScore;
		this.playRoundMaster = playRoundMaster;
		this.score = score;
		this.golfCourse = golfCourse;
		this.playerHandicap = playerHandicap;
		registerAsListener();
	}

	@Override
	public void update(int holeNumber) {
		grossScore.setText("Gross: " + Long.toString(score.getGrossScore(golfCourse)));
		nettScore.setText("Nett: " + Long.toString(score.getNettScore(golfCourse, playerHandicap)));
		staplefordScore.setText("SF: " + Long.toString(score.getStaplefordScore(golfCourse, playerHandicap)));
	}

	@Override
	public void registerAsListener() {
		playRoundMaster.registerListener(this);
	}

	@Override
	public void save(int holeNumber) {
		// nothing needs to be saved here.
	}
}
