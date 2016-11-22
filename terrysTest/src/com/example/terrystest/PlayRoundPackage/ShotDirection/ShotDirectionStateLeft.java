package com.example.terrystest.PlayRoundPackage.ShotDirection;

import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ShotDirectionStateLeft extends ShotDirectionState {
	

	public ShotDirectionStateLeft(ToggleButton left, ToggleButton straight, ToggleButton right, OnCheckedChangeListener listener) {
		super(left, straight, right, listener);
	}

	@Override
	public void update() {
		leftToggleButton.setChecked(true);
		straightToggleButton.setChecked(false);
		rightToggleButton.setChecked(false);
		leftToggleButton.setOnCheckedChangeListener(null);
		straightToggleButton.setOnCheckedChangeListener(listener);
		rightToggleButton.setOnCheckedChangeListener(listener);
		leftToggleButton.setEnabled(false);
		straightToggleButton.setEnabled(true);
		rightToggleButton.setEnabled(true);
	}

	@Override
	public String getDirection() {
		return "Left";
	}
}
