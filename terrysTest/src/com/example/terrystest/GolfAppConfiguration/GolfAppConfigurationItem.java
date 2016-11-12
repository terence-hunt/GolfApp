package com.example.terrystest.GolfAppConfiguration;

import java.io.File;

import com.example.MyApplication;

import android.content.Context;

public abstract class GolfAppConfigurationItem {
	String configurationFile;

	public File getConfigurationFile(){
		Context mAppContext = MyApplication.getAppContext();
		return new File(mAppContext.getFilesDir()+""+File.separator+configurationFile);
	}
	
	abstract public void save(boolean[] boolArray);
}
