package com.example.terrystest.PlayRoundPackage.ShotDirection;

import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ShotDirectionStateRight extends ShotDirectionState {

	public ShotDirectionStateRight(ToggleButton left, ToggleButton straight, ToggleButton right, OnCheckedChangeListener listener) {
		super(left, straight, right,listener);
	}

	@Override
	public void update() {
		leftToggleButton.setChecked(false);
		straightToggleButton.setChecked(false);
		rightToggleButton.setChecked(true);
		leftToggleButton.setOnCheckedChangeListener(listener);
		straightToggleButton.setOnCheckedChangeListener(listener);
		rightToggleButton.setOnCheckedChangeListener(null);
		leftToggleButton.setEnabled(true);
		straightToggleButton.setEnabled(true);
		rightToggleButton.setEnabled(false);
	}

	@Override
	public String getDirection() {
		return "Right";
	}
}
