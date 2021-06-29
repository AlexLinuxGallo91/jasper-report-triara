package com.triara.jarperreport.utils;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.constants.Constants;

public class JdbcUtils {

	public static boolean isValidPort(String port) {
		boolean isValid = true;

		try {
			Integer.parseInt(port);
		} catch (NumberFormatException e) {
			isValid = false;
		}

		return isValid;
	}

	public static boolean isValidDriver(String driver) {
		driver = driver.toUpperCase();
		return Constants.DRIVER_LIST.contains(driver);
	}

	public static boolean isValidIp(String ipLocalhost) {
		boolean isValid = true;
		String ip = ipLocalhost.toLowerCase();

		if (ip.equals("localhost")) {
			return isValid;
		}

		InetAddressValidator inetValidator = InetAddressValidator.getInstance();
		isValid = inetValidator.isValid(ip);

		return isValid;
	}

	public static boolean isValidString(String param) {

		if (param.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String generateJdbcUrl(ArgumentDestinationBean argBean) {
		String urlJdbc = "jdbc:";

		if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_MSSQL)) {
			urlJdbc = urlJdbc.concat("sqlserver://%s:%s;databaseName=%s;");
		} else if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_MYSQL)) {
			urlJdbc = urlJdbc.concat("mysql://%s:%s/%s");
		} else if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_POSTGRESQL)) {
			urlJdbc = urlJdbc.concat("postgresql://%s:%s/%s");
		} else if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_ORACLE)) {
			urlJdbc = urlJdbc.concat("oracle:thin:@%s:%s:%s");
		}

		urlJdbc = String.format(urlJdbc, argBean.getLocalhost(), argBean.getPort(), argBean.getDatabaseName());

		return urlJdbc;
	}

	public static Class<?> loadJdbcClass(ArgumentDestinationBean argBean) {

		Class<?> classLoaded = null;

		try {
			if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_MSSQL)) {
				classLoaded = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} else if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_MYSQL)) {
				classLoaded = Class.forName("com.mysql.cj.jdbc.Driver");
			} else if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_POSTGRESQL)) {
				classLoaded = Class.forName("org.postgresql.Driver");
			} else if (argBean.getDriver().toUpperCase().equals(Constants.DRIVER_ORACLE)) {
				classLoaded = Class.forName("oracle.jdbc.driver.OracleDriver");
			}

		} catch (ClassNotFoundException e) {
			System.err.println(String.format("Sucedio un error al intentar cargar la clase del JDBC para el driver %s",
					argBean.getDriver()));
			System.exit(1);
		}

		return classLoaded;
	}
}
