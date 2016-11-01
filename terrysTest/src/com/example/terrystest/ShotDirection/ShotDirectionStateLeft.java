package com.example.terrystest.ShotDirection;

import android.widget.ToggleButton;

public class ShotDirectionStateLeft extends ShotDirectionState {

	public ShotDirectionStateLeft(ToggleButton left, ToggleButton straight, ToggleButton right) {
		super(left, straight, right);
	}

	@Override
	public void update() {
		leftToggleButton.setChecked(true);
		straightToggleButton.setChecked(false);
		rightToggleButton.setChecked(false);
	}

	@Override
	public String getDirection() {
		return "Left";
	}
}
