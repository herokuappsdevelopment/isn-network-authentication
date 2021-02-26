package com.isn.network.storage;

/**
 * @author tcts-india
 *
 */
public class StorageFileNotFoundException extends StorageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1669996172125273921L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
