package com.example.terrystest.GolfAppConfiguration;

import java.io.File;
import java.util.HashMap;

import com.example.MyApplication;

import android.content.Context;

public abstract class GolfAppConfigurationItem {
	public static String configurationFile = "ERROR";

	public File getConfigurationFile(){
		Context mAppContext = MyApplication.getAppContext();
		return new File(mAppContext.getFilesDir()+""+File.separator+configurationFile);
	}
	
	abstract public void save(HashMap<Integer,Boolean> configAttributes);
}
