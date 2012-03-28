package com.greendata.demo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class YouTubeItem implements Parcelable {
	private String mTitle;
	private String mId;

	public YouTubeItem() {
		super();
	}
	public YouTubeItem(String title) {
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

	public void setVideoID(String id) {
		mId = id;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	private YouTubeItem(Parcel in) {
		mId = in.readString();
		mTitle = in.readString();
	}

	public static final Parcelable.Creator<YouTubeItem> CREATOR = new Parcelable.Creator<YouTubeItem>() {
		public YouTubeItem createFromParcel(final Parcel in) {
			return new YouTubeItem(in);
		}

		public YouTubeItem[] newArray(final int size) {
			return new YouTubeItem[size];
		}
	};

	@Override
	public String toString() {
		return mTitle;
	}
}
