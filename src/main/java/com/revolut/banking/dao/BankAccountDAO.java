package com.revolut.banking.dao;

import java.util.List;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.exception.TransferException;

/**
 * Interface for a BankAccountDAO.
 * 
 * @author ilincamarin
 * 
 */
public interface BankAccountDAO {
	
	/** 
	 * Registers a new account.
	 * @param bankAccount
	 */
	void registerNewBankAccount(BankAccount bankAccount);
	
	/**
	 * Retrieves list of bank accounts.
	 * @return List of all current bank accounts.
	 */
	List<BankAccount> getAllBankAccounts();
	
	/**
	 * Transfer money between two bank accounts.
	 * @param fromAccountNumber
	 * @param toBankAccountNumber
	 * @param amount
	 * @throws TransferException
	 */
	void transfer(String fromAccountNumber, String toBankAccountNumber, double amount) throws TransferException;

	/**
	 * Retrieves a BankAccount by his bank account number.
	 * @param bankAccountNumber
	 * @return found bank account
	 */
	BankAccount findByAccountNumber(String bankAccountNumber);
}
