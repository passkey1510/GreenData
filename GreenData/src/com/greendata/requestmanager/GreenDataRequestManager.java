package com.greendata.requestmanager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.SparseArray;

import com.foxykeep.datadroid.requestmanager.RequestManager;
import com.greendata.configuration.RequestConfiguration;
import com.greendata.configuration.GreenDataConfiguration;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;
import com.greendata.data.service.DataService;
import com.greendata.data.worker.DataWorker;
import com.greendata.provider.MemoryProvider;
import com.greendata.util.Utils;

public class GreenDataRequestManager extends RequestManager{
	// TODO : This constant will be used in your special methods
    @SuppressWarnings("unused")
    private static final int MAX_RANDOM_REQUEST_ID = 1000000;
	public static final String RECEIVER_EXTRA_VIDEO_LIST = "com.passkey1510.mediabook.extras.videoList";
    private static Random sRandom = new Random();
    // Singleton management
    private static GreenDataRequestManager sInstance;

    public static GreenDataRequestManager from(final Context context) {
        if (sInstance == null) {
            sInstance = new GreenDataRequestManager(context);
        }

        return sInstance;
    }

    private SparseArray<Intent> mRequestSparseArray;
    // TODO : This variable will be used in your special methods
    @SuppressWarnings("unused")
    private Context mContext;
    private ArrayList<WeakReference<OnRequestFinishedListener>> mListenerList;
    private Handler mHandler = new Handler();
    // TODO : This variable will be used in your special methods
    @SuppressWarnings("unused")
    private EvalReceiver mEvalReceiver = new EvalReceiver(mHandler);
	private MemoryProvider mMemoryProvider = MemoryProvider.getInstance();
	private GreenDataConfiguration mConfiguration = GreenDataConfiguration.getInstance();
	
    private GreenDataRequestManager(final Context context) {
        mContext = context.getApplicationContext();
        mRequestSparseArray = new SparseArray<Intent>();
        mListenerList = new ArrayList<WeakReference<OnRequestFinishedListener>>();
    }

    /**
     * The ResultReceiver that will receive the result from the Service
     */
    private class EvalReceiver extends ResultReceiver {
        EvalReceiver(final Handler h) {
            super(h);
        }

        @Override
        public void onReceiveResult(final int resultCode, final Bundle resultData) {
            handleResult(resultCode, resultData);
        }
    }

    /**
     * Clients may implements this interface to be notified when a request is
     * finished
     * 
     * @author Foxykeep
     */
    public static interface OnRequestFinishedListener extends EventListener {

        /**
         * Event fired when a request is finished.
         * 
         * @param requestId The request Id (to see if this is the right request)
         * @param resultCode The result code (0 if there was no error)
         * @param payload The result of the service execution.
         */
        public void onRequestFinished(int requestId, int resultCode, Bundle payload);
    }

    /**
     * Add a {@link OnRequestFinishedListener} to this
     * {@link MediaBookRequestManager}. Clients may use it in order to listen to
     * events fired when a request is finished.
     * <p>
     * <b>Warning !! </b> If it's an {@link Activity} that is used as a
     * Listener, it must be detached when {@link Activity#onPause} is called in
     * an {@link Activity}.
     * </p>
     * 
     * @param listener The listener to add to this
     *            {@link MediaBookRequestManager} .
     */
    public void addOnRequestFinishedListener(final OnRequestFinishedListener listener) {
        WeakReference<OnRequestFinishedListener> weakRef = new WeakReference<OnRequestFinishedListener>(listener);
        synchronized (mListenerList) {
            if (!mListenerList.contains(weakRef)) {
                mListenerList.add(weakRef);
            }
        }
    }

    /**
     * Remove a {@link OnRequestFinishedListener} to this
     * {@link MediaBookRequestManager}.
     * 
     * @param listenerThe listener to remove to this
     *            {@link MediaBookRequestManager}.
     */
    public void w(final OnRequestFinishedListener listener) {
        synchronized (mListenerList) {
            mListenerList.remove(new WeakReference<OnRequestFinishedListener>(listener));
        }
    }

    /**
     * Return whether a request (specified by its id) is still in progress or
     * not
     * 
     * @param requestId The request id
     * @return whether the request is still in progress or not.
     */
    public boolean isRequestInProgress(final int requestId) {
        return (mRequestSparseArray.indexOfKey(requestId) >= 0);
    }

