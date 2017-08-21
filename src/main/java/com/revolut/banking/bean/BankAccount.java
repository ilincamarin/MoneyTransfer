package com.revolut.banking.bean;

/**
 * POJO that represent a bank account.
 * 
 * @author ilincamarin
 * 
 */
public class BankAccount {
	
	private final String owner;

	private final String accountNumber;
	
	private Double balance;
	
	public BankAccount(String owner, String accountNumber, Double balance) {
		super();
		this.owner = owner;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	
	public String getOwner() {
		return owner;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public Double getBalance() {
		return balance;
	}
	
	public void deposit(double amount) {
		this.balance += amount;
	}
	
	public void withdraw(double amount) {
		this.balance -=amount;
	}
	
	@Override
	public String toString() {
		return "BankAccount [owner=" + owner + ", accountNumber=" + accountNumber + ", balance=" + balance
				 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
}
