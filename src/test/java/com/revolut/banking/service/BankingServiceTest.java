package com.revolut.banking.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.dao.BankAccountDAO;
import com.revolut.banking.exception.NonExistingBankAccountException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class BankingServiceTest {
	
	@Mock
	private BankAccountDAO bankAccountdAO;
	
	@Test(expected=NonExistingBankAccountException.class)
	public void whenNoAccountIsFoundExceptionIsThrown() throws NonExistingBankAccountException {
		
		Mockito.when(bankAccountdAO.findByAccountNumber("123")).thenReturn(null);
		final BankingService bankingService = new BankingService(bankAccountdAO);
		bankingService.findByAccountNumber("123");	
	}
	
	@Test
	public void whenAccountisFoundInDAOThenIsCorrectlyReturned() throws NonExistingBankAccountException {

		Mockito.when(bankAccountdAO.findByAccountNumber("123")).thenReturn(new BankAccount("owner", "123", 100d));
		final BankingService bankingService = new BankingService(bankAccountdAO);
		BankAccount foundBankAccount = bankingService.findByAccountNumber("123");	
		Assert.assertEquals(foundBankAccount.getOwner(), "owner");
		Assert.assertEquals(foundBankAccount.getAccountNumber(), "123");
		Assert.assertEquals(foundBankAccount.getBalance(), 100d,0);
	}
	
	@Test
	public void whenListIsRetrievedThenIsCorrectlyReturned() throws NonExistingBankAccountException {
		
		final List<BankAccount> existingBankAccounts = new ArrayList<>(); 
		final BankAccount bankAccount = new BankAccount("owner", "123", 100d);
		existingBankAccounts.add(bankAccount);
		Mockito.when(bankAccountdAO.getAllBankAccounts()).thenReturn(existingBankAccounts);
		final BankingService bankingService = new BankingService(bankAccountdAO);
		final List<BankAccount> foundBankAccounts = bankingService.getAllBankAccounts();
		Assert.assertEquals(foundBankAccounts.size(), 1);
		Assert.assertTrue(foundBankAccounts.contains(bankAccount));
	}
}
