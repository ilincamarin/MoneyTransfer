package com.revolut.banking.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.exception.NonExistingBankAccountException;
import com.revolut.banking.exception.TransferException;
import com.revolut.banking.service.BankingService;

import org.junit.Assert;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class BankingApiTest {
	
	@Mock
	private BankingService bankingService;
	
	@Test
	public void whenRegisterNewBankAccountThenCorrectResponse() {
		
		final BankingAPI bankingApi = new BankingAPI(bankingService);
		final Response response = bankingApi.registerNewBankAccount("test", "1234", 200d);
		Assert.assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
		Assert.assertEquals(response.getEntity(),"Bank account with number 1234 has been added");
	}

	@Test(expected = NonExistingBankAccountException.class)
	public void whenFindBankAccountThrowsErrorThenStatusInternalError() throws NonExistingBankAccountException {
		
		final BankingAPI bankingApi = new BankingAPI(bankingService);
		Mockito.when(bankingService.findByAccountNumber("1234")).thenThrow(new NonExistingBankAccountException("the bank account does not exist"));
		bankingApi.findByAccountNumber("1234");		
	}
	
	@Test
	public void whenFindBankAccountThenCorrectResponse() throws NonExistingBankAccountException {
		
		final BankingAPI bankingApi = new BankingAPI(bankingService);
		Mockito.when(bankingService.findByAccountNumber("1234")).thenReturn(new BankAccount("test","123",200d));
		final Response response = bankingApi.findByAccountNumber("1234");	
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
		Assert.assertEquals(response.getEntity(),"BankAccount [owner=test, accountNumber=123, balance=200.0]");
	}
	
	@Test(expected = IllegalStateException.class)
	public void whenTransferWithSameAccountsThenExceptionIsThrown() throws TransferException {
		
		final BankingAPI bankingApi = new BankingAPI(bankingService);
		bankingApi.transfer("1234","1234", 200d);
	}
	
	@Test
	public void whenTransferSuccedsThenStatusOK() throws TransferException {
		
		final BankingAPI bankingApi = new BankingAPI(bankingService);
		final Response response = bankingApi.transfer("1234","12345", 200d);
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
		Assert.assertEquals(response.getEntity(),"Transfer succeeded");
	}
	
	@Test
	public void whenGetAllBankAccountsListReturnedThenRightResponse() throws TransferException {
		
		final BankingAPI bankingApi = new BankingAPI(bankingService);
		final List<BankAccount> bankingAccounts = new ArrayList<>();
		final BankAccount existingBankAccount = new BankAccount("test","123", 200d);
		bankingAccounts.add(existingBankAccount);
		Mockito.when(bankingService.getAllBankAccounts()).thenReturn(bankingAccounts);
		final Response response = bankingApi.getAllBankAccounts();
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
		Assert.assertEquals(response.getEntity(),"BankAccount [owner=test, accountNumber=123, balance=200.0]");
	}

}
