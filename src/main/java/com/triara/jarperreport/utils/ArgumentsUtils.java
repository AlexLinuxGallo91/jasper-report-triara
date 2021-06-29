package com.triara.jarperreport.utils;

import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.constants.Constants;
import org.apache.commons.cli.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class ArgumentsUtils {


    public static boolean verifyStringArrayFormats(String[] formats) {

        for (String fileFormat : formats) {
            boolean formatFound = false;
            for (String formatArrayValue : Constants.JASPER_FILE_FORMATS) {
                if (fileFormat.toUpperCase().equals(formatArrayValue)) {
                    formatFound = true;
                    continue;
                }
            }

            if (!formatFound) {
                System.err.println(String.format("Formato \"%s\" desconocido.", fileFormat));
                return false;
            }

        }
        return true;
    }

    public static boolean checkLengthArray(String[] args) {
        return args.length > 0;
    }

    public static boolean verifyCorrectSyntaxisParameters(String[] jasperParameters) {
        boolean result = true;

        for (String parameter : jasperParameters) {
            String[] splitParameters = parameter.split("\\|");

            if (splitParameters.length < 2) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static ArgumentDestinationBean validateArgumentsShowParamsJson(CommandLine cmd) {
        String jasperFilePath = cmd.getOptionValue(Constants.CMD_PATH_JASPER_REPORT);

//      verifica la ruta del reporte jasper
        if (!FileUtils.existFilePath(jasperFilePath)) {
            System.err.println(String.format(
                    "Directorio con reporte jasper %s invalido, favor de verificar la ruta correctamente.",
                    jasperFilePath));
            System.exit(1);
        }

        return new ArgumentDestinationBean(Constants.EMPTY_STRING, Constants.EMPTY_STRING, Constants.EMPTY_STRING,
                Constants.EMPTY_STRING, Constants.EMPTY_STRING, Constants.EMPTY_STRING, jasperFilePath,
                Constants.EMPTY_STRING, new String[]{}, new String[]{});
    }

    public static ArgumentDestinationBean validateArgumentsDatabaseConnection(CommandLine cmd) {

        String driver = cmd.getOptionValue(Constants.CMD_OPT_DRIVER);
        String ipLocalhost = cmd.getOptionValue(Constants.CMD_IP_LOCALHOST);
        String port = cmd.getOptionValue(Constants.CMD_PORT);
        String databaseName = cmd.getOptionValue(Constants.CMD_DB_NAME);
        String username = cmd.getOptionValue(Constants.CMD_USER_NAME);
        String password = cmd.getOptionValue(Constants.CMD_PASSWORD);
        ArgumentDestinationBean argBean;

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

        argBean = new ArgumentDestinationBean(driver, ipLocalhost, port, databaseName, username, password,
                Constants.EMPTY_STRING, Constants.EMPTY_STRING, new String[]{}, new String[]{});

        return argBean;
    }

    public static ArgumentDestinationBean validateArgumentsExportJasperReportWithParameters(CommandLine cmd) {

        String driver = cmd.getOptionValue(Constants.CMD_OPT_DRIVER);
        String ipLocalhost = cmd.getOptionValue(Constants.CMD_IP_LOCALHOST);
        String port = cmd.getOptionValue(Constants.CMD_PORT);
        String databaseName = cmd.getOptionValue(Constants.CMD_DB_NAME);
        String username = cmd.getOptionValue(Constants.CMD_USER_NAME);
        String password = cmd.getOptionValue(Constants.CMD_PASSWORD);
        String jasperFilePath = cmd.getOptionValue(Constants.CMD_PATH_JASPER_REPORT);
        String destinationPathReport = cmd.getOptionValue(Constants.CMD_PATH_DEST_REPORTS);
        String[] formatos = cmd.getOptionValues(Constants.CMD_FORMATS);
        ArgumentDestinationBean argBean;
        String[] parametersJasper = cmd.getOptionValues(Constants.CMD_SET_IN_REPORT_PARAMS);

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
            System.err.println(String.format("Password %s invalido, favor de establecerlo correctamente.", password));
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

//        verifica que se hayan establecido correctamente los formatos a exportar en el reporte
        if (!ArgumentsUtils.verifyStringArrayFormats(formatos)) {
            System.err.println("Favor de establecer correctamente los formatos a exportar el reporte.");
            System.exit(1);
        }

//        verifica que al menos se haya establecido un formato a exportar en el reporte
        if (!ArgumentsUtils.checkLengthArray(formatos)) {
            System.err.println("Favor de establecer al menos un formato a exportar el reporte.");
            System.exit(1);
        }

//        verifica que al menos se haya establecido un parametro
        if (!ArgumentsUtils.checkLengthArray(parametersJasper)) {
            System.err.println("Favor de establecer al menos un parametro.");
            System.exit(1);
        }

//        verifica que los parametros esten correctamente extablecidos y divididos por un pipe
        if(!ArgumentsUtils.verifyCorrectSyntaxisParameters(parametersJasper)){
            System.err.println("Favor de revisar la sintaxis de cada parametro.");
            System.exit(1);
        }

        argBean = new ArgumentDestinationBean(driver, ipLocalhost, port, databaseName, username, password,
                jasperFilePath, destinationPathReport, formatos, parametersJasper);

        return argBean;
    }

    public static ArgumentDestinationBean validateArgumentsExportJasperReport(CommandLine cmd) {

        String driver = cmd.getOptionValue(Constants.CMD_OPT_DRIVER);
        String ipLocalhost = cmd.getOptionValue(Constants.CMD_IP_LOCALHOST);
        String port = cmd.getOptionValue(Constants.CMD_PORT);
        String databaseName = cmd.getOptionValue(Constants.CMD_DB_NAME);
        String username = cmd.getOptionValue(Constants.CMD_USER_NAME);
        String password = cmd.getOptionValue(Constants.CMD_PASSWORD);
        String jasperFilePath = cmd.getOptionValue(Constants.CMD_PATH_JASPER_REPORT);
        String destinationPathReport = cmd.getOptionValue(Constants.CMD_PATH_DEST_REPORTS);
        String[] formatos = cmd.getOptionValues(Constants.CMD_FORMATS);
        ArgumentDestinationBean argBean;

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

//        verifica que se hayan establecido correctamente los formatos a exportar en el reporte
        if (!ArgumentsUtils.verifyStringArrayFormats(formatos)) {
            System.err.println("Favor de establecer correctamente los formatos a exportar el reporte.");
            System.exit(1);
        }

//        verifica que al menos se haya establecido un formato a exportar en el reporte
        if (!ArgumentsUtils.checkLengthArray(formatos)) {
            System.err.println("Favor de establecer al menos un formato a exportar el reporte.");
            System.exit(1);
        }


        argBean = new ArgumentDestinationBean(driver, ipLocalhost, port, databaseName, username, password,
                jasperFilePath, destinationPathReport, formatos, new String[]{});

        return argBean;
    }

    public static Map<String, Object> getParametersFromListArg(String[] listParameters) {
        Map<String, Object> jasperParameters = new HashMap<>();

        for (String paramString : listParameters) {
            String[] splitParameters = paramString.split("\\|");
            jasperParameters.put(splitParameters[0], splitParameters[1]);
        }

        return jasperParameters;
    }

}
