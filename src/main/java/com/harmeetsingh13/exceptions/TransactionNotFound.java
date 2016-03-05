/**
 * 
 */
package com.harmeetsingh13.exceptions;

/**
 * @author Harmeet Singh(Taara)
 *
 */

public class TransactionNotFound extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4224217556613151468L;
	
	private String errorCode;
	
	public TransactionNotFound(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
}
