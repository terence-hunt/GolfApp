package com.example.terrystest.GolfAppConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GolfAppConfigScoring extends GolfAppConfigurationItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean recordTeeShotDirection;
	boolean recordNumberOfPutts;
	boolean showStaplefordScore;
	String configFile = "GolfAppConfigScoring.srl";

	GolfAppConfigScoring(){
		//loads default configuration
		recordTeeShotDirection = true;
		recordNumberOfPutts = true;
		showStaplefordScore = true;
	}
	
	public void setRecordTeeShotDirection(boolean value){
		recordTeeShotDirection = value;
	}

	public boolean getRecordTeeShotDirection(){
		return recordTeeShotDirection;
	}

	@Override
	public void save(boolean[] boolArray) {
		recordTeeShotDirection = boolArray[0];
		File outFile = this.getConfigurationFile();
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(outFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
