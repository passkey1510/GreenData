package com.greendata.configuration;

import com.greendata.data.DataQuery;
import com.greendata.data.worker.DataWorker;

public class RequestConfiguration {
	private DataWorker mWorker;
	private DataQuery mQuery;
	
	public RequestConfiguration() {
		super();
	}
	
	public RequestConfiguration(DataWorker worker, DataQuery query) {
		mWorker = worker;
		mQuery = query;
	}
	
	/**
	 * @return the mWorker
	 */
	public DataWorker getWorker() {
		return mWorker;
	}

	/**
	 * @param mWorker the mWorker to set
	 */
	public void setWorker(DataWorker worker) {
		mWorker = worker;
	}

	/**
	 * @return the mQuery
	 */
	public DataQuery getQuery() {
		return mQuery;
	}

	/**
	 * @param mQuery the mQuery to set
	 */
	public void setQuery(DataQuery query) {
		mQuery = query;
	}
	
	public String getId() {
		return mWorker.hashCode() + "@" + mQuery.hashCode();
	}
}
