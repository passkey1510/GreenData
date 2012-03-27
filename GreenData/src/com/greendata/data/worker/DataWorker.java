package com.greendata.data.worker;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.NetworkConnectionResult;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;
import com.greendata.data.DataResults;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class DataWorker {
	protected String mRestUrl;
	// private DataQuery mQuery;
	protected DataParser mParser;

	public DataWorker() {
		super();
	}

	public DataWorker(String restUrl, DataParser parser) {
		mRestUrl = restUrl;
		// mQuery = query;
		mParser = parser;
	}

	public String getRestUrl() {
		return mRestUrl;
	}

	public void setRestUrl(String restUrl) {
		mRestUrl = restUrl;
	}

	// public DataQuery getQuery() {
	// return mQuery;
	// }
	//
	// public void setDataQuery(DataQuery query) {
	// mQuery = query;
	// }

	public DataParser getParser() {
		return mParser;
	}

	public void setParser(DataParser parser) {
		mParser = parser;
	}

	public Bundle start(final Context context, DataQuery query)
			throws IllegalStateException, IOException, URISyntaxException,
			RestClientException, JSONException {
		NetworkConnectionResult result = NetworkConnection
				.retrieveResponseFromService(mRestUrl,
						NetworkConnection.METHOD_GET, query.getParams());
		DataResults<?> results = mParser.parseResult(result.wsResponse);
		// MediaResults<YouTubeVideoItem> mediaResults =
		// YouTubeVideoListJsonFactory
		// .parseResult(result.wsResponse);
		// mParser.parseResult(result.wsResponse);
		Bundle bundle = new Bundle();
		bundle.putParcelable("results",
				results);
		return bundle;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		DataWorker worker = (DataWorker) obj;

		return mRestUrl.equals(worker.getRestUrl());
	}

	public int hashCode() {
		return mRestUrl.hashCode();
	}

//	private DataWorker(final Parcel in) {
//		mRestUrl = in.readString();
//		mParser = in.readParcelable(DataParser.class.getClassLoader());
//	}
//
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		dest.writeString(mRestUrl);
//		dest.writeParcelable(mParser, flags);
//	}
//
//	public static final Parcelable.Creator<DataWorker> CREATOR = new Parcelable.Creator<DataWorker>() {
//		public DataWorker createFromParcel(final Parcel in) {
//			return new DataWorker(in);
//		}
//
//		public DataWorker[] newArray(final int size) {
//			return new DataWorker[size];
//		}
//	};
}
