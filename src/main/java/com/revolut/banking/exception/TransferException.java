package com.revolut.banking.exception;

public class TransferException extends Exception {

	private static final long serialVersionUID = 7617998600900135953L;
	
	public TransferException(final String message) {
		super(message);
	}

}
