package com.chenm.microservice.elasticsearch.repository;

import java.io.Serializable;

@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID>{
	/**
	 * Saves a given entity
	 * @param entity
	 * @return the saved entity
	 */
	<S extends T> S save(S entity);
	
	/**
	 * Saves all given entities
	 * @param entities
	 * @return the saved entities
	 */
	<S extends T> Iterable<S> save(Iterable<S> entities);
	
	/**
	 * Retrives an entity by its id
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 */
	T findOne(ID id);
	
	/**
	 * Returns whether an entity with the given id exists.
	 * @param id must not be {@literal null}.
	 * @return Retrives an entity by its id
	 */
	boolean exists(ID id);
	
	/**
	 * Returns all instances of the type
	 * @return all entities
	 */
	Iterable<T> findAll();
	
	/**
	 * Returns all instances of the type with the given IDs
	 * @param ids
	 * @return
	 */
	Iterable<T> findAll(Iterable<ID> ids);
	
	/**
	 * Returns the number of entities available
	 * @return the number of entities
	 */
	long count();
	
	/**
	 * Deletes the entity with the given id
	 * @param id must not be {@literal null}
	 */
	void delete(ID id);
	
	/**
	 * Deletes a given entity
	 * @param entity
	 */
	void delete(T entity);
	
	/**
	 * Deletes a given entity
	 * @param entities
	 */
	void delete(Iterable<? extends T> entities);
	
	/**
	 * Deletes all entities managed by the repository
	 */
	void deleteAll();
}
