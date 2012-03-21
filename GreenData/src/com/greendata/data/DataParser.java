package com.greendata.data;

import com.greendata.data.worker.DataWorker;

import android.os.Parcel;
import android.os.Parcelable;

public class DataParser implements Parcelable{

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	public static final Parcelable.Creator<DataParser> CREATOR = new Parcelable.Creator<DataParser>() {
		public DataParser createFromParcel(final Parcel in) {
			return new DataParser();
		}

		public DataParser[] newArray(final int size) {
			return new DataParser[size];
		}
	};
	
//	public Object parseResults();
	
}
