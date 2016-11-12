package com.example.terrystest.GolfAppConfiguration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class GolfAppConfigFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8639485319939075907L;
	private static GolfAppConfigScoring scoringConfig;

	public static GolfAppConfigScoring getGolfAppConfigScoring(){
		if(scoringConfig == null){
			scoringConfig = new GolfAppConfigScoring();
			if(scoringConfig.getConfigurationFile().exists()){
				FileInputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream(scoringConfig.getConfigurationFile());
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					scoringConfig = (GolfAppConfigScoring) objectInputStream.readObject();
					objectInputStream.close();
					fileInputStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return scoringConfig;
	}
}
