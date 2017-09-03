package com.chenm.microservice.elasticsearch.repository;

import java.io.Serializable;

import org.elasticsearch.index.query.QueryBuilder;

import com.microservice.common.project.domain.Page;
import com.microservice.common.project.domain.Pageable;

@NoRepositoryBean
public interface ElasticsearchRepository<T, ID extends Serializable> extends ElasticsearchCrudRepository<T, ID> {
	
	<S extends T> S save(S entity);
	
	Iterable<T> search(QueryBuilder query);
	
	Page<T> search(QueryBuilder query, Pageable pageable);
	
//	Page<T> search(SearchQuery searchQuery);
	
}
