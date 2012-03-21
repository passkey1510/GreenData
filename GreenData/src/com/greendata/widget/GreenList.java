package com.greendata.widget;

import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.greendata.requestmanager.GreenDataRequestManager.OnRequestFinishedListener;
import com.openwidget.listview.RefreshAndLoadMoreListView;

public class GreenList extends RefreshAndLoadMoreListView implements
		OnRequestFinishedListener {
	private BaseAdapter mAdapter;
	
	public GreenList(Context context) {
		super(context);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		getList();
	}

	private void getList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestFinished(int requestId, int resultCode, Bundle payload) {
		// TODO Auto-generated method stub
		
	}
}
