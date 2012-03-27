package com.greendata.data.service;

import java.io.IOException;
import java.net.URISyntaxException;
import org.json.JSONException;
import android.content.Intent;
import android.util.Log;
import com.foxykeep.datadroid.exception.RestClientException;
import com.foxykeep.datadroid.service.WorkerService;
import com.greendata.configuration.RequestConfiguration;
import com.greendata.configuration.GreenDataConfiguration;
import com.greendata.data.DataQuery;
import com.greendata.data.worker.DataWorker;

public class DataService extends WorkerService {
	private static final String LOG_TAG = DataService.class.getSimpleName();
	public static final String INTENT_EXTRA_CONFIGURATION_ID = "com.greendata.extras.configuration.id";
	public static final String INTENT_EXTRA_QUERY = "com.greendata.extras.query";
	private static final int MAX_THREADS = 3;

	public DataService() {
		super(MAX_THREADS);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final String configurationId = intent
				.getStringExtra(DataService.INTENT_EXTRA_CONFIGURATION_ID);
		RequestConfiguration configuration = GreenDataConfiguration.getInstance()
				.getConfiguration().get(configurationId);
		if (configuration != null) {
			final DataWorker worker = configuration.getWorker();
			final DataQuery query = configuration.getQuery();
			try {
				sendSuccess(intent, worker.start(this, query));
			} catch (final IllegalStateException e) {
				Log.e(LOG_TAG, "IllegalStateException", e);
				sendConnexionFailure(intent, null);
			} catch (final IOException e) {
				Log.e(LOG_TAG, "IOException", e);
				sendConnexionFailure(intent, null);
			} catch (final URISyntaxException e) {
				Log.e(LOG_TAG, "URISyntaxException", e);
				sendConnexionFailure(intent, null);
			} catch (final RestClientException e) {
				Log.e(LOG_TAG, "RestClientException", e);
				sendConnexionFailure(intent, null);
			} catch (final JSONException e) {
				Log.e(LOG_TAG, "JSONException", e);
				sendDataFailure(intent, null);
			}
			// This block (which should be the last one in your implementation)
			// will catch all the RuntimeException and send you back an error
			// that you can manage. If you remove this catch, the
			// RuntimeException will still crash the PoCService but you will not
			// be
			// informed (as it is in 'background') so you should never remove
			// this
			// catch
			catch (final RuntimeException e) {
				Log.e(LOG_TAG, "RuntimeException", e);
				sendDataFailure(intent, null);
			}
		}
	}
}
