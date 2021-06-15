package com.gg.server.config.Exception;

public class ErrorCodeException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 3090049857043017753L;

	/** 编码*/
	private int code;

	/** 错误信息*/
	private String errorMsg;

	private ErrorCodeException(String message) {
		super(message);
	}

	public static ErrorCodeException valueOf(String errorMsg) {
		ErrorCodeException e = new ErrorCodeException(errorMsg);
		e.code = 500;
		e.errorMsg = errorMsg;
		return e;
	}

	public static ErrorCodeException valueOf(int code,String errorMsg) {
		ErrorCodeException e = new ErrorCodeException(errorMsg);
		e.code = code;
		e.errorMsg = errorMsg;
		return e;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

