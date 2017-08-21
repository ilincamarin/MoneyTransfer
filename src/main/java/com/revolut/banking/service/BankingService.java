package com.revolut.banking.service;

import java.util.List;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.dao.BankAccountDAO;
import com.revolut.banking.exception.NonExistingBankAccountException;
import com.revolut.banking.exception.TransferException;

public class BankingService {
	
	private final BankAccountDAO bankAccountDAO;

	public BankingService(final BankAccountDAO bankAccountDAO) {
		
	 this.bankAccountDAO = bankAccountDAO;
	}
	
	public void registerNewBankAccount(BankAccount bankAccount) {

		bankAccountDAO.registerNewBankAccount(bankAccount);	
	}
	
	public void transfer(String fromAccountNumber, String toBankAccountNumber, double amount) throws TransferException {
	
		bankAccountDAO.transfer(fromAccountNumber,toBankAccountNumber,amount);	
	}
	
	public List<BankAccount> getAllBankAccounts() {
	
		return bankAccountDAO.getAllBankAccounts();
	}

	public BankAccount findByAccountNumber(final String bankAccountNumber) throws NonExistingBankAccountException {
	
		final BankAccount bankAccount = bankAccountDAO.findByAccountNumber(bankAccountNumber);
		if (bankAccount == null) {
			throw new NonExistingBankAccountException("The requested bank account number does not exist");
		}
		return bankAccount;
	}
}
