package com.example.terrystest.ScoreData;

import java.util.ArrayList;

public class Score {
	ArrayList<HoleScore> score;

	public Score(){
		score = new ArrayList<HoleScore>();
	}

	public void addScoreForHole(long holeNumber, long shots, long putts, String direction) {
		if(exists(holeNumber)){
			score.remove((int)(holeNumber-1));
		}
		score.add(((int)(holeNumber-1)),new HoleScore(shots,putts,direction));
	}

	public boolean exists(long holeNumber) {
		holeNumber--;
		if(holeNumber>=0 && holeNumber<score.size()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public HoleScore getHoleScore(long holeNumber){
		return score.get((int)holeNumber-1);
	}
}
