package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.GolfRoundData.GolfRound;
import com.example.terrystest.GolfRoundData.GolfCourseData.GolfCourse;

import android.widget.TextView;

public class HoleInformation implements PlayRoundListenerInterface{
	
	TextView holeNumberTextView;
	TextView holeSITextView;
	TextView holeParTextView;
	GolfRound round;
	PlayRoundMaster playRound;
	
	public HoleInformation(TextView holeNumber, TextView holeSI, TextView holePar, GolfRound round, PlayRoundMaster playRound){
		this.holeNumberTextView = holeNumber;
		this.holeSITextView = holeSI;
		this.holeParTextView = holePar;
		this.round = round;
		this.playRound = playRound;
		registerAsListener();
	}

	public void update(int holeNumber) {
		GolfCourse golfCourse = round.getGolfCourse();
		holeNumberTextView.setText("Hole Number: " + holeNumber);
		holeSITextView.setText("SI: " + golfCourse.getHole(holeNumber).getStrokeIndex());
		holeParTextView.setText("Par: " + golfCourse.getHole(holeNumber).getPar());	
	}

	@Override
	public void registerAsListener() {
		playRound.registerListener(this);
	}

	@Override
	public void save(int holeNumber) {
		//no information needs to be saved from the holeInfo
	}
}
