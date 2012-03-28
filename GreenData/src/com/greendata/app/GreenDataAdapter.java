package com.greendata.app;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.greendata.data.DataPager;
import com.greendata.data.DataQuery;

import android.app.Service;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GreenDataAdapter<E extends Parcelable> extends BaseAdapter {
	public static final int UNLIMITED = -1;
	private Context mContext;
	private FileInputStream fis;
	private static final StringBuilder BUILDER = new StringBuilder();
	private LayoutInflater mInflater;
	private List<E> mList;
	private int mMaximumItemCount = UNLIMITED;
	private DataQuery mCurrentQuery = new DataQuery(
			new TreeMap<String, String>());

	public GreenDataAdapter(Context context) {
		initialize(context, new ArrayList<E>());
	}

	public GreenDataAdapter(Context context, List<E> list) {
		initialize(context, list);
	}

	public void initialize(Context context, List<E> list) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		mList = list;
	}

	public DataQuery getCurrentQuery() {
		return mCurrentQuery;
	}

	public int getMaximumItemCount() {
		return mMaximumItemCount;
	}

	public void setMaximumItemCount(int maximumItemCount) {
		mMaximumItemCount = maximumItemCount;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// return null;
	// }

	public void addAll(int index, List<E> list) {
		mList.addAll(index, list);
	}

	public void appendAll(List<E> list) {
		mList.addAll(list);
	}

	public void prependAll(List<E> list) {
		addAll(0, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(mContext);
		textView.setText(mList.get(position).toString());
		textView.setHeight(90);
		return textView;
	}
}
