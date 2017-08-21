package com.revolut.banking.dao;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.exception.NonExistingBankAccountException;
import com.revolut.banking.exception.NotEnoughMoneyException;
import com.revolut.banking.exception.TransferException;

public class InMemoryBankAccountDAO implements BankAccountDAO {
	
	private final ConcurrentMap<String, BankAccount> bankAccounts;
	
	public InMemoryBankAccountDAO() {
		
		bankAccounts = new ConcurrentHashMap<>();
	}

	@Override
	public void registerNewBankAccount(BankAccount bankAccount) {
		bankAccounts.putIfAbsent(bankAccount.getAccountNumber(), bankAccount);
	}

	@Override
	public List<BankAccount> getAllBankAccounts() {
		return bankAccounts.values().stream().collect(Collectors.toList());
	}

	@Override
	public void transfer(String fromAccountNumber, String toAccountNumber, double amount) throws TransferException {
		
		if (!bankAccounts.containsKey(fromAccountNumber)) {
			throw new NonExistingBankAccountException("The origin bank account does not exist.");
		}
		if (!bankAccounts.containsKey(toAccountNumber)) {
			throw new NonExistingBankAccountException("The to bank account does not exist.");
		}
		
		BankAccount fromBankAccount = bankAccounts.get(fromAccountNumber);
		if (amount > fromBankAccount.getBalance()) {
			throw new NotEnoughMoneyException("Not enough money exception");
		}
		fromBankAccount.withdraw(amount);
		BankAccount toBankAccount = bankAccounts.get(toAccountNumber);
		toBankAccount.deposit(amount);
	}

	@Override
	public BankAccount findByAccountNumber(String bankAccountNumber) {
		
		return bankAccounts.get(bankAccountNumber);
	}

}
