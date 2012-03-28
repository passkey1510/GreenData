package com.greendata.data;

public interface DataPager {
	public DataQuery buildFirstPageQuery(DataQuery query);
	public DataQuery buildNextPageQuery(DataQuery query);
	public DataQuery buildPreviousPageQuery(DataQuery query);
	public DataQuery buildRefreshQuery(DataQuery query);
}
