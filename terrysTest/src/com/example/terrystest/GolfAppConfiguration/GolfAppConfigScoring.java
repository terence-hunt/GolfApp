package com.example.terrystest.GolfAppConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import android.util.Log;

public class GolfAppConfigScoring extends GolfAppConfigurationItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<Integer, Boolean> configAttributes;
	public static final int RECORD_NUMBER_OF_PUTTS = 0;
	public static final int RECORD_TEE_SHOT_DIRECTION = 1;
	public static final int SHOW_STAPLEFORD_SCORE = 2;
	public static final int SHOW_GROSS_SCORE = 3;
	public static final int SHOW_NETT_SCORE = 4;
	public static final int SHOW_NUMBER_OF_PUTTS = 5;

	GolfAppConfigScoring(){
		//loads default configuration
		configurationFile = "GolfAppConfigScoring.srl";
		configAttributes = createDefaultConfig();
	}

	private HashMap<Integer, Boolean> createDefaultConfig(){
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		map.put(RECORD_NUMBER_OF_PUTTS, true);
		map.put(RECORD_TEE_SHOT_DIRECTION, true);
		map.put(SHOW_STAPLEFORD_SCORE,true);
		map.put(SHOW_GROSS_SCORE,true);
		map.put(SHOW_NETT_SCORE, true);
		map.put(SHOW_NUMBER_OF_PUTTS, true);
		return map;
	}

	public void setConfigurationAttribute(Integer attribute,boolean value){
		configAttributes.put(attribute,value);
	}

	public boolean getConfigurationAttribute(int attribute){
		if(configAttributes.get(attribute) != null){
			return configAttributes.get(attribute);
		}
		else{
			Log.e("GolfAppConfigScoring",("The configuration Attribute " + attribute + " does not exist"));
			StackTraceElement[] trace = new Exception().getStackTrace();
			Log.e("GolfAppConfigScoring", trace.toString());
			return false;
		}
	}

	@Override
	public void save(HashMap<Integer,Boolean> newConfigAttributes) {
		//check if the config attributes have changed
		//Need to check if the new settings are the same as the default settings, if they are delete the config file
		if(!newConfigAttributes.equals(configAttributes)){
			configAttributes = newConfigAttributes;
			File outFile = this.getConfigurationFile();
			if(newConfigAttributes.equals(createDefaultConfig()) && outFile.exists() ){
				outFile.delete();
			}
			else {
				FileOutputStream fout;
				try {
					fout = new FileOutputStream(outFile);
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(this);
					oos.close();
				} catch (FileNotFoundException e) {
					Log.e("GolfAppConfigScoring", e.toString());
				} catch (IOException e) {
					Log.e("GolfAppConfigScoring", e.toString());
				}
			}
		}
	}
}
