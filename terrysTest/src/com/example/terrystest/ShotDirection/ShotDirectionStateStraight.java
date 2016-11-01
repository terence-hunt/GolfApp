package com.example.terrystest.ShotDirection;

import android.widget.ToggleButton;

public class ShotDirectionStateStraight extends ShotDirectionState{

	public ShotDirectionStateStraight(ToggleButton left, ToggleButton straight, ToggleButton right) {
		super(left, straight, right);
	}

	@Override
	public void update() {
		leftToggleButton.setChecked(false);
		straightToggleButton.setChecked(true);
		rightToggleButton.setChecked(false);
	}

	@Override
	public String getDirection() {
		return "Straight";
	}
}
