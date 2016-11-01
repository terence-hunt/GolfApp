package com.example.terrystest;

import GolfCourseData.GolfCourse;
import GolfCourseData.GolfCourseFactory;
import GolfCourseData.Hole;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewCourse extends Activity {

	int holeNumber = 0;
	GolfCourse golfCourse;
	long courseID = 0;

	TextView holeTitle;
	EditText editParTextView ;
	EditText editStrokeIndexTextView;
	EditText editWhiteTeeTextView;
	EditText editYellowTeeTextView;
	EditText editBlueTeeTextView;
	EditText editRedTeeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_course);

		holeTitle = (TextView) findViewById(R.id.holeNumber);
		editParTextView = (EditText)findViewById(R.id.editPar);
		editStrokeIndexTextView = (EditText)findViewById(R.id.editStrokeIndex);
		editWhiteTeeTextView = (EditText)findViewById(R.id.editWhiteYardage);
		editYellowTeeTextView = (EditText)findViewById(R.id.editYellowYardage);
		editBlueTeeTextView = (EditText)findViewById(R.id.editBlueYardage);
		editRedTeeTextView = (EditText)findViewById(R.id.editRedYardage);

		Bundle extras = getIntent().getExtras();
		Intent intent = getIntent();

		golfCourse = new GolfCourse();

		if (intent.hasExtra("COURSE_ID")){
			courseID = extras.getLong("COURSE_ID");
			loadGolfCourse();
		}
		refreshView();
	}

	private void loadGolfCourse() {
		PlayerDatabase mydb = new PlayerDatabase(this);
		Cursor cursor1 = mydb.getCourseInfo(courseID);
		Cursor cursor2 = mydb.getHoleInfo(courseID);
		
		GolfCourseFactory factory = new GolfCourseFactory();
		golfCourse = factory.createGolfCourse(cursor1, cursor2);
	}

	public void nextHole(View view){
		save();
		holeNumber++;
		refreshView();
	}
	public void previousHole(View view){
		save();
		holeNumber--;
		refreshView();
	}
	public void deleteHole(View view){
		golfCourse.deleteHole(holeNumber);
		if(holeNumber > golfCourse.getNumberOfHoles()){
			holeNumber = golfCourse.getNumberOfHoles();
		}
		refreshView();
	}
	public void addHole(View view){
		save();
		holeNumber++;
		//creates new hole with default values
		golfCourse.createHole(holeNumber, 4, 9, 0, 0, 0, 0);
		refreshView();
	}

	public void save(){
		if(holeNumber != 0){
			int par = Integer.parseInt(editParTextView.getText().toString());
			int SI = Integer.parseInt(editStrokeIndexTextView.getText().toString()); 
			int WT = Integer.parseInt(editWhiteTeeTextView.getText().toString());
			int YT = Integer.parseInt(editYellowTeeTextView.getText().toString());
			int BT = Integer.parseInt(editBlueTeeTextView.getText().toString());
			int RT = Integer.parseInt(editRedTeeTextView.getText().toString());

			golfCourse.updateHole(holeNumber, par, SI, WT, YT, BT, RT);
		}
		else if(holeNumber == 0){
			String name = editParTextView.getText().toString();
			String address = editStrokeIndexTextView.getText().toString();
			golfCourse.setGolfCourseName(name);
			golfCourse.setGolfCourseAddress(address);
		}
		else{
			throw new RuntimeException("HoleNumber not valid");
		}
	}

	public void saveCourse(View view){
		save();
		PlayerDatabase mydb = new PlayerDatabase(this);
		if(courseID == 0){
			mydb.saveGolfCourse(golfCourse);
		}
		else{
			mydb.updateGolfCourse(golfCourse,courseID);
		}
		Intent intent = new Intent(getApplicationContext(),CourseSelect.class);
		Bundle dataBundle = getIntent().getExtras();
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

	public void refreshView(){
		//assume this method is only called if the hole exists

		TextView par = (TextView) findViewById(R.id.par);
		TextView strokeIndex = (TextView) findViewById(R.id.strokeIndex);
		TextView whiteYardage = (TextView) findViewById(R.id.whiteYardage);
		TextView yellowYardage = (TextView) findViewById(R.id.yellowYardage);
		TextView blueYardage = (TextView) findViewById(R.id.blueYardage);
		TextView redYardage = (TextView) findViewById(R.id.redYardage);


		if(holeNumber != 0){
			par.setText("Par");
			strokeIndex.setText("Stroke Index");
			whiteYardage.setVisibility(View.VISIBLE);
			yellowYardage.setVisibility(View.VISIBLE);
			blueYardage.setVisibility(View.VISIBLE);
			redYardage.setVisibility(View.VISIBLE);
			editWhiteTeeTextView.setVisibility(View.VISIBLE);
			editYellowTeeTextView.setVisibility(View.VISIBLE);
			editBlueTeeTextView.setVisibility(View.VISIBLE);
			editRedTeeTextView.setVisibility(View.VISIBLE);
			editParTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
			editStrokeIndexTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
			holeTitle.setText("Hole: " + Integer.toString(holeNumber));
			Hole hole = golfCourse.getHole(holeNumber);
			editParTextView.setText(Integer.toString(hole.getPar()));
			editStrokeIndexTextView.setText(Integer.toString(hole.getStrokeIndex()));
			editWhiteTeeTextView.setText(Integer.toString(hole.getWhiteTeeYardage()));
			editYellowTeeTextView.setText(Integer.toString(hole.getYellowTeeYardage()));
			editBlueTeeTextView.setText(Integer.toString(hole.getBlueTeeYardage()));
			editRedTeeTextView.setText(Integer.toString(hole.getRedTeeYardage()));
		}
		if(holeNumber == 0){
			holeTitle.setText("This is the hole title page");
			par.setText("Course Name");
			strokeIndex.setText("Address");
			whiteYardage.setVisibility(View.GONE);
			yellowYardage.setVisibility(View.GONE);
			blueYardage.setVisibility(View.GONE);
			redYardage.setVisibility(View.GONE);
			editWhiteTeeTextView.setVisibility(View.GONE);
			editYellowTeeTextView.setVisibility(View.GONE);
			editBlueTeeTextView.setVisibility(View.GONE);
			editRedTeeTextView.setVisibility(View.GONE);
			editParTextView.setInputType(InputType.TYPE_CLASS_TEXT);
			editStrokeIndexTextView.setInputType(InputType.TYPE_CLASS_TEXT);
			editParTextView.setText(golfCourse.getGolfCourseName());
			editStrokeIndexTextView.setText(golfCourse.getGolfCourseAddress());
		}

		Button previous = (Button) findViewById(R.id.button1);
		Button next = (Button) findViewById(R.id.button4);
		Button delete = (Button) findViewById(R.id.button2);
		Button add = (Button) findViewById(R.id.button3);

		if(holeNumber == 0){
			previous.setEnabled(false);
			delete.setEnabled(false);
		}
		else{
			previous.setEnabled(true);
			delete.setEnabled(true);
		}
		if(holeNumber == golfCourse.getNumberOfHoles()){
			next.setEnabled(false);
		}
		else{
			next.setEnabled(true);
		}
		if(golfCourse.getNumberOfHoles() == 1){
			delete.setEnabled(false);
		}
		else{
			delete.setEnabled(true);
		}
		//maximum of 18 holes in a golf course.
		if(golfCourse.getNumberOfHoles() == 18){
			add.setEnabled(false);
		}
		else{
			add.setEnabled(true);
		}
	}
}

