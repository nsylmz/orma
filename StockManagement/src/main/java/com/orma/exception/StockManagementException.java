package com.orma.exception;

@SuppressWarnings("serial")
public class StockManagementException extends Exception {

	public StockManagementException() {
		super();
	}

	public StockManagementException(String message) {
		super(message);
	}

	public StockManagementException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockManagementException(Throwable cause) {
		super(cause);
	}
}
