package com.revolut.banking.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class that starts a {@HttpServer}.
 * 
 * @author ilincamarin
 */
public class Server {

	private static final Logger LOGGER = Logger.getLogger(Server.class
			.getName());

	public static void main(String args[]) throws URISyntaxException,
			IOException {

		try {
		
			final ServerHelper serverHelper = new ServerHelper();
			serverHelper.initHttpServer();

		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "Error creating server", e);
		}
	}

}
