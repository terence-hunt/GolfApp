package com.example.terrystest.ScoreData;

public class HoleScore {
	long shots;
	long putts;
	String teeShotDirection;

	public HoleScore(long shots, long putts, String direction) {
		this.shots = shots;
		this.putts = putts;
		this.teeShotDirection = directionValidator(direction);

	}
	
	public HoleScore(long shots, long putts) {
		this.shots = shots;
		this.putts = putts;
	}
	
	public void addShotDirection(String teeShotDirection){
		this.teeShotDirection = directionValidator(teeShotDirection);
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
	
	public String directionValidator(String direction){
		if(direction.equals("Straight") || direction.equals("Left") || direction.equals("Right")){
			this.teeShotDirection = direction;
			return direction;
		}
		else{
			throw new RuntimeException("Invalid shot direction");
		}
	}
}
