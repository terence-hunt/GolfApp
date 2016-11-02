package com.example.terrystest.GolfCourseData;

import android.database.Cursor;

public class GolfCourseFactory {

	public GolfCourse createGolfCourse(Cursor golfCourseCursor, Cursor holeCursor){
		golfCourseCursor.moveToFirst();
		GolfCourse golfCourse = new GolfCourse();
		golfCourse.setGolfCourseName(golfCourseCursor.getString(golfCourseCursor.getColumnIndex("name")));
		golfCourse.setGolfCourseAddress(golfCourseCursor.getString(golfCourseCursor.getColumnIndex("address")));


		while (holeCursor.moveToNext()){
			long holeNumber = holeCursor.getLong(holeCursor.getColumnIndex("hole_number"));
			long par = holeCursor.getLong(holeCursor.getColumnIndex("par"));
			long strokeIndex = holeCursor.getLong(holeCursor.getColumnIndex("stroke_index"));
			long WT = holeCursor.getLong(holeCursor.getColumnIndex("white_tee_yardage"));
			long YT = holeCursor.getLong(holeCursor.getColumnIndex("yellow_tee_yardage"));
			long BT = holeCursor.getLong(holeCursor.getColumnIndex("blue_tee_yardage"));
			long RT = holeCursor.getLong(holeCursor.getColumnIndex("red_tee_yardage"));
			golfCourse.createHole(holeNumber,par,strokeIndex, WT, YT, BT, RT);
		} 
		
		return golfCourse;
	}
}
