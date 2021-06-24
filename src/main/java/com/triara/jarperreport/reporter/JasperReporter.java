package com.triara.jarperreport.reporter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.beans.JasperParamJsonBean;
import com.triara.jarperreport.cmd.CmdHelper;
import com.triara.jarperreport.constants.CmdChoices;
import com.triara.jarperreport.constants.Constants;
import com.triara.jarperreport.db_connection.DatabaseConnection;
import com.triara.jarperreport.utils.ArgumentsUtils;
import com.triara.jarperreport.utils.FileUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class JasperReporter {

    private JasperPrint jasperPrint;
    private File jasperFilePath;
    private File jasperDestDirPath;
    private String fileName;

    public JasperReporter() {
    }

    public void generateReports(ArgumentDestinationBean bean) throws SQLException, JRException {
        DatabaseConnection.initDbConnection(bean);

        this.jasperFilePath = new File(bean.getJasperFilePath());
        this.jasperDestDirPath = new File(bean.getDestinationPathReport());

        this.jasperPrint = JasperFillManager.fillReport(bean.getJasperFilePath(), null,
                DatabaseConnection.getDbConnection());

        this.fileName = FileUtils.getFileNameWithoutExt(this.jasperFilePath.getName());

        for (String argReportFormat : bean.getFormats()) {
            if (argReportFormat.trim().equalsIgnoreCase(Constants.PDF_EXT_FILE)) {
                this.generatePdfReport();
            } else if (argReportFormat.trim().equalsIgnoreCase(Constants.HTML_EXT_FILE)) {
                this.generateHtmlReport();
            } else if (argReportFormat.trim().equalsIgnoreCase(Constants.CSV_EXT_FILE)) {
                this.generateCsvReport();
            }
        }

        DatabaseConnection.closeConnection();
    }

    private void generatePdfReport() throws JRException {
        String destPathFilePdf = Paths.get(this.jasperDestDirPath.getAbsolutePath(),
                this.fileName).toString().concat(".pdf");
        JasperExportManager.exportReportToPdfFile(this.jasperPrint, destPathFilePdf);
    }

    private void generateHtmlReport() throws JRException {
        String destPathFileHtml = Paths.get(this.jasperDestDirPath.getAbsolutePath(),
                this.fileName).toString().concat(".html");
        JasperExportManager.exportReportToHtmlFile(this.jasperPrint, destPathFileHtml);
    }

    private void generateCsvReport() throws JRException {
        File xlsOutputReport = new File(Paths.get(
                this.jasperDestDirPath.getAbsolutePath(), this.fileName).toString().concat(".csv"));
        JRXlsExporter xlsExporter = new JRXlsExporter();

        xlsExporter.setExporterInput(new SimpleExporterInput(this.jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsOutputReport));
        SimpleXlsReportConfiguration xlsConfig = new SimpleXlsReportConfiguration();
        xlsConfig.setDetectCellType(true);
        xlsConfig.setCollapseRowSpan(false);
        xlsExporter.setConfiguration(xlsConfig);

        xlsExporter.exportReport();
    }

    public String getJasperReportParamsJson(ArgumentDestinationBean bean) throws JRException {
        this.jasperFilePath = new File(bean.getJasperFilePath());
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.jasperFilePath);
        JRParameter[] listParams = jasperReport.getParameters();

        Gson jsonResult = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<JasperParamJsonBean> beanList = new ArrayList<>();

        for (JRParameter param : listParams) {
            if (!param.isSystemDefined() && param.isForPrompting()) {
                beanList.add(new JasperParamJsonBean(
                        param.getName(), param.getDescription(), param.getValueClassName()));
            }
        }

        return jsonResult.toJson(beanList);
    }

    public void verifyEnumTask(CmdChoices cmdChoice, CommandLine cmd) throws JRException, SQLException {
        ArgumentDestinationBean bean;

        switch (cmdChoice) {
            case OBTENER_REPORTE_JASPER:
                bean = ArgumentsUtils.validateArgumentsExportJasperReport(cmd);
                this.generateReports(bean);
                break;
            case OBTENER_LISTA_PARAMETROS_REPORTE_JASPER:
                bean = ArgumentsUtils.validateArgumentsShowParamsJson(cmd);
                System.out.println(this.getJasperReportParamsJson(bean));
                break;
            case OPCION_INDEFINIDA:
                CmdHelper.printHelper();
                System.exit(1);
                break;
        }

    }

}