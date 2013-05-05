package com.orma.exception;

@SuppressWarnings("serial")
public class StockManagementException extends Exception {
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public StockManagementException() {
		super();
	}

	public StockManagementException(String message) {
		super(message);
	}
	
	public StockManagementException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public StockManagementException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public StockManagementException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockManagementException(Throwable cause) {
		super(cause);
	}
}
