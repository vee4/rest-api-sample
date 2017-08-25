package com.telecom.hz.sample.exception;

/**
 * 项目同名异常类
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月4日  下午4:47:21
 */
public class IllegalParameterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6470861232133519999L;

	public IllegalParameterException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IllegalParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public IllegalParameterException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IllegalParameterException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IllegalParameterException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
