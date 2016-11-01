package com.example.terrystest;

import java.util.ArrayList;
import java.util.Iterator;

import GolfCourseData.GolfCourse;
import GolfCourseData.Hole;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerDatabase extends SQLiteOpenHelper  {

	public static final String DATABASE_NAME = "playerDatabase.db";
	public static final String PLAYERDATABASE_TABLE_NAME = "players";
	public static final String _id = "_id"; 
	public static final String PLAYERS_COLUMN_NAME = "name";
	public static final String PLAYERS_COLUMN_HANDICAP = "handicap";
	public static final String PLAYERS_COLUMN_EMAIL = "email";


	public PlayerDatabase(Context context) {
		super(context,DATABASE_NAME,null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table players (_id integer primary key, name text, handicap integer, email text)");
		db.execSQL("insert into players values(1,'Terence Hunt',15,'t.a.hunt89@gmail.com')");

		db.execSQL("create table courses (_id integer primary key, name text, address text)");
		db.execSQL("insert into courses values(1,'St Ives','Hunts')");

		db.execSQL("create table holes ("
				+ "course_id integer, "
				+ "hole_number integer, "
				+ "stroke_index integer, "
				+ "par integer, "
				+ "white_tee_yardage integer, "
				+ "yellow_tee_yardage integer, "
				+ "blue_tee_yardage integer, "
				+ "red_tee_yardage, "
				+ "primary key(course_id,hole_number)"
				+ ")");

		db.execSQL("insert into holes (course_id,hole_number,stroke_index,par,white_tee_yardage,yellow_tee_yardage,"
				+ "blue_tee_yardage,red_tee_yardage) values (1,1,18,4,400,300,200,100)");
		db.execSQL("insert into holes (course_id,hole_number,stroke_index,par,white_tee_yardage,yellow_tee_yardage,"
				+ "blue_tee_yardage,red_tee_yardage) values (1,2,15,5,500,400,300,200)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS players");
		db.execSQL("DROP TABLE IF EXISTS courses");
	}
	public boolean addPlayer( String name, int handicap, String email){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("handicap", handicap);
		contentValues.put("email", email);
		db.insert(PLAYERDATABASE_TABLE_NAME,null,contentValues);
		return true;
	}

	public Cursor getData(long id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from players where _id="+id+"", null );
		return res;
	}

	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, PLAYERDATABASE_TABLE_NAME);
		return numRows;
	}
	public boolean updatePlayer(long id, String name, int handicap, String email){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("handicap", handicap);
		contentValues.put("email", email);
		db.update("players", contentValues, "_id = ? ", new String[] { Long.toString(id) } );
		return true;
	}
	public void deletePlayer (long id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("players","_id = ? ", new String[] { Long.toString(id) });
	}
	public ArrayList<String> getAllPlayers()
	{
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from players", null );
		res.moveToFirst();

		while(res.isAfterLast() == false){
			array_list.add(res.getString(res.getColumnIndex(PLAYERS_COLUMN_NAME)));
			res.moveToNext();
		}
		return array_list;
	}

	public Cursor getAllPlayersCursor()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from " + PLAYERDATABASE_TABLE_NAME, null );
		return res;
	}


	public Cursor getAllGolfCourses(){

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from courses", null );
		return res;
	}

	public Boolean saveGolfCourse(GolfCourse golfCourse) {
		Iterator<Hole> holeIterator = golfCourse.getHoleIterator();
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues2 = new ContentValues();
		contentValues2.put("name",golfCourse.getGolfCourseName());
		contentValues2.put("address", golfCourse.getGolfCourseAddress());
		long courseID = db.insert("courses", null, contentValues2);

		int holeNumber=1;
		while (holeIterator.hasNext()){
			Hole hole = holeIterator.next();
			ContentValues contentValues = new ContentValues();
			contentValues.put("course_id", courseID);
			contentValues.put("hole_number", holeNumber);
			contentValues.put("par", hole.getPar());
			contentValues.put("white_tee_yardage", hole.getWhiteTeeYardage());
			contentValues.put("yellow_tee_yardage", hole.getYellowTeeYardage());
			contentValues.put("blue_tee_yardage", hole.getBlueTeeYardage());
			contentValues.put("red_tee_yardage", hole.getRedTeeYardage());
			db.insert("holes",null,contentValues);
			holeNumber++;
		}
		return true;
	}
	public Cursor getPlayer(long playerID){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from players where _id="+playerID+"", null );
		return res;
	}

	public void deleteCourse(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("courses","_id = ? ", new String[] { Long.toString(id) });
		db.delete("holes","course_id = ? ", new String[] { Long.toString(id) });
	}

	public void updateGolfCourse(GolfCourse golfCourse, long courseID) {
		deleteCourse(courseID);
		saveGolfCourse(golfCourse,courseID);
	}

	private boolean saveGolfCourse(GolfCourse golfCourse, long courseID) {
		Iterator<Hole> holeIterator = golfCourse.getHoleIterator();
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues2 = new ContentValues();
		contentValues2.put("_id", courseID);
		contentValues2.put("name",golfCourse.getGolfCourseName());
		contentValues2.put("address", golfCourse.getGolfCourseAddress());
		db.insert("courses", null, contentValues2);

		int holeNumber=1;
		while (holeIterator.hasNext()){
			Hole hole = holeIterator.next();
			ContentValues contentValues = new ContentValues();
			contentValues.put("course_id", courseID);
			contentValues.put("hole_number", holeNumber);
			contentValues.put("par", hole.getPar());
			contentValues.put("stroke_index", hole.getStrokeIndex());
			contentValues.put("white_tee_yardage", hole.getWhiteTeeYardage());
			contentValues.put("yellow_tee_yardage", hole.getYellowTeeYardage());
			contentValues.put("blue_tee_yardage", hole.getBlueTeeYardage());
			contentValues.put("red_tee_yardage", hole.getRedTeeYardage());
			db.insert("holes",null,contentValues);
			holeNumber++;
		}
		return true;
		
	}
	
	public Cursor getCourseInfo(long courseID){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( "select * from courses where _id = "+courseID+"", null );
		return cursor;
	}
	public Cursor getHoleInfo(long courseID){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( "select * from holes where course_id = "+courseID+" order by hole_number", null );
		return cursor;
	}
}