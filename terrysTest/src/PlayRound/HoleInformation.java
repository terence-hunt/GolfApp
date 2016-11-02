package PlayRound;


import GolfCourseData.GolfCourse;
import android.widget.TextView;

public class HoleInformation implements PlayRoundListenerInterface{
	
	TextView holeNumberTextView;
	TextView holeSITextView;
	TextView holeParTextView;
	GolfCourse golfCourse;
	PlayRoundMaster playRound;
	
	public HoleInformation(TextView holeNumber, TextView holeSI, TextView holePar, GolfCourse golfCourse, PlayRoundMaster playRound){
		this.holeNumberTextView = holeNumber;
		this.holeSITextView = holeSI;
		this.holeParTextView = holePar;
		this.golfCourse = golfCourse;
		this.playRound = playRound;
		registerAsListener();
	}

	public void update(int holeNumber) {
		holeNumberTextView.setText("Hole Number: " + holeNumber);
		holeSITextView.setText("SI: " + golfCourse.getHole(holeNumber).getStrokeIndex());
		holeParTextView.setText("Par: " + golfCourse.getHole(holeNumber).getPar());	
	}

	@Override
	public void registerAsListener() {
		playRound.registerListener(this);
	}

	@Override
	public void save(int holeNumber) {
		//no information needs to be saved from the holeInfo
	}
}
