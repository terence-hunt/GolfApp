package com.example.terrystest.ScoreData;

public class HoleScore {
	long shots;
	long putts;
	String teeShotDirection;

	public HoleScore(long shots, long putts, String direction) {
		this.shots = shots;
		this.putts = putts;
		if(direction.equals("Straight") || direction.equals("Left") || direction.equals("Right")){
			this.teeShotDirection = direction;
		}
		else{
			throw new RuntimeException("Invalid shot direction");
		}
	}
	
	public long getShots(){
		return shots;
	}
	public long getPutts(){
		return putts;
	}
	public String getTeeShotDirection(){
		return teeShotDirection;
	}
}
