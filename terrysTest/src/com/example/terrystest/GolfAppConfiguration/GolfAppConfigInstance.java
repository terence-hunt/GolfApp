package com.example.terrystest.GolfAppConfiguration;

public class GolfAppConfigInstance {
	
	private static GolfAppConfigInstance golfAppConfiguration;
	boolean recordTeeShotDirection = true;
	boolean showRunningTotalOfScore;
	
	private GolfAppConfigInstance(){
		//TODO load configuration object from fileSystem
	}
	
	public static GolfAppConfigInstance getGolfAppConfigurationInstance(){
		if(golfAppConfiguration == null){
			golfAppConfiguration = new GolfAppConfigInstance();
		}
		return golfAppConfiguration;
	}
	
	public boolean getRecordTeeShotDirection(){
		return recordTeeShotDirection;
	}
	public void setRecordTeeShotDirection(boolean bool){
		this.recordTeeShotDirection = bool;
	}
}
