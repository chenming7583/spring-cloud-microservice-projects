package com.chenm.microservice.elasticsearch.template;

import javax.naming.directory.SearchResult;

import org.json.JSONObject;

public interface ElasticsearchOperations {

	/**
	 * Create an index for given indexName
	 * @param indexName
	 * @return
	 */
	boolean createIndex(String indexName);
	/**
	 * Delete an index for given indexName
	 * @param indexName
	 * @return
	 */
	boolean deleteIndex(String indexName);
	/**
	 * Delete a typeName for given indexName and typeName
	 * @param indexName
	 * @param typeName
	 * @return
	 */
	boolean deleteType(String indexName, String typeName);
	/**
	 * 搜索推荐服务接口
	 * @param searchResult
	 * @param queryWord
	 * @return
	 */
	public JSONObject recommend(SearchResult searchResult, String queryWord);
	
	
}
