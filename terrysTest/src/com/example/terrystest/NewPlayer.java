package com.example.terrystest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewPlayer extends Activity {

	long playerID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_player);

		Bundle extras = getIntent().getExtras();

		Intent intent = getIntent();

		if (intent.hasExtra("PLAYER_ID")){
			playerID = extras.getLong("PLAYER_ID");

			//if playerid is 0 it means there is no record of this player in the database.
			if( extras.getLong("PLAYER_ID") != 0L){
				TextView name = ((TextView) findViewById(R.id.editTextName));
				TextView email = ((TextView) findViewById(R.id.editTextEmail));
				TextView handicap = ((TextView) findViewById(R.id.editTextHandicap));
				PlayerDatabase playerDatabase = new PlayerDatabase(this);

				Cursor cursor =playerDatabase.getData(playerID);
				if (cursor != null){
					cursor.moveToFirst();
					String emailString = cursor.getString(cursor.getColumnIndexOrThrow("email"));
					String nameString = cursor.getString(cursor.getColumnIndexOrThrow("name"));
					String handicapString = cursor.getString(cursor.getColumnIndexOrThrow("handicap"));

					name.setText(nameString);
					email.setText(emailString);
					handicap.setText(handicapString);
				}	
			}		
		}
	}

	public void run(View view){
		PlayerDatabase playerDatabase = new PlayerDatabase(this);
		String name = ((TextView) findViewById(R.id.editTextName)).getText().toString();
		String email = ((TextView) findViewById(R.id.editTextEmail)).getText().toString();
		int handicap = Integer.parseInt(((TextView) findViewById(R.id.editTextHandicap)).getText().toString());
		if(playerID == 0){
			playerDatabase.addPlayer(name, handicap, email);
		}
		else{
			playerDatabase.updatePlayer(playerID, name, handicap, email);
		}

		Intent intent = new Intent(getApplicationContext(),PlayerSelect.class);
		startActivity(intent);
	}
}
