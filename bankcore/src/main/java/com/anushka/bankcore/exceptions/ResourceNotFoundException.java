package com.anushka.bankcore.exceptions;

/**
 * Custom exception for resource not found scenarios. Extends RuntimeException
 * to provide unchecked exception handling.
 */
public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}