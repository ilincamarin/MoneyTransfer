package com.revolut.banking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that reads entries from config.properties and internalconfig.properties
 * files.
 * 
 * @author ilincamarin
 * 
 */
public class Settings {

	private static final Properties properties = new Properties();

	private static final String PROPERTIES_FILE = "config.properties";

	private static final Logger LOGGER = Logger.getLogger(Settings.class
			.getName());

	static {

		try (final InputStream propertiesInputStream = ClassLoader
				.getSystemClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties.load(propertiesInputStream);

		} catch (IOException e) {

			LOGGER.log(Level.SEVERE,
					"Exception occured while reading properties file", e);
		}
	}

	/**
	 * @return Port for the HttpServer to start.
	 */
	public static int getPort() {

		return Integer.valueOf(properties.getProperty("port"));
	}

	/**
	 * @return Host for the HttpServer to be started.
	 */
	public static String getHost() {

		return properties.getProperty("host");
	}

}
