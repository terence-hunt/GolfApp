package com.example.terrystest.PlayerData;

public class Player {
	String name;
	long handicap;
	String email;
	
	public Player(String name, long handicap, String email){
		this.name = name;
		this.handicap = handicap;
		this.email = email;
	}
	public long getHandicap(){
		return handicap;
	}

}
