package com.microservice.common.project.domain;

import java.io.Serializable;
import java.util.Iterator;

import org.springframework.util.StringUtils;

public class Sort<T> implements Iterable<T>,Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2410643616880182202L;

	public static final Direction DEFAULT_DIRECTION = Direction.ASC; 
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Enumeration for sort directions
	 * @author chenm
	 *
	 */
	public static enum Direction{
		
		ASC, DESC;
		
		/**
		 * Returns the {@link Direction} enum for the given {@link String} value
		 * @param value
		 * @return
		 */
		public static Direction fromString(String value){
			try {
				return Direction.valueOf(value);
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
			}
		}
	}
	
	public static class Order implements Serializable{

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 6050114102818988129L;
		
		private final Direction direction;
		
		private final String property;

		public Order(Direction direction, String property) {
		   if (!StringUtils.hasText(property)) { 
			    throw new IllegalArgumentException("Property must not null or empty!"); 
			   } 
			 
			   this.direction = direction == null ? DEFAULT_DIRECTION : direction; 
			   this.property = property; 
		}
		
	}

}
