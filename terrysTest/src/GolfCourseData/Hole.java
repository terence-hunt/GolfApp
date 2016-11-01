package GolfCourseData;

public class Hole {
	int strokeIndex;
	int par;
	int whiteTeeYardage;
	int yellowTeeYardage;
	int blueTeeYardage;
	int redTeeYardage;
	
	
	public Hole(int par, int strokeIndex, int whiteTeeYardage, int yellowTeeYardage, int blueTeeYardage, int redTeeYardage){
		this.par = par;
		this.strokeIndex = strokeIndex;
		this.whiteTeeYardage = whiteTeeYardage;
		this.yellowTeeYardage = yellowTeeYardage;
		this.blueTeeYardage = blueTeeYardage;
		this.redTeeYardage = redTeeYardage;
	}
	
	public int getPar(){
		return par;
	}

	public int getStrokeIndex() {
		return strokeIndex;
	}

	public int getWhiteTeeYardage() {
		return whiteTeeYardage;
	}

	public int getYellowTeeYardage() {
		return yellowTeeYardage;
	}
	public int getBlueTeeYardage() {
		return blueTeeYardage;
	}
	public int getRedTeeYardage() {
		return redTeeYardage;
	}
}
