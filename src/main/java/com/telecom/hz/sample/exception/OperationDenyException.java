package com.telecom.hz.sample.exception;

public class OperationDenyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3903644707175756429L;

	public OperationDenyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperationDenyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public OperationDenyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OperationDenyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public OperationDenyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
