package com.example.terrystest.GolfRoundData;

import java.util.ArrayList;

import com.example.terrystest.GolfRoundData.GolfCourseData.GolfCourse;


public class Score {
	ArrayList<HoleScore> score;

	public Score(){
		score = new ArrayList<HoleScore>();
	}
	public HoleScore getScoreForHole(int holeNumber){
		return score.get(holeNumber-1);
	}
	
	public int getNumberOfScores(){
		return score.size();
	}

	public void addScoreForHole(long holeNumber, long shots, long putts) {
		if(exists(holeNumber)){
			score.remove((int)(holeNumber-1));
		}
		score.add(((int)(holeNumber-1)),new HoleScore(shots,putts));
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

	public HoleScore getHoleScore (long holeNumber){
		return score.get((int)holeNumber-1);
	}

	public long getGrossScore(GolfCourse golfCourse){
		long grossScore = 0;
		for(int i=1 ; i <= score.size() ; i++){
			grossScore += (getHoleScore(i).getShots() - golfCourse.getHole(i).getPar());
		}
		return grossScore;
	}
	
	public char getGIR(int holeNumber,GolfCourse course){
		try{
			if(course.getHole(holeNumber).getPar() -2 >= getHoleScore(holeNumber).getShots() - getHoleScore(holeNumber).getPutts() ){
				return 'Y';
			}
			else return 'N';
		}
		catch(Exception ex){
			return '-';
		}
	}
}
