package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.GolfRoundData.GolfRound;
import com.example.terrystest.GolfRoundData.Score;
import com.example.terrystest.PlayRoundPackage.ShotDirection.ShotDirectionState;
import com.example.terrystest.PlayRoundPackage.ShotDirection.ShotDirectionStateLeft;
import com.example.terrystest.PlayRoundPackage.ShotDirection.ShotDirectionStateRight;
import com.example.terrystest.PlayRoundPackage.ShotDirection.ShotDirectionStateStraight;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class ShotDirectionBar implements PlayRoundListenerInterface {

	ToggleButton leftTB;
	ToggleButton straightTB;
	ToggleButton rightTB;
	PlayRoundMaster playRoundMaster;
	GolfRound round;
	ShotDirectionStateLeft leftState;
	ShotDirectionStateRight rightState;
	ShotDirectionStateStraight straightState;
	ShotDirectionState shotDirectionState;

	public ShotDirectionBar(LinearLayout layout,ToggleButton left, ToggleButton straight, ToggleButton right, PlayRoundMaster playRoundMaster, GolfRound round){
		this.leftTB = left;
		this.straightTB = straight;
		this.rightTB = right;
		this.playRoundMaster = playRoundMaster;
		this.round = round;
		this.leftState = new ShotDirectionStateLeft(leftTB, straightTB, rightTB,listener);
		this.straightState = new ShotDirectionStateStraight(leftTB, straightTB, rightTB,listener);
		this.rightState = new ShotDirectionStateRight(leftTB, straightTB, rightTB,listener);
		registerAsListener();
		layout.setVisibility(View.VISIBLE);
	}

	@Override
	public void update(int holeNumber) {
		Score score = round.getPlayersScore(0).getScore();
		String direction =  score.getHoleScore(holeNumber).getTeeShotDirection();
		if(direction == null || direction.equals("Straight")){
			shotDirectionState = straightState;
		}
		else if(direction.equals("Left")){
			shotDirectionState = leftState;
		}
		else if (direction.equals("Right")){
			shotDirectionState = rightState;
		}
		else {
			throw new RuntimeException("Invalid shot direction");
		}
		shotDirectionState.update();
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
			//if it is changing to checked then isChecked = true
			if(buttonView == leftTB){
				shotDirectionState = leftState;
			}
			else if (buttonView == straightTB){
				shotDirectionState = straightState;
			}
			else if(buttonView == rightTB){
				shotDirectionState = rightState;
			}
			shotDirectionState.update();
		}
	};

	@Override
	public void registerAsListener() {
		playRoundMaster.registerListener(this);
	}

	@Override
	public void save(int holeNumber) {
		Score score = round.getPlayersScore(0).getScore();
		score.getScoreForHole(holeNumber).addShotDirection(shotDirectionState.getDirection());
	}
}
