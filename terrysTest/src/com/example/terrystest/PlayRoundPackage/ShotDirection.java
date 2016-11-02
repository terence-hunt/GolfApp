package com.example.terrystest.PlayRoundPackage;

import com.example.terrystest.ScoreData.Score;
import com.example.terrystest.ShotDirection.ShotDirectionState;
import com.example.terrystest.ShotDirection.ShotDirectionStateLeft;
import com.example.terrystest.ShotDirection.ShotDirectionStateRight;
import com.example.terrystest.ShotDirection.ShotDirectionStateStraight;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class ShotDirection implements PlayRoundListenerInterface {
	
	ToggleButton leftTB;
	ToggleButton straightTB;
	ToggleButton rightTB;
	PlayRoundMaster playRoundMaster;
	Score score;
	ShotDirectionStateLeft leftState;
	ShotDirectionStateRight rightState;
	ShotDirectionStateStraight straightState;
	ShotDirectionState shotDirectionState;
 
	public ShotDirection(LinearLayout layout,ToggleButton left, ToggleButton straight, ToggleButton right, PlayRoundMaster playRoundMaster, Score score){
		this.leftTB = left;
		this.straightTB = straight;
		this.rightTB = right;
		this.playRoundMaster = playRoundMaster;
		this.score = score;
		this.leftState = new ShotDirectionStateLeft(leftTB, straightTB, rightTB);
		this.straightState = new ShotDirectionStateStraight(leftTB, straightTB, rightTB);
		this.rightState = new ShotDirectionStateRight(leftTB, straightTB, rightTB);
		registerAsListener();
		layout.setVisibility(View.VISIBLE);
		
		leftTB.setOnCheckedChangeListener(listener);
		straightTB.setOnCheckedChangeListener(listener);
		rightTB.setOnCheckedChangeListener(listener);
	}

	@Override
	public void update(int holeNumber) {
		String direction =  score.getHoleScore(holeNumber).getTeeShotDirection();
		if(direction == null){
			shotDirectionState = straightState;
		}
		else if(direction.equals("Left")){
			shotDirectionState = leftState;
		}
		else if (direction.equals("Right")){
			shotDirectionState = rightState;
		}
		else if (direction.equals("Straight")){
			shotDirectionState = straightState;
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
			if(isChecked){
				if(buttonView == leftTB){
					shotDirectionState = leftState;
				}
				else if (buttonView == straightTB){
					shotDirectionState = straightState;
				}
				else if(buttonView == rightTB){
					shotDirectionState = rightState;
				}
			}
			shotDirectionState.update();
		}
	};

	@Override
	public void registerAsListener() {
		playRoundMaster.registerListener(this);
	}
	
	public String getShotDirection(){
		return shotDirectionState.getDirection();
	}

	@Override
	public void save(int holeNumber) {
		score.updateShotDirection((long)holeNumber, getShotDirection());
	}
}
