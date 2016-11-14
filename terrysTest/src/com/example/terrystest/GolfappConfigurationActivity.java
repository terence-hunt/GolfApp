package com.example.terrystest;

import java.util.HashMap;

import com.example.terrystest.GolfAppConfiguration.GolfAppConfigFactory;
import com.example.terrystest.GolfAppConfiguration.GolfAppConfigScoring;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class GolfappConfigurationActivity extends Activity {
	Switch recordShotDirection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.golfapp_configuration);

		//add all the configuration options.
		GolfAppConfigScoring scoringConfig = GolfAppConfigFactory.getGolfAppConfigScoring();
		recordShotDirection = (Switch)findViewById(R.id.ShotDirection);
		recordShotDirection.setChecked(scoringConfig.getConfigurationAttribute(GolfAppConfigScoring.RECORD_TEE_SHOT_DIRECTION));
		recordShotDirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//saveConfig();
			}
		});
	}
	@Override
	public void onBackPressed() {
	    this.saveConfig();
	    super.onBackPressed();
	}

	public void onPause(Bundle savedInstanceState){
		//TODO this does not work
		Log.i("TerryTerryTerry", "onPause was called");
		this.saveConfig();
		super.onPause();
	}

	public void onStop(Bundle savedInstanceState){

	}
	public void onDestroy(Bundle savedInstanceState){

	}
	
	private void saveConfig(){
		HashMap<Integer, Boolean> configAttributes = new HashMap<Integer, Boolean>();
		configAttributes.put(GolfAppConfigScoring.RECORD_NUMBER_OF_PUTTS, true);
		configAttributes.put(GolfAppConfigScoring.RECORD_TEE_SHOT_DIRECTION, recordShotDirection.isChecked());
		configAttributes.put(GolfAppConfigScoring.SHOW_STAPLEFORD_SCORE,true);
		configAttributes.put(GolfAppConfigScoring.SHOW_GROSS_SCORE,true);
		configAttributes.put(GolfAppConfigScoring.SHOW_NETT_SCORE, true);
		configAttributes.put(GolfAppConfigScoring.SHOW_NUMBER_OF_PUTTS, true);
		GolfAppConfigFactory.getGolfAppConfigScoring().save(configAttributes);
	}

}
