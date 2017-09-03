package com.chenm.microservice.elasticsearch.repository;

import java.io.Serializable;

import com.microservice.common.project.domain.Page;
import com.microservice.common.project.domain.Pageable;
import com.microservice.common.project.domain.Sort;


public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID>{
	
	/**
	 * Returns all entities sorted by the given options
	 * @param sort
	 * @return all entities sorted by the given options
	 */
	Iterable<T> findAll(Sort sort);
	
	 /**
	  * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object. 
	  *  
	  * @param pageable 
	  * @return a page of entities 
	  */
	Page<T> findAll(Pageable pageable);
}
