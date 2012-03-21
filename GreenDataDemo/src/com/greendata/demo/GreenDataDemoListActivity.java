package com.greendata.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.greendata.app.GreenDataListActivity;
import com.greendata.data.DataParser;
import com.greendata.data.DataQuery;
import com.greendata.data.service.DataService;
import com.greendata.data.worker.DataWorker;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

public class GreenDataDemoListActivity extends GreenDataListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        worker = new DataWorker(endpoint, parser);
//        configuration = new Configuration();
//        GreenList mListView = new GreenList(worker, configuration);
//        mListView = (GreenList) findViewById(R.id.green_list);
//        mListView.setWorker(worker);
//        mListView.setConfiguration(configuration);
//        final Intent intent = new Intent(this, LocalService.class);
//        Bundle bundle = new Bundle();
//        bundle.putCharSequence("extraData", "test");
//        intent.putExtras(bundle);
//        startService(intent);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));
        DataParser parser = new DataParser();
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("start-index", "1");
        params.put("max-results", "5");
        DataQuery query = new DataQuery(params);
        
        DataWorker worker = new DataWorker("https://gdata.youtube.com/feeds/api/standardfeeds/top_rated", parser);
        setWorker(worker);
        doGetList(query);
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