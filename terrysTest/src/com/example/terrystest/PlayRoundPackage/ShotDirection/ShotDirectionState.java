package com.example.terrystest.PlayRoundPackage.ShotDirection;

import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

//TODO if the toggle button is selected need to remove the listener

public abstract class ShotDirectionState {
	
	ToggleButton leftToggleButton;
	ToggleButton straightToggleButton;
	ToggleButton rightToggleButton;
	OnCheckedChangeListener listener;
	
	public ShotDirectionState(ToggleButton left, ToggleButton straight, ToggleButton right, OnCheckedChangeListener listener){
		this.leftToggleButton = left;
		this.straightToggleButton = straight;
		this.rightToggleButton = right;
		this.listener = listener; 
	}
	
	public abstract void update();
	public abstract String getDirection();
}
