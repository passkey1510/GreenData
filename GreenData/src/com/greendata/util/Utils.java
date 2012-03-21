package com.greendata.util;

import java.net.URLEncoder;
import java.util.SortedMap;

public class Utils {
	public static String getSortedQueryString(SortedMap<String, String> params) {
		try {
			final StringBuffer sb = new StringBuffer();
			sb.append("");
			for (String key : params.keySet()) {
				sb.append("=");
				sb.append(URLEncoder.encode(params.get(key), "UTF-8"));
				sb.append("&");
			}
			return sb.toString();
		} finally {
			return "";
		}
	}
}
