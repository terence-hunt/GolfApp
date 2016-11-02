package com.example.terrystest.ScoreData;

import java.util.ArrayList;
import java.util.Iterator;

import GolfCourseData.GolfCourse;

public class Score {
	ArrayList<HoleScore> score;

	public Score(){
		score = new ArrayList<HoleScore>();
	}

	public void addScoreForHole(long holeNumber, long shots, long putts) {
		if(exists(holeNumber)){
			score.remove((int)(holeNumber-1));
		}
		score.add(((int)(holeNumber-1)),new HoleScore(shots,putts));
	}

	public void updateShotDirection(long holeNumber, String direction){
		score.get((int)holeNumber-1).addShotDirection(direction);
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

	public long getGrossScore(GolfCourse golfCourse){
		long grossScore = 0;
		for(int i=1 ; i <= score.size() ; i++){
			grossScore += (getHoleScore(i).getShots() - golfCourse.getHole(i).getPar());
		}
		return grossScore;
	}

	public long getNettScore(GolfCourse golfCourse, int playerHandicap){
		long nettScore = 0;
		int quotant = Math.round(playerHandicap) / 18;
		int remainder = Math.round(playerHandicap) % 18;
		for(int i=1 ; i <= score.size() ; i++){
			nettScore += (getHoleScore(i).getShots() - golfCourse.getHole(i).getPar());
			nettScore -= quotant;
			if (remainder >= golfCourse.getHole(i).getStrokeIndex()){
				nettScore--;
			}
		}
		return nettScore;
	}
	public int getStaplefordScore(GolfCourse golfCourse, int playerHandicap){
		int staplefordScore = 0;
		int quotant = Math.round(playerHandicap) / 18;
		int remainder = Math.round(playerHandicap) % 18;
		for(int i=1 ; i <= score.size() ; i++){
			if((golfCourse.getHole(i).getPar() - getHoleScore(i).getShots()+2+quotant+remainder) > 0){
				staplefordScore += (golfCourse.getHole(i).getPar() - getHoleScore(i).getShots()+2+quotant);
				if(remainder >= golfCourse.getHole(i).getStrokeIndex()){
					staplefordScore++;
				}
			}
		}

		return staplefordScore;
	}
}
