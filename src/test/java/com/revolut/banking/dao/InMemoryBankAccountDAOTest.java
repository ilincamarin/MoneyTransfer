package com.revolut.banking.dao;

import org.junit.Test;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.exception.NonExistingBankAccountException;
import com.revolut.banking.exception.NotEnoughMoneyException;
import com.revolut.banking.exception.TransferException;

import java.util.List;

import org.junit.Assert;

public class InMemoryBankAccountDAOTest {
	
	@Test
	public void whenRegisterNewAccountThenFindReturnsIt() {
		
		final BankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankAccount newBankAccount = new BankAccount("owner", "656766",200d);
		bankAccountDAO.registerNewBankAccount(newBankAccount);
		final BankAccount foundBankAccount = bankAccountDAO.findByAccountNumber("656766");
		Assert.assertEquals(foundBankAccount.getOwner(), "owner");
		Assert.assertEquals(foundBankAccount.getAccountNumber(), "656766");
		Assert.assertEquals(foundBankAccount.getBalance(),200d,0);
	}
	
	@Test
	public void whenTransferWithCorrectDataThenTransferSucceds() throws TransferException {
		final BankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankAccount fromBankAccount = new BankAccount("owner", "656766",100d);
		final BankAccount toBankAccount = new BankAccount("owner", "65676612344",250d);
		bankAccountDAO.registerNewBankAccount(toBankAccount);
		bankAccountDAO.registerNewBankAccount(fromBankAccount);
		bankAccountDAO.transfer("656766", "65676612344", 50);
		final double fromBankAccountAfterTransfer = bankAccountDAO.findByAccountNumber("656766").getBalance();
		final double toBankAccountAfterTransfer = bankAccountDAO.findByAccountNumber("65676612344").getBalance();
		Assert.assertEquals(fromBankAccountAfterTransfer,50d,0);
		Assert.assertEquals(toBankAccountAfterTransfer,300d,0);
	}
	
	@Test
	public void whenRegisterNewBankAccountListReturnsTheRightAnswer() throws TransferException {
		final BankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankAccount fromBankAccount = new BankAccount("owner", "656766",100d);
		final BankAccount toBankAccount = new BankAccount("owner", "65676612344",250d);
		bankAccountDAO.registerNewBankAccount(toBankAccount);
		bankAccountDAO.registerNewBankAccount(fromBankAccount);
		List<BankAccount> bankAccounts = bankAccountDAO.getAllBankAccounts();
		Assert.assertEquals(bankAccounts.size(), 2);
		Assert.assertTrue(bankAccounts.contains(fromBankAccount));
		Assert.assertTrue(bankAccounts.contains(toBankAccount));
	}
	
	@Test(expected = NonExistingBankAccountException.class)
	public void whenFromBankAccountDoesNotExistExceptionIsThrown() throws TransferException {
		final BankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankAccount toBankAccount = new BankAccount("owner", "65676612344",250d);
		bankAccountDAO.registerNewBankAccount(toBankAccount);
		bankAccountDAO.transfer("656766", "65676612344", 50);
	}
	
	@Test(expected = NonExistingBankAccountException.class)
	public void whenToBankAccountDoesNotExistExceptionIsThrown() throws TransferException {
		final BankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankAccount fromBankAccount = new BankAccount("owner", "65676612344",250d);
		bankAccountDAO.registerNewBankAccount(fromBankAccount);
		bankAccountDAO.transfer("65676612344", "65676", 50);
	}
	
	@Test(expected = NotEnoughMoneyException.class)
	public void whenNotEnoughMoneyExceptionIsThrown() throws TransferException {
		final BankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankAccount fromBankAccount = new BankAccount("owner", "656766",100d);
		final BankAccount toBankAccount = new BankAccount("owner", "65676612344",250d);
		bankAccountDAO.registerNewBankAccount(toBankAccount);
		bankAccountDAO.registerNewBankAccount(fromBankAccount);
		bankAccountDAO.transfer("656766", "65676612344",500);
	}

}
