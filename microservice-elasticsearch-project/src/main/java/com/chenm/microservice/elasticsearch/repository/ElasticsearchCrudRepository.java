package com.chenm.microservice.elasticsearch.repository;

import java.io.Serializable;

@NoRepositoryBean
public interface ElasticsearchCrudRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>{

}
