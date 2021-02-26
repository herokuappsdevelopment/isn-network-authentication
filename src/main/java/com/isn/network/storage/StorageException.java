package com.isn.network.storage;

/**
 * @author tcts-india
 *
 */
public class StorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6996425603639441950L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
