package com.example.terrystest.ShotDirection;

import android.widget.ToggleButton;

public class ShotDirectionStateRight extends ShotDirectionState {

	public ShotDirectionStateRight(ToggleButton left, ToggleButton straight, ToggleButton right) {
		super(left, straight, right);
	}

	@Override
	public void update() {
		leftToggleButton.setChecked(false);
		straightToggleButton.setChecked(false);
		rightToggleButton.setChecked(true);
	}

	@Override
	public String getDirection() {
		return "Right";
	}
}
