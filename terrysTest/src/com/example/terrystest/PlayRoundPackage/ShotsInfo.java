package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.PlayRound;
import com.example.terrystest.GolfRoundData.GolfRound;
import com.example.terrystest.GolfRoundData.Score;
import com.example.terrystest.GolfRoundData.GolfCourseData.GolfCourse;

import android.view.View;
import android.widget.Spinner;
import android.widget.RelativeLayout;

public class ShotsInfo implements PlayRoundListenerInterface{

	Spinner shots;
	Spinner putts;
	PlayRoundMaster playRoundMaster;
	PlayRound playRound;
	GolfRound round;

	public ShotsInfo(Spinner shots, Spinner putts, PlayRoundMaster playRoundMaster, GolfRound round,
			PlayRound playRound, RelativeLayout layout){
		layout.setVisibility(View.VISIBLE);
		this.shots = shots;
		this.putts = putts;
		this.playRoundMaster = playRoundMaster;
		this.playRound = playRound;
		this.round = round;
		registerAsListener();
	}

	@Override
	public void update(int holeNumber) {
		Score score = round.getPlayersScore(0).getScore();
		GolfCourse golfCourse = round.getGolfCourse();
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
		Score score = round.getPlayersScore(0).getScore();
		score.addScoreForHole(holeNumber, shots.getSelectedItemId(), putts.getSelectedItemId());
	}
}
