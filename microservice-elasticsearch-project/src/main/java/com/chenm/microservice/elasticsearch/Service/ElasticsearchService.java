package com.chenm.microservice.elasticsearch.Service;

import javax.naming.directory.SearchResult;

import org.json.JSONObject;

public interface ElasticsearchService {
	/**
	 * 搜索推荐服务接口
	 * @param searchResult
	 * @param queryWord
	 * @return
	 */
	public JSONObject recommend(SearchResult searchResult, String queryWord);
}
