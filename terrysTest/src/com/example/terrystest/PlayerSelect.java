package com.example.terrystest;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PlayerSelect extends ListActivity {

	ArrayAdapter<String> arrayAdapter;
	long lastSelectedId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_select);

		PlayerDatabase mydb = new PlayerDatabase(this);

		Cursor cursor = mydb.getAllPlayersCursor();

		@SuppressWarnings("static-access")
		String[] columns = new String[] {mydb.PLAYERS_COLUMN_NAME, mydb.PLAYERS_COLUMN_HANDICAP};

		ListView obj = (ListView)findViewById(android.R.id.list);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.activity_text_view_for_list_view, cursor, columns, new int[] {R.id.name_entry,R.id.handicap_entry}, 0);

		this.setListAdapter(adapter);

		obj.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Bundle dataBundle = new Bundle();
				dataBundle.putLong("PLAYER_ID", id);
				Intent intent = new Intent(getApplicationContext(),CourseSelect.class);
				intent.putExtras(dataBundle);
				startActivity(intent);
			}

		});
		//this registers the list view menu for the long click
		registerForContextMenu(obj);

	}
	public void newPlayer(View view) {
		Intent intent = new Intent(this, NewPlayer.class);
		startActivity(intent);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Player Menu");
		menu.add("Edit");
		menu.add("Delete");

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		lastSelectedId = info.id;
	}

	public boolean onContextItemSelected(MenuItem item) {

		PlayerDatabase db = new PlayerDatabase(this);

		if(item.getTitle() == "Edit"){
			Intent newPlayerIntent = new Intent(getApplicationContext(),NewPlayer.class);
			Bundle dataBundle = new Bundle();
			dataBundle.putLong("PLAYER_ID", lastSelectedId);
			newPlayerIntent.putExtras(dataBundle);
			startActivity(newPlayerIntent);
		}
		else if (item.getTitle() == "Delete"){
			db.deletePlayer(lastSelectedId);
			Intent intent = new Intent(getApplicationContext(),PlayerSelect.class);
			startActivity(intent);
		}
		return true;
	}
}
