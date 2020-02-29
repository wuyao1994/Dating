package com.dating.exception;

/**
 * @author elvis
 */
public class UserNameAlreadyExistException extends RuntimeException {
	public UserNameAlreadyExistException() {
	}

	public UserNameAlreadyExistException(String message) {
		super(message);
	}
}
