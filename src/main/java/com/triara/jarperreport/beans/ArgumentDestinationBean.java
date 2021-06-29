package com.triara.jarperreport.beans;

public class ArgumentDestinationBean {

    private String driver;
    private String localhost;
    private String port;
    private String databaseName;
    private String username;
    private String password;
    private String jasperFilePath;
    private String destinationPathReport;
    private String[] formats;
    private String[] jasperParameters;

    public ArgumentDestinationBean() {
    }

    public ArgumentDestinationBean(
            String driver, String localhost, String port, String databaseName, String username,
            String password, String jasperFilePath, String destinationPathReport, String[] formats,
            String[] jasperParameters) {
        this.driver = driver;
        this.localhost = localhost;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.jasperFilePath = jasperFilePath;
        this.destinationPathReport = destinationPathReport;
        this.formats = formats;
        this.jasperParameters = jasperParameters;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getLocalhost() {
        return localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJasperFilePath() {
        return jasperFilePath;
    }

    public void setJasperFilePath(String jasperFilePath) {
        this.jasperFilePath = jasperFilePath;
    }

    public String getDestinationPathReport() {
        return destinationPathReport;
    }

    public String[] getFormats() {
        return formats;
    }

    public void setFormats(String[] formats) {
        this.formats = formats;
    }

    public void setDestinationPathReport(String destinationPathReport) {
        this.destinationPathReport = destinationPathReport;
    }

    public String[] getJasperParameters() {
        return jasperParameters;
    }

    public void setJasperParameters(String[] jasperParameters) {
        this.jasperParameters = jasperParameters;
    }

    public String toString() {
        String beanString = String.format(
                "driver = %s\n" + "localhost = %s\n" + "port = %s\n" + "databaseName = %s\n" + "username = %s\n" +
                        "password = %s\n" + "jasperFilePath = %s\n" + "destinationPathReport = %s\n" +
                        "formats = %s\n", this.driver, this.localhost, this.port, this.databaseName, this.username,
                this.password, this.jasperFilePath, this.destinationPathReport, this.formats);

        return beanString;

    }

}
