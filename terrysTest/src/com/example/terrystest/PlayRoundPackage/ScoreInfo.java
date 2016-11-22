package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.GolfRoundData.GolfRound;
import com.example.terrystest.GolfRoundData.Score;
import com.example.terrystest.GolfRoundData.GolfCourseData.GolfCourse;

import android.widget.TextView;

public class ScoreInfo implements PlayRoundListenerInterface {

	TextView nettScore;
	TextView grossScore;
	TextView staplefordScore;
	PlayRoundMaster playRoundMaster;
	GolfRound round;


	public ScoreInfo(TextView nettScore, TextView grossScore, TextView staplefordScore, PlayRoundMaster playRoundMaster, GolfRound round){
		this.nettScore = nettScore;
		this.grossScore = grossScore;
		this.staplefordScore = staplefordScore;
		this.playRoundMaster = playRoundMaster;
		this.round = round;
		registerAsListener();
	}

	@Override
	public void update(int holeNumber) {
		String tmp = Long.toString(round.getPlayersScore(0).getScore().getGrossScore(round.getGolfCourse()));
		grossScore.setText("Gross: " + tmp);
		tmp = Integer.toString(round.getNetScoreForPlayer(0));
		nettScore.setText("Nett: " + tmp);
		staplefordScore.setText("SF: " + Long.toString(round.getStaplefordScoreForPlayer(0)));
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
