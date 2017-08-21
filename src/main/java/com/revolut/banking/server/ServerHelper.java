package com.revolut.banking.server;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.revolut.banking.api.BankingAPI;
import com.revolut.banking.api.BankingExceptionMapper;
import com.revolut.banking.dao.InMemoryBankAccountDAO;
import com.revolut.banking.service.BankingService;
import com.revolut.banking.utils.Settings;
import com.sun.net.httpserver.HttpServer;

/**
 * Helper class for the main class,responsible to instantiate all needed
 * services, handlers and DAOs and also the HttpServer.
 * 
 * @author ilincamarin
 */
public class ServerHelper {

	private static final Logger LOGGER = Logger.getLogger(ServerHelper.class
			.getName());


	public HttpServer initHttpServer() throws IOException {

		LOGGER.log(Level.INFO, "Starting server");
		
		final URI uri = UriBuilder.fromUri("http://" + Settings.getHost() + "/").port(Settings.getPort()).build();

		final InMemoryBankAccountDAO bankAccountDAO = new InMemoryBankAccountDAO();
		final BankingService bankingService = new BankingService(bankAccountDAO);
	
		final ResourceConfig resourceConfig = new ResourceConfig().register(new BankingAPI(bankingService)).register(new BankingExceptionMapper());
		final HttpServer server = JdkHttpServerFactory.createHttpServer(uri, resourceConfig);
		
		LOGGER.log(Level.INFO, "Server started");
		LOGGER.log(Level.INFO, "Starting session consumer");
		LOGGER.log(Level.INFO, "Session consumer started");	
		return server;
	}

}
