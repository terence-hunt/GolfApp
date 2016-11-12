package com.example.terrystest;

import com.example.terrystest.GolfAppConfiguration.GolfAppConfigFactory;
import com.example.terrystest.GolfAppConfiguration.GolfAppConfigScoring;

import android.app.Activity;
import android.os.Bundle;
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
		recordShotDirection.setChecked(scoringConfig.getRecordTeeShotDirection());

		recordShotDirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				boolean[] boolArray = new boolean[1];
				boolArray[0] = recordShotDirection.isChecked();
				GolfAppConfigFactory.getGolfAppConfigScoring().save(boolArray);
			}
		});
	}

	public void onPause(Bundle savedInstanceState){
		//save all the inputs from the user
		boolean[] boolArray = new boolean[1];
		boolArray[0] = recordShotDirection.isChecked();
		GolfAppConfigFactory.getGolfAppConfigScoring().save(boolArray);
	}

	public void onStop(Bundle savedInstanceState){
		//save all the inputs from the user
		boolean[] boolArray = new boolean[1];
		boolArray[0] = recordShotDirection.isChecked();
		GolfAppConfigFactory.getGolfAppConfigScoring().save(boolArray);
	}
	public void onDestroy(Bundle savedInstanceState){
		//save all the inputs from the user
		boolean[] boolArray = new boolean[1];
		boolArray[0] = recordShotDirection.isChecked();
		GolfAppConfigFactory.getGolfAppConfigScoring().save(boolArray);
	}

}
