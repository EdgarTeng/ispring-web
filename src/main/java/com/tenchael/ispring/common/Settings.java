package com.tenchael.ispring.common;

import com.tenchael.ispring.exception.NotFoundException;

import java.util.Properties;

public class Settings {

	private Properties configProperties;

	public String getProperty(final String property) throws NotFoundException {
		String retVal = configProperties.getProperty(property);
		if (retVal == null) {
			throw new NotFoundException("Property not found: " + property);
		}
		return retVal;
	}

	public String getMode() throws NotFoundException {
		return getProperty("mode");
	}

	public String getJdbcDriver() throws NotFoundException {
		return getProperty("jdbc.driver");
	}

	public String getJdbcUrl() throws NotFoundException {
		return getProperty("jdbc.url");
	}

	public String getJdbcUser() throws NotFoundException {
		return getProperty("jdbc.user");
	}

	public String getJdbcPassword() throws NotFoundException {
		return getProperty("jdbc.password");
	}

	public Properties getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(final Properties configProperties) {
		this.configProperties = configProperties;
	}
}
