package com.triara.jarperreport.cmd;

import com.triara.jarperreport.constants.CmdChoices;
import com.triara.jarperreport.constants.Constants;
import org.apache.commons.cli.*;

public class CmdHelper {

    private static final Option DRIVER = Option.builder(Constants.CMD_OPT_DRIVER).hasArg().argName("driver")
            .desc("Driver JDBC de la conexion a la BD").build();

    private static final Option PUERTO = Option.builder(Constants.CMD_PORT).hasArg().argName("port")
            .desc("Numero de puerto a la BD").build();

    private static final Option IP_LOCALHOST = Option.builder(Constants.CMD_IP_LOCALHOST).hasArg().argName(
            "localhost - IP").desc("Localhost o IP de la conexion a la BD").build();

    private static final Option DB_NAME = Option.builder(Constants.CMD_DB_NAME).hasArg().argName("Database name")
            .desc("Nombre de la BD").build();

    private static final Option USER_NAME = Option.builder(Constants.CMD_USER_NAME).hasArg().argName("Username")
            .desc("Username de la conexion a la BD").build();

    private static final Option PASSWORD = Option.builder(Constants.CMD_PASSWORD).hasArg().argName("Password")
            .desc("Password de la conexion a la BD").build();

    private static final Option PATH_JASPER_REPORT = Option.builder(Constants.CMD_PATH_JASPER_REPORT).hasArg().argName(
            "Path Jasper Report").desc("Ruta absoluta del reporte Jasper").build();

    private static final Option PATH_DEST_REPORTS = Option.builder(Constants.CMD_PATH_DEST_REPORTS).hasArg()
            .argName("Dest path created " + "reports")
            .desc("Ruta absoluta en donde se guardaran los reportes").build();

    private static final Option FORMATS = Option.builder(Constants.CMD_FORMATS).hasArgs().argName("Report formats")
            .desc("Formatos de los reportes a exportar").numberOfArgs(Option.UNLIMITED_VALUES).build();

    private static final Option CONNECTION = Option.builder(Constants.CMD_CONNECTION).hasArg(false).argName(
            "Connection").desc("Indicador para la conexion a la BD").build();

    private static final Option LIST_PARAMS = Option.builder(Constants.CMD_GET_LIST_PARAMS).hasArg(false).argName(
            "Param List").desc("Lista de parametros del reporte Jasper").build();

    private static final Option CHECK_DB_CONNECTION = Option.builder(Constants.CMD_CHECK_DB_CONN).hasArg(false).argName(
            "Check DB connection").desc("Verifica la conexion a la Base de datos").build();

    private static Options options;
    private static CommandLine cmd;
    private static HelpFormatter helpFormatter;

    public static void initCmdArguments(String[] args) throws ParseException {

        options = new Options();
        CommandLineParser commandLineParser = new DefaultParser();

        options.addOption(CmdHelper.DRIVER);
        options.addOption(CmdHelper.PUERTO);
        options.addOption(CmdHelper.IP_LOCALHOST);
        options.addOption(CmdHelper.DB_NAME);
        options.addOption(CmdHelper.USER_NAME);
        options.addOption(CmdHelper.PASSWORD);
        options.addOption(CmdHelper.PATH_JASPER_REPORT);
        options.addOption(CmdHelper.PATH_DEST_REPORTS);
        options.addOption(CmdHelper.FORMATS);
        options.addOption(CmdHelper.CONNECTION);
        options.addOption(CmdHelper.LIST_PARAMS);
        options.addOption(CmdHelper.CHECK_DB_CONNECTION);

        CmdHelper.cmd = commandLineParser.parse(options, args);
        CmdHelper.helpFormatter = new HelpFormatter();
    }

    public static CmdChoices validateCmdArgs() {

        if (CmdHelper.getCmd().hasOption(Constants.CMD_CONNECTION)) {
            for (String option : Constants.LIST_NECESSARY_GEN_REPORT_PARAMS) {
                if (!CmdHelper.getCmd().hasOption(option)) {
                    System.err.println(String.format("Parametro '-%s' faltante. Favor de establecerlo.", option));
                    return CmdChoices.OPCION_INDEFINIDA;
                }
            }
            return CmdChoices.OBTENER_REPORTE_JASPER;
        } else if (CmdHelper.getCmd().hasOption(Constants.CMD_GET_LIST_PARAMS)) {
            if (!CmdHelper.getCmd().hasOption(Constants.CMD_PATH_JASPER_REPORT)) {
                System.err.println(String.format("Parametro '-%s' faltante. Favor de establecerlo.",
                        Constants.CMD_PATH_JASPER_REPORT));
                return CmdChoices.OPCION_INDEFINIDA;
            }
            return CmdChoices.OBTENER_LISTA_PARAMETROS_REPORTE_JASPER;
        } else if (CmdHelper.getCmd().hasOption(Constants.CMD_CHECK_DB_CONN)) {
            return CmdChoices.COMPROBAR_CONEXION_BD;
        } else {
            return CmdChoices.OPCION_INDEFINIDA;
        }
    }

    public static void printHelper() {
        CmdHelper.getHelpFormatter().printHelp("jasperReporter", CmdHelper.getOptions());
    }

    public static Options getOptions() {
        return options;
    }

    public static CommandLine getCmd() {
        return cmd;
    }

    public static HelpFormatter getHelpFormatter() {
        return helpFormatter;
    }
}
