package com.example.terrystest.PlayRoundPackage.ShotDirection;

import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ShotDirectionStateStraight extends ShotDirectionState{

	public ShotDirectionStateStraight(ToggleButton left, ToggleButton straight, ToggleButton right, OnCheckedChangeListener listener) {
		super(left, straight, right, listener);
	}

	@Override
	public void update() {
		leftToggleButton.setChecked(false);
		straightToggleButton.setChecked(true);
		rightToggleButton.setChecked(false);
		leftToggleButton.setOnCheckedChangeListener(listener);
		straightToggleButton.setOnCheckedChangeListener(null);
		rightToggleButton.setOnCheckedChangeListener(listener);
		leftToggleButton.setEnabled(true);
		straightToggleButton.setEnabled(false);
		rightToggleButton.setEnabled(true);
	}

	@Override
	public String getDirection() {
		return "Straight";
	}
}
