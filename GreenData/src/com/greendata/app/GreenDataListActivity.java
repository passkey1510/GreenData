package com.greendata.app;

import java.util.TreeMap;

import com.greendata.R;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;
import com.greendata.data.DataResults;
import com.greendata.data.service.DataService;
import com.greendata.data.worker.DataWorker;
import com.greendata.requestmanager.GreenDataRequestManager;
import com.greendata.requestmanager.GreenDataRequestManager.OnRequestFinishedListener;
import com.openwidget.listview.RefreshAndLoadMoreListView;
import com.openwidget.listview.RefreshAndLoadMoreListView.OnLoadMoreListener;
import com.openwidget.listview.RefreshAndLoadMoreListView.OnRefreshListener;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.BaseAdapter;
import com.greendata.app.GreenDataActivity.OnRequestDataListener;

public class GreenDataListActivity extends GreenDataActivity implements
		OnRequestDataListener {
	private static final int DEFAULT_THRESHOLD = 5;
	private RefreshAndLoadMoreListView mListView;
	private BaseAdapter mAdapter;

	private int mThreshold = DEFAULT_THRESHOLD;
	private boolean mIsFirstLoad = true;
	private DataParser mParser;
	private DataQuery mQuery;
	private DataWorker mWorker;

	public int getThreshold() {
		return mThreshold;
	}

	public void setThreshold(int threshold) {
		mThreshold = threshold;
	}

	public BaseAdapter getListAdapter() {
		return mAdapter;
	}

	public void setListAdapter(BaseAdapter adapter) {
		mAdapter = adapter;
	}

	public DataParser getParser() {
		return mParser;
	}

	public void setParser(DataParser parser) {
		mParser = parser;
	}

	public void setWorker(DataWorker worker) {
		mWorker = worker;
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.green_list_activity);
		setOnRequestDataListener(this);

		mListView = (RefreshAndLoadMoreListView) findViewById(R.id.green_list_view);
		mListView.setAdapter(mAdapter);
		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				doGetList(new DataQuery(new TreeMap<String, String>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -5957690356117127223L;

					{
						put("start-index", "1");
						put("maxResuls", String.valueOf(mThreshold));
					}
				}));
			}
		});

		mListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// final int itemCount = mMediaAdapter.getCount();
				// if (mMediaAdapter.getMaximumItemCount() ==
				// MediaAdapter.UNLIMITED
				// || itemCount < mMediaAdapter.getMaximumItemCount()) {
				// getVideoList(itemCount + 1, mThreshold);
				// } else {
				// mVideoListView.completeLoadMore();
				// }
			}
		});
	}

	@Override
	public void onGetData() {
		// TODO Auto-generated method stub
		// doGetList();
		// Show loader progress bar
	}

	@Override
	public void onGetDataCompleted(DataResults results) {
		// mediaResults =
		// if (mIsFirstLoad) {
		// mAdapter.prependAll(mediaResults.getMediaList());
		// mIsFirstLoad = false;
		// } else {
		// if (mListView.getIsRefreshing()) {
		// mAdapter.prependAll(mediaResults.getMediaList());
		// mListView.completeRefreshing();
		// } else {
		// mAdapter.appendAll(mediaResults.getMediaList());
		// mListView.completeLoadMore();
		// }
		// }
		// mAdapter.setMaximumItemCount(mediaResults.getTotalIem());
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void getDataComplete() {
		// Remove loader
	}

	protected void doGetList(DataQuery dataQuery) {
		super.doGetData(mWorker, dataQuery);
	}
}
