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
import com.greendata.app.GreenDataListActivity;
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
        ArrayAdapter<YouTubeItem> adapter = new ArrayAdapter<YouTubeItem>(this, android.R.layout.simple_list_item_1);
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
	//				mediaResults.setOffset(offset);
	//				mediaResults.setLimit(limit);
					return mediaResults;
				}
				catch(JSONException exception){
					return null;
				}
		}}));
    }
    
    
    private static class TestAdapter extends ArrayAdapter<YouTubeItem> {

		public TestAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			// TODO Auto-generated constructor stub
		}
		@Override
	    public View getView(int position, View convertView, ViewGroup parent)
	    {
			YouTubeItem o = (YouTubeItem) getItem(position);
			TextView textView = new TextView(getContext());
			textView.setText(o.toString());
	        return textView;
	    }

    }
    
//    private static class DemoWorker extends DataWorker {
//
//		public DemoWorker(String restUrl, DataParser parser) {
//			super(restUrl, parser);
//		}
//
//		private DemoWorker(final Parcel in) {
//			mRestUrl = in.readString();
//			mParser = in.readParcelable(DataParser.class.getClassLoader());
//		}
//		
//		@Override
//		public int describeContents() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void writeToParcel(Parcel dest, int flags) {
//			dest.writeString(mRestUrl);
//			dest.writeParcelable(mParser, flags);
//		}
//		
//		public static final Parcelable.Creator<DemoWorker> CREATOR = new Parcelable.Creator<DemoWorker>() {
//			public DemoWorker createFromParcel(final Parcel in) {
//				return new DemoWorker(in);
//			}
//
//			public DemoWorker[] newArray(final int size) {
//				return new DemoWorker[size];
//			}
//		};
//		
//		@Override
//		public Bundle start(Context context, DataQuery query)
//				throws IllegalStateException, IOException, URISyntaxException,
//				RestClientException, JSONException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//    	
//    }
    
//    private class DemoParser implements DataParser {
//
//		@Override
//		public Object parseResults() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public int describeContents() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void writeToParcel(Parcel dest, int flags) {
//			// TODO Auto-generated method stub
//			
//		}
//    	
//    }
    
}