package com.revolut.banking.api;


import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import com.jayway.restassured.http.ContentType;

import com.revolut.banking.server.ServerHelper;
import com.sun.net.httpserver.HttpServer;

public class BankingAPIIntegrationTest {
	
	private HttpServer httpServer;
	
	@Before
	public void setUp() throws IOException {

		final ServerHelper	serverHelper = new ServerHelper();
		httpServer =serverHelper.initHttpServer();
	}
	
	@Test
	public void whenSendingRequestsForAllMethodsRightAnswerReturned() {
				
		given().formParam("owner", "test").formParam("bankAccountNumber", "12344")
		.formParam("balance",200)
		.contentType("application/x-www-form-urlencoded")
		.expect()
		.statusCode(Response.Status.CREATED.getStatusCode())
		.when().put("http://localhost:8081/banking/register");
		
		expect().statusCode(Response.Status.OK.getStatusCode())
		.contentType(ContentType.TEXT)
		.body(containsString("BankAccount [owner=test, accountNumber=12344, balance=200.0]"))
		.when().get("http://localhost:8081/banking/find?accountNumber=12344");
		
		given().formParam("owner", "test1").formParam("bankAccountNumber", "12345")
		.formParam("balance",250)
		.contentType("application/x-www-form-urlencoded")
		.expect()
		.statusCode(Response.Status.CREATED.getStatusCode())
		.when().put("http://localhost:8081/banking/register");
		
		expect().statusCode(Response.Status.OK.getStatusCode())
		.contentType(ContentType.TEXT)
		.body(containsString("BankAccount [owner=test1, accountNumber=12345, balance=250.0]"))
		.when().get("http://localhost:8081/banking/find?accountNumber=12345");
		
		expect().statusCode(Response.Status.OK.getStatusCode())
		.contentType(ContentType.TEXT)
		.body(containsString("BankAccount [owner=test, accountNumber=12344, balance=200.0],BankAccount [owner=test1, accountNumber=12345, balance=250.0]"))
		.when().get("http://localhost:8081/banking/list");
		
		given().formParam("fromAccountNumber", "12345").formParam("toAccountNumber", "12344")
		.formParam("amount",100)
		.contentType("application/x-www-form-urlencoded")
		.expect()
		.statusCode(Response.Status.OK.getStatusCode())
		.when().post("http://localhost:8081/banking/transfer");
		
		expect().statusCode(Response.Status.OK.getStatusCode())
		.contentType(ContentType.TEXT)
		.body(containsString("BankAccount [owner=test, accountNumber=12344, balance=300.0],BankAccount [owner=test1, accountNumber=12345, balance=150.0]"))
		.when().get("http://localhost:8081/banking/list");
	}

	@Test
	public void whenExceptionsAreThrownThenServerInternalErrorReturned() {
		expect().statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
		.contentType(ContentType.TEXT)
		.body(containsString("The requested bank account number does not exist"))
		.when().get("http://localhost:8081/banking/find?accountNumber=12344");
	}
	
	@After
	public void tearDown() {

		httpServer.stop(0);
	}
}
