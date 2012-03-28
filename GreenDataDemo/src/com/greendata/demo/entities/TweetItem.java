package com.greendata.demo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class TweetItem implements Parcelable {
	private String mTitle;
	private String mId;

	public TweetItem() {
		super();
	}
	public TweetItem(String title) {
		mTitle = title;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mId);
		dest.writeString(mTitle);
	}

	public void setID(String id) {
		mId = id;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	private TweetItem(Parcel in) {
		mId = in.readString();
		mTitle = in.readString();
	}

	public static final Parcelable.Creator<TweetItem> CREATOR = new Parcelable.Creator<TweetItem>() {
		public TweetItem createFromParcel(final Parcel in) {
			return new TweetItem(in);
		}

		public TweetItem[] newArray(final int size) {
			return new TweetItem[size];
		}
	};

	@Override
	public String toString() {
		return mTitle;
	}
}
