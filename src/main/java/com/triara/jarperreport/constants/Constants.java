package com.triara.jarperreport.constants;

import com.triara.jarperreport.cmd.CmdHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final List<String> DRIVER_LIST = new ArrayList<String>(
            Arrays.asList(new String[]{"MYSQL", "POSTGRESQL", "MSSQL", "ORACLE"}));

    public static final String EMPTY_STRING = "";

    public static final String DRIVER_MYSQL = "MYSQL";
    public static final String DRIVER_POSTGRESQL = "POSTGRESQL";
    public static final String DRIVER_MSSQL = "MSSQL";
    public static final String DRIVER_ORACLE = "ORACLE";

    // constantes desde el CMD Helper
    public static final String CMD_OPT_DRIVER = "d";
    public static final String CMD_PORT = "p";
    public static final String CMD_IP_LOCALHOST = "l";
    public static final String CMD_DB_NAME = "dbn";
    public static final String CMD_USER_NAME = "u";
    public static final String CMD_PASSWORD = "pass";
    public static final String CMD_PATH_JASPER_REPORT = "f";
    public static final String CMD_PATH_DEST_REPORTS = "fd";
    public static final String CMD_FORMATS = "format";
    public static final String CMD_CONNECTION = "conn";
    public static final String CMD_GET_LIST_PARAMS = "params";
    public static final String CMD_CHECK_DB_CONN = "checkdbconn";

    public static final String[] JASPER_FILE_FORMATS = new String[]{"PDF", "HTML", "CSV"};

    public static final String PDF_EXT_FILE = "PDF";
    public static final String HTML_EXT_FILE = "HTML";
    public static final String CSV_EXT_FILE = "CSV";

    public static final String[] LIST_NECESSARY_GEN_REPORT_PARAMS = new String[]{
            Constants.CMD_OPT_DRIVER,
            Constants.CMD_IP_LOCALHOST,
            Constants.CMD_PORT,
            Constants.CMD_DB_NAME,
            Constants.CMD_USER_NAME,
            Constants.CMD_PASSWORD,
            Constants.CMD_PATH_JASPER_REPORT,
            Constants.CMD_PATH_DEST_REPORTS,
            Constants.CMD_FORMATS
    };

}
