package com.chenm.microservice.elasticsearch.template;

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
}
