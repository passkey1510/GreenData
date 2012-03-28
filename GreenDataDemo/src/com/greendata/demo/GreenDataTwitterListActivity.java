package com.greendata.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.foxykeep.datadroid.exception.RestClientException;
import com.greendata.app.GreenDataAdapter;
import com.greendata.app.GreenDataListActivity;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;
import com.greendata.data.DataResults;
import com.greendata.data.GreenRequest;
import com.greendata.data.service.DataService;
import com.greendata.data.worker.DataWorker;
import com.greendata.demo.entities.TweetItem;
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

public class GreenDataTwitterListActivity extends GreenDataListActivity<TweetItem> {

	@Override
	public DataQuery getNextPageQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataQuery getPreviousPageQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataQuery getRefreshQuery() {
		DataQuery currentQuery = getCurrentQuery();
		DataResults<TweetItem> results = (DataResults<TweetItem>) getLastResults();
//		results.getRawResponse();
		try {
			JSONObject resultObj = new JSONObject(results.getRawResponse());
			String refreshUrl = resultObj.getString("refresh_url");
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(refreshUrl), HTTP.UTF_8);
			for (NameValuePair nameValuePair : params) {
				currentQuery.putParam(nameValuePair.getName(), nameValuePair.getValue());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return currentQuery;
	}

	@Override
	public DataQuery getLoadMoreQuery() {
		DataQuery currentQuery = getCurrentQuery();
		DataResults<TweetItem> results = (DataResults<TweetItem>) getLastResults();
//		results.getRawResponse();
		try {
			JSONObject resultObj = new JSONObject(results.getRawResponse());
			String nextPage = resultObj.getString("next_page");
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(nextPage), HTTP.UTF_8);
			for (NameValuePair nameValuePair : params) {
				currentQuery.putParam(nameValuePair.getName(), nameValuePair.getValue());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return currentQuery;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("q", "appoke");
//        params.put("max-results", "5");
//        params.put("alt", "jsonc");
//        params.put("v", "2");
        DataQuery query = new DataQuery(params);
        
//        DataWorker worker = new DataWorker("http://gdata.youtube.com/feeds/api/standardfeeds/top_rated", parser);
//        setWorker(worker);
//        doGetList(query);
        GreenDataAdapter<TweetItem> adapter = new GreenDataAdapter<TweetItem>(this);
        setListAdapter(adapter);
        doGetList(new GreenRequest("http://search.twitter.com/search.json", params, new DataParser() {
			
			@Override
			public DataResults<TweetItem> parseResult(String wsResponse) {
				DataResults<TweetItem> tweetResults = (DataResults<TweetItem>) super.parseResult(wsResponse);
				final ArrayList<TweetItem> tweetList = new ArrayList<TweetItem>();
				try{
					final JSONObject parser = new JSONObject(wsResponse);
//					final JSONObject root = parser.getJSONObject("data");
					final JSONArray itemArray = parser.getJSONArray("results");
					final int size = itemArray.length();
					for (int i = 0; i < size; i++) {
						final JSONObject item = itemArray.getJSONObject(i);
						final TweetItem tweet = new TweetItem();
						tweet.setTitle(item.getString("text"));
						tweet.setID(item.getString("id_str"));
	//					final JSONObject thumbnailObject = item
	//							.getJSONObject(JSONTag.YOUTUBE_THUMBNAIL);
	//					video.setThumbnailUrl(thumbnailObject
	//							.getString(JSONTag.YOUTUBE_THUMBNAIL_SQ));
						tweetList.add(tweet);
					}
					
					tweetResults.setList(tweetList);
//					mediaResults.setTotalItem(totalItem);
//					mediaResults.setOffset(offset);
//					mediaResults.setLimit(limit);
					return tweetResults;
				}
				catch(JSONException exception){
					return null;
				}
		}}));
    }
}