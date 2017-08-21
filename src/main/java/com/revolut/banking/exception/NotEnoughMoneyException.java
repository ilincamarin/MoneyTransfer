package com.revolut.banking.exception;

public class NotEnoughMoneyException extends TransferException {

	private static final long serialVersionUID = -6158119454439528866L;

	public NotEnoughMoneyException(String message) {
		super(message);
	}
}
