package com.greendata.data;

import android.os.Parcelable;

public abstract class DataParser {
	public DataResults<? extends Parcelable> parseResult(String wsResponse) {
		DataResults<? extends Parcelable> results = new DataResults<Parcelable>();
		results.setRawResponse(wsResponse);
		return results;
	}
}
