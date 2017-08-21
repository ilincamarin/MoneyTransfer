package com.revolut.banking.exception;

public class NonExistingBankAccountException extends TransferException{

	private static final long serialVersionUID = 8883194900672446555L;
	
	public NonExistingBankAccountException(final String message) {
		
		super(message);
	}

}
