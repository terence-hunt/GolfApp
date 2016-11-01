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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CourseSelect extends ListActivity {
	
	long lastSelectedId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_select);
		
		PlayerDatabase mydb = new PlayerDatabase(this);
		Cursor cursor = mydb.getAllGolfCourses();
		String[] columns = new String[] {"name", "address"};

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.activity_text_view_for_list_view, cursor, columns, new int[] {R.id.name_entry,R.id.handicap_entry}, 0);
		ListView listView = (ListView)findViewById(android.R.id.list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				lastSelectedId = id;
				Intent intent = new Intent(getApplicationContext(),PlayRound.class);
				Bundle dataBundle = getIntent().getExtras();
				dataBundle.putLong("COURSE_ID", id);
				intent.putExtras(dataBundle);
				startActivity(intent);
			}
		});
		registerForContextMenu(listView);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		lastSelectedId = info.id;
		menu.setHeaderTitle("Player Menu");
		menu.add("Edit");
		menu.add("Delete");
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		PlayerDatabase db = new PlayerDatabase(this);
		Intent intent = null;
		Bundle dataBundle = getIntent().getExtras();

		if(item.getTitle() == "Edit"){
			intent = new Intent(getApplicationContext(),NewCourse.class);
			dataBundle.putLong("COURSE_ID", lastSelectedId);	
		}
		else if (item.getTitle() == "Delete"){
			db.deleteCourse(lastSelectedId);
			intent = new Intent(getApplicationContext(),CourseSelect.class);
		}
		intent.putExtras(dataBundle);
		startActivity(intent);
		return true;
	}
	
	public void newCourse(View view){
		Intent intent = new Intent(getApplicationContext(),NewCourse.class);
		startActivity(intent);
	}
}
