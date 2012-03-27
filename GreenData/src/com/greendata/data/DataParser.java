package com.greendata.data;

import android.os.Parcelable;

public interface DataParser {
	public DataResults<? extends Parcelable> parseResult(String wsResponse);
}
