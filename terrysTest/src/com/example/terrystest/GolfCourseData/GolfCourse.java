package com.example.terrystest.GolfCourseData;

import java.util.ArrayList;
import java.util.Iterator;

public class GolfCourse {

	
	ArrayList<Hole> holes;
	String name = "Golf Course Name";
	String address = "Address";
			
	
	public GolfCourse(){
		holes = new ArrayList<Hole>();
	}
	public void createHole(int holeNumber,int par, int strokeIndex, int WT,int YT,int BT,int RT){
		Hole hole = new Hole(par,strokeIndex,WT,YT,BT,RT);
		holes.add(holeNumber-1, hole);
	}
	
	public void createHole(long holeNumber,long par,long strokeIndex, long WT,long YT,long BT,long RT){
		int holeNumber1 = (int)holeNumber;
		int strokeIndex1 = (int)strokeIndex;
		int par10 = (int) par;
		int WT1 = (int) WT;
		int YT1 = (int) YT;
		int BT1 = (int) BT;
		int RT1 = (int) RT;
		createHole(holeNumber1,par10,strokeIndex1, WT1, YT1, BT1, RT1);
	}
	
	public Hole getHole(int holeNumber){
		return holes.get(holeNumber - 1); 
	}
	public void deleteHole(int holeNumber){
		holes.remove(holeNumber - 1);
	}
	public void updateHole(int holeNumber,int par, int strokeIndex, int WT,int YT,int BT,int RT){
		holes.remove(holeNumber - 1);
		createHole(holeNumber,par,strokeIndex,WT,YT,BT,RT);
	}
	public int getNumberOfHoles(){
		return holes.size();
	}
	public Iterator<Hole> getHoleIterator(){
		return holes.iterator();
	}
	public void setGolfCourseName(String name){
		this.name = name;
	}
	public String getGolfCourseName(){
		return name;
	}
	public void setGolfCourseAddress(String address){
		this.address = address;
	}
	public String getGolfCourseAddress(){
		return address;
	}
}
