package com.example.terrystest.PlayerData;

import android.database.Cursor;

public class PlayerFactory {
	
	public Player createPlayer(Cursor playerCursor) {
		playerCursor.moveToFirst();
		String name = playerCursor.getString(playerCursor.getColumnIndex("name"));
		long handicap = playerCursor.getLong(playerCursor.getColumnIndex("handicap"));
		String email =  playerCursor.getString(playerCursor.getColumnIndex("email"));
		return new Player(name, handicap,email );
	}
}
