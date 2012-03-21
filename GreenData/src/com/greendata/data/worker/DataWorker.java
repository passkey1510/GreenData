package com.greendata.data.worker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.json.JSONException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class DataWorker implements Parcelable{
	protected String mRestUrl;
//	private DataQuery mQuery;
	protected DataParser mParser;
	
	public DataWorker() {
		super();
	}
	public DataWorker(String restUrl, DataParser parser) {
		mRestUrl = restUrl;
//		mQuery = query;
		mParser = parser;
	}
	
	public String getRestUrl() {
		return mRestUrl;
	}
	
	public void setRestUrl(String restUrl) {
		mRestUrl = restUrl;
	}
	
//	public DataQuery getQuery() {
//		return mQuery;
//	}
//	
//	public void setDataQuery(DataQuery query) {
//		mQuery = query;
//	}
	
	public DataParser getParser() {
		return mParser;
	}
	
	public void setParser(DataParser parser) {
		mParser = parser;
	}
	
	public Bundle start(final Context context, DataQuery query) throws IllegalStateException, IOException, URISyntaxException, RestClientException, JSONException {
		return null;
	}
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		DataWorker worker = (DataWorker) obj;
		
		return mRestUrl.equals(worker.getRestUrl());
	}
	
	public int hashCode() {
		return mRestUrl.hashCode();
	}
	private DataWorker(final Parcel in) {
		mRestUrl = in.readString();
		mParser = in.readParcelable(DataParser.class.getClassLoader());
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mRestUrl);
		dest.writeParcelable(mParser, flags);
	}
	
	public static final Parcelable.Creator<DataWorker> CREATOR = new Parcelable.Creator<DataWorker>() {
		public DataWorker createFromParcel(final Parcel in) {
			return new DataWorker(in);
		}

		public DataWorker[] newArray(final int size) {
			return new DataWorker[size];
		}
	};
}
