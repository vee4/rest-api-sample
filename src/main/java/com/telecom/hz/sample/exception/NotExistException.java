package com.telecom.hz.sample.exception;

/**
 * 不存在异常
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月4日  下午4:58:11
 */
public class NotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4083370511917961180L;

	public NotExistException() {
		super();
	}

	public NotExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
