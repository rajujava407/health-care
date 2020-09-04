/**
 * 
 */
package com.challenge.domain.healthcare.exception;


public class HealthServiceException extends Exception{

	String message;
	String details;
	
	private static final long serialVersionUID = 1L;

	
	public HealthServiceException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public HealthServiceException(String msg){
		this.message=msg;
	}
	
	public HealthServiceException(String msg,String details){
		this.message=msg;
		this.details= details;
	}
	
}
