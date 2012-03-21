package com.greendata.data;

import java.util.SortedMap;
import java.util.TreeMap;

import android.os.Parcel;
import android.os.Parcelable;

public class DataQuery implements Parcelable{
	TreeMap<String, String> mParams;
	public DataQuery(TreeMap<String, String> params) {
		mParams = params;
	}
	
	public TreeMap<String, String> getParams() {
		return mParams;
	}
	
	public void putParam(String key, String value) {
		mParams.put(key, value);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		DataQuery query = (DataQuery) obj;
		
		return mParams.equals(query.getParams());
		
	}
	
	public int hashCode() {
		return mParams.hashCode();
	}

	private DataQuery(Parcel in) {
		if (mParams == null) {
			mParams = new TreeMap<String, String>();
		}
		in.readMap(mParams, TreeMap.class.getClassLoader());
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(mParams);
	}
	
	public static final Parcelable.Creator<DataQuery> CREATOR = new Parcelable.Creator<DataQuery>() {
		public DataQuery createFromParcel(final Parcel in) {
			return new DataQuery(in);
		}

		public DataQuery[] newArray(final int size) {
			return new DataQuery[size];
		}
	};
}
