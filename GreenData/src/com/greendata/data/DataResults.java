package com.greendata.data;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class DataResults<E extends Parcelable> implements Parcelable {
	private List<E> mList;
	private int mTotalItem;
	private int mOffset;
	private int mLimit;
	
	public DataResults(){
		super();
	}
	
	public List<E> getList() {
		return mList;	
	}
	
	public void setList(List<E> list) {
		mList = list;
	}
	
	public void setTotalItem(int totalItem) {
		mTotalItem = totalItem;
	}
	
	public int getTotalItem() {
		return mTotalItem;
	}
	
	public void setOffset(int offset) {
		mOffset = offset;
	}
	
	public int getOffset() {
		return mOffset;
	}
	
	public void setLimit(int limit) {
		mLimit = limit;
	}
	
	public int getLimit() {
		return mLimit;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
//		dest.writeParcelableArray(mList, flags);
		dest.writeList(mList);
		dest.writeInt(mTotalItem);
		dest.writeInt(mOffset);
		dest.writeInt(mLimit);
	}
	
	private DataResults(Parcel in) {
		in.readList(mList, null);
		mTotalItem = in.readInt();
		mOffset = in.readInt();
		mLimit = in.readInt();
	}
	
	public static final Parcelable.Creator<DataResults> CREATOR = new Parcelable.Creator<DataResults>() {
		public DataResults createFromParcel(final Parcel in) {
			return new DataResults(in);
		}

		public DataResults[] newArray(final int size) {
			return new DataResults[size];
		}
	};
}
