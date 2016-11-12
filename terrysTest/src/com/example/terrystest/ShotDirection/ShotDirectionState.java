package com.example.terrystest.ShotDirection;

import android.widget.ToggleButton;

//TODO if the toggle button is selected need to remove the listener

public abstract class ShotDirectionState {
	
	ToggleButton leftToggleButton;
	ToggleButton straightToggleButton;
	ToggleButton rightToggleButton;
	
	public ShotDirectionState(ToggleButton left, ToggleButton straight, ToggleButton right){
		this.leftToggleButton = left;
		this.straightToggleButton = straight;
		this.rightToggleButton = right;
	}
	
	public abstract void update();
	public abstract String getDirection();
}
