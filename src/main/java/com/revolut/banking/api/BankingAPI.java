package com.revolut.banking.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.banking.bean.BankAccount;
import com.revolut.banking.exception.NonExistingBankAccountException;
import com.revolut.banking.exception.TransferException;
import com.revolut.banking.service.BankingService;

/**
 * Class that contains all methods for the REST API.
 * It contains methods for register, finding, listing and transfering money between
 * accounts.
 * 
 * @author ilincamarin
 * 
 */
@Path("/banking")
public class BankingAPI {
	
	private final BankingService bankingService;
	
	public BankingAPI(final BankingService bankingService) {
		this.bankingService = bankingService;
	}
	
	@PUT
	@Path("/register")
	public Response registerNewBankAccount(@FormParam("owner") final String owner,@FormParam("bankAccountNumber") final String bankAccountNumber,@FormParam("balance") final Double balance) {
		bankingService.registerNewBankAccount(new BankAccount(owner,bankAccountNumber, balance));
		return Response.status(Response.Status.CREATED).entity("Bank account with number "+ bankAccountNumber +" has been added").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/list")
	public Response getAllBankAccounts() {
		final List<BankAccount> bankAccounts = bankingService.getAllBankAccounts();
		final String result = bankAccounts.stream().map(e -> e.toString()).collect(Collectors.joining(","));
		return Response.status(Response.Status.OK).entity(result).build();
	}
	
	@POST
	@Path("/transfer")
	public Response transfer(@FormParam("fromAccountNumber") String fromAccountNumber,@FormParam("toAccountNumber") String toBankAccountNumber,@FormParam("amount") double amount) throws TransferException {
		
		if (fromAccountNumber.equals(toBankAccountNumber)) {
			throw new IllegalStateException("From and to bank account are the same");
		}
		bankingService.transfer(fromAccountNumber, toBankAccountNumber, amount);
		return Response.status(Response.Status.OK).entity("Transfer succeeded").build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/find")
	public Response findByAccountNumber(@QueryParam("accountNumber") String bankAccountNumber) throws NonExistingBankAccountException  {
	
		final BankAccount bankAccount = bankingService.findByAccountNumber(bankAccountNumber);	
		return Response.status(Response.Status.OK).entity(bankAccount.toString()).build();
	}
}
