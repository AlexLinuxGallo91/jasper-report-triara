package com.triara.jarperreport.utils;

import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.constants.Constants;

public class ArgumentsUtils {

	public static ArgumentDestinationBean validateArguments(String[] args) {

		String driver;
		String ipLocalhost;
		String port;
		String databaseName;
		String username;
		String password;
		String jasperFilePath;
		String destinationPathReport;
		ArgumentDestinationBean argBean;

//		verifica que se hayan establecido los 8 argumentos

		if (args.length < 8) {
			System.err.println(String.format(
					"Favor de establecer los 8 argumentos necesarios, solo se establecieron %d argumentos",
					args.length));

			System.err.println("Argumentos necesarios: \n" + "1. Driver\n" + "2. IP/Localhost\n" + "3. Port\n"
					+ "4. Database Name\n" + "5. Username\n" + "6. Password\n" + "7. Jasper File Path\n"
					+ "8. Destination Path Reports");

			System.exit(1);
		}

//		verifica que ninguna variable sea null
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				args[i] = Constants.EMPTY_STRING;
			}
		}

		driver = args[0];
		ipLocalhost = args[1];
		port = args[2];
		databaseName = args[3];
		username = args[4];
		password = args[5];
		jasperFilePath = args[6];
		destinationPathReport = args[7];

//		verifica el driver
		if (!JdbcUtils.isValidDriver(driver)) {
			System.err.println(
					String.format("Driver %s invalido, favor de establece alguno de los siguientes drivers: %s", driver,
							Constants.DRIVER_LIST.toString()));
			System.exit(1);
		}

//		verifica la ip o localhost
		if (!JdbcUtils.isValidIp(ipLocalhost)) {
			System.err.println(
					String.format("Ip %s invalido. Favor de establecer una direccion ip valido.", ipLocalhost));
			System.exit(1);
		}

//		verifica el puerto
		if (!JdbcUtils.isValidPort(port)) {
			System.err.println("Favor de establecer solo digitos en el parametro port.");
			System.exit(1);
		}

//		verifica el nombre de la BD
		if (!JdbcUtils.isValidString(databaseName)) {
			System.err.println(
					String.format("Databasename %s invalido, favor de establecerlo correctamente.", databaseName));
			System.exit(1);
		}

//		verifica el nombre del usuario
		if (!JdbcUtils.isValidString(username)) {
			System.err.println(String.format("Username %s invalido, favor de establecerlo correctamente.", username));
			System.exit(1);
		}

//		verifica el password de la BD
		if (!JdbcUtils.isValidString(password)) {
			System.err.println(String.format("password %s invalido, favor de establecerlo correctamente.", password));
			System.exit(1);
		}

//		verifica la ruta del reporte jasper
		if (!FileUtils.existFilePath(jasperFilePath)) {
			System.err.println(String.format(
					"Directorio con reporte jasper %s invalido, favor de verificar la ruta correctamente.",
					jasperFilePath));
			System.exit(1);
		}

//		verifica que exista el path de destino, en caso contrario se trara de crearlo. En caso de falla, se detiene
//		la ejecucion del script

		if (!FileUtils.existDir(destinationPathReport, true)) {
			System.err.println(String
					.format("Directorio destino para la creacion del reporte %s invalido o sin posibilidad de crearlo, "
							+ "favor de verificar la ruta y permisos correctamente.", destinationPathReport));
			System.exit(1);
		}

		argBean = new ArgumentDestinationBean(driver, ipLocalhost, port, databaseName, username, password,
				jasperFilePath, destinationPathReport);

		return argBean;
	}

}
