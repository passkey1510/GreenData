package com.greendata.data;

import java.util.Map;
import java.util.TreeMap;

public class GreenRequest {
	private String mRestUrl;
	private Map<String, String> mParams;
	private DataParser mParser;
	
	public GreenRequest(String restUrl, Map<String, String> params, DataParser parser) {
		mRestUrl = restUrl;
		mParams = params;
		mParser = parser;
	}

	public Map<String, String> getParams() {
		return mParams;
	}
	
	public DataParser getParser() {
		return mParser;
	}
	
	public String getRestUrl() {
		return mRestUrl;
	}
}
