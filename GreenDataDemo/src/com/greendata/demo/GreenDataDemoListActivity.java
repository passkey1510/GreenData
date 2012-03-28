package com.greendata.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.foxykeep.datadroid.exception.RestClientException;
import com.greendata.app.GreenDataAdapter;
import com.greendata.app.GreenDataListActivity;
import com.greendata.data.DataPager;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;
import com.greendata.data.DataResults;
import com.greendata.data.GreenRequest;
import com.greendata.data.service.DataService;
import com.greendata.data.worker.DataWorker;
import com.greendata.demo.entities.YouTubeItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GreenDataDemoListActivity extends GreenDataListActivity<YouTubeItem> {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("start-index", "1");
        params.put("max-results", "5");
        params.put("alt", "jsonc");
        params.put("v", "2");
        DataQuery query = new DataQuery(params);
        
//        DataWorker worker = new DataWorker("http://gdata.youtube.com/feeds/api/standardfeeds/top_rated", parser);
//        setWorker(worker);
//        doGetList(query);
        GreenDataAdapter<YouTubeItem> adapter = new GreenDataAdapter<YouTubeItem>(this);
        setListAdapter(adapter);
        doGetList(new GreenRequest("http://gdata.youtube.com/feeds/api/standardfeeds/top_rated", params, new DataParser() {
			
			@Override
			public DataResults<YouTubeItem> parseResult(String wsResponse) {
				final ArrayList<YouTubeItem> videoList = new ArrayList<YouTubeItem>();
				try{
					final JSONObject parser = new JSONObject(wsResponse);
					final JSONObject root = parser.getJSONObject("data");
					final JSONArray itemArray = root.getJSONArray("items");
					final int size = itemArray.length();
					for (int i = 0; i < size; i++) {
						final JSONObject item = itemArray.getJSONObject(i);
						final YouTubeItem video = new YouTubeItem();
						video.setVideoID(item.getString("id"));
						video.setTitle(item.getString("title"));
	//					final JSONObject thumbnailObject = item
	//							.getJSONObject(JSONTag.YOUTUBE_THUMBNAIL);
	//					video.setThumbnailUrl(thumbnailObject
	//							.getString(JSONTag.YOUTUBE_THUMBNAIL_SQ));
						videoList.add(video);
					}
					final int totalItem = root.getInt("totalItems");
					final int offset = root.getInt("startIndex");
					final int limit = root.getInt("itemsPerPage");
					DataResults<YouTubeItem> mediaResults = new DataResults<YouTubeItem>();
					mediaResults.setList(videoList);
					mediaResults.setTotalItem(totalItem);
					mediaResults.setOffset(offset);
					mediaResults.setLimit(limit);
					return mediaResults;
				}
				catch(JSONException exception){
					return null;
				}
		}}));
    }
    
    private final class YouTubeDataPager implements DataPager {
    	GreenDataAdapter<YouTubeItem> mAdapter;
		@Override
		public DataQuery buildFirstPageQuery(DataQuery query) {
			query.getParams().put("start-index", "1");
			return query;
		}

		@Override
		public DataQuery buildNextPageQuery(DataQuery query) {
//			query.getParams().put("start-index", value);
			return null;
		}

		@Override
		public DataQuery buildPreviousPageQuery(DataQuery query) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataQuery buildRefreshQuery(DataQuery query) {
			return buildFirstPageQuery(query);
		}
    }
    
    private final class YouTubeDataAdapter extends GreenDataAdapter<YouTubeItem> {

		public YouTubeDataAdapter(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public DataQuery buildFirstPageQuery(DataQuery query) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataQuery buildNextPageQuery(DataQuery query) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataQuery buildPreviousPageQuery(DataQuery query) {
			DataQuery currentQuery = getCurrentQuery();
			final TreeMap<String, String> params = currentQuery.getParams();
			params.put("start-index", "1");
			return currentQuery;
		}

		@Override
		public DataQuery buildRefreshQuery(DataQuery query) {
			DataQuery currentQuery = getCurrentQuery();
			final TreeMap<String, String> params = currentQuery.getParams();
			params.put("start-index", "1");
			return currentQuery;
		}

    	
    }
    
}