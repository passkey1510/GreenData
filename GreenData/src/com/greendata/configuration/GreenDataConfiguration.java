package com.greendata.configuration;

import java.util.HashMap;
import java.util.Map;

public class GreenDataConfiguration {
	private static GreenDataConfiguration mInstance = new GreenDataConfiguration();
	private Map<String, RequestConfiguration> mConfiguration;
	private GreenDataConfiguration() {
		if (mConfiguration == null) {
			mConfiguration = new HashMap<String, RequestConfiguration>();
		}
	}
	
	public Map<String, RequestConfiguration> getConfiguration() {
		return mConfiguration;
	}
	
	public static GreenDataConfiguration getInstance() {
		return mInstance;
	}
}