    /**
     * This method is call whenever a request is finished. Call all the
     * available listeners to let them know about the finished request
     * 
     * @param resultCode The result code of the request
     * @param resultData The bundle sent back by the service
     */
    protected void handleResult(final int resultCode, final Bundle resultData) {

        // Get the request Id
        final int requestId = resultData.getInt(RECEIVER_EXTRA_REQUEST_ID);

//        if (resultCode == DataService.SUCCESS_CODE) {
//            final Intent intent = mRequestSparseArray.get(requestId);
//            switch (intent.getIntExtra(DataService.INTENT_EXTRA_WORKER_TYPE, -1)) {
////                case DataService.WORKER_TYPE_YOUTUBE_LIST:
////                    mMemoryProvider.mediaResults = resultData.getParcelable(RECEIVER_EXTRA_VIDEO_LIST);
////                    break;
//            }
//        }
        // Remove the request Id from the "in progress" request list
        mRequestSparseArray.remove(requestId);

        // Call the available listeners
        synchronized (mListenerList) {
            for (WeakReference<OnRequestFinishedListener> weakRef : mListenerList) {
                OnRequestFinishedListener listener = weakRef.get();
                if (weakRef != null) {
                    listener.onRequestFinished(requestId, resultCode, resultData);
                }
            }
        }
    }

	/**
     * Remove a {@link OnRequestFinishedListener} to this
     * {@link SkeletonRequestManager}.
     * 
     * @param listenerThe listener to remove to this
     *            {@link SkeletonRequestManager}.
     */
    public void removeOnRequestFinishedListener(final OnRequestFinishedListener listener) {
        synchronized (mListenerList) {
            mListenerList.remove(new WeakReference<OnRequestFinishedListener>(listener));
        }
    }

    /**
     * Here begin the special methods
     */

    /**
     * Gets the list of videos and save it in the memory provider
     * 
     * @return the request Id
     */
//    public int getYouTubeVideoList(int startIndex, int maxResults) {
//
//        // Check if a match to this request is already launched
//        final int requestSparseArrayLength = mRequestSparseArray.size();
//        for (int i = 0; i < requestSparseArrayLength; i++) {
//            final Intent savedIntent = mRequestSparseArray.valueAt(i);
//
////            if (savedIntent.getIntExtra(DataService.INTENT_EXTRA_WORKER_TYPE, -1) != DataService.WORKER_TYPE_YOUTUBE_LIST) {
////                continue;
////            }
//            return mRequestSparseArray.keyAt(i);
//        }
//
//        final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);
//
//        final Intent intent = new Intent(mContext, DataService.class);
////        intent.putExtra(DataService.INTENT_EXTRA_WORKER_TYPE, DataService.WORKER_TYPE_YOUTUBE_LIST);
//        intent.putExtra(DataService.INTENT_EXTRA_RECEIVER, mEvalReceiver);
//        intent.putExtra(DataService.INTENT_EXTRA_REQUEST_ID, requestId);
////        intent.putExtra(DataService.INTENT_EXTRA_YOUTUBE_VIDEO_LIST_START_INDEX, startIndex);
////        intent.putExtra(DataService.INTENT_EXTRA_YOUTUBE_VIDEO_LIST_MAX_RESULTS, maxResults);
//        mContext.startService(intent);
//
//        mRequestSparseArray.append(requestId, intent);
//
//        // Reset the cityList in the provider
////        mMemoryProvider.videoList = null;
//
//        return requestId;
//    }

//    public int getList() {
//		return 0;
//    	
//    }
//    
//    public int getDetails() {
//		return 0;
//    	
//    }
//    
//	public int getYouTubeVideoList() {
//		return getYouTubeVideoList(1, 50);
//	}

	public int getData(RequestConfiguration configuration) {
		// A request is identified by a worker and query
		final String uid = configuration.getId();
		
		// Put a request configuration to global configuration
		mConfiguration.getConfiguration().put(uid, configuration);
		
		// Check if a match to this request is already launched
        final int requestSparseArrayLength = mRequestSparseArray.size();
        for (int i = 0; i < requestSparseArrayLength; i++) {
            final Intent savedIntent = mRequestSparseArray.valueAt(i);

            if (savedIntent.getStringExtra(DataService.INTENT_EXTRA_CONFIGURATION_ID) != uid) {
                continue;
            }
            return mRequestSparseArray.keyAt(i);
        }

        final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);

        final Intent intent = new Intent(mContext, DataService.class);
        intent.putExtra(DataService.INTENT_EXTRA_RECEIVER, mEvalReceiver);
        intent.putExtra(DataService.INTENT_EXTRA_REQUEST_ID, requestId);
        intent.putExtra(DataService.INTENT_EXTRA_CONFIGURATION_ID, uid);
        mContext.startService(intent);

        mRequestSparseArray.append(requestId, intent);

        // Reset the cityList in the provider
//        mMemoryProvider.videoList = null;
        //TODO: User MemoryProvider
        return requestId;
	}
}
