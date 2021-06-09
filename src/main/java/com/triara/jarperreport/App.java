package com.triara.jarperreport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.constants.Constants;
import com.triara.jarperreport.utils.ArgumentsUtils;
import com.triara.jarperreport.utils.JdbcUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class App {
	public static void main(String[] args) {
		String fileName = Constants.EMPTY_STRING;
		try {
			ArgumentDestinationBean argBean = ArgumentsUtils.validateArguments(args);
			JdbcUtils.loadJdbcClass(argBean);
			Connection conn = DriverManager.getConnection(JdbcUtils.generateJdbcUrl(argBean), argBean.getUsername(),
					argBean.getPassword());

			JasperPrint jasperPrint = JasperFillManager.fillReport(argBean.getJasperFilePath(), null, conn);
			File pathFileJasper = new File(argBean.getJasperFilePath());
			fileName = pathFileJasper.getName().substring(0, pathFileJasper.getName().lastIndexOf('.'));

			Path baseDestPath = Paths.get(argBean.getDestinationPathReport());
			Path absoluteDestPath = Paths.get(baseDestPath.toString(), fileName);

			String destPathPdfReport = absoluteDestPath.toString().concat(".pdf");
			String destPathHtmlReport = absoluteDestPath.toString().concat(".html");
			String destPathXlsReport = absoluteDestPath.toString().concat(".xls");

			JasperExportManager.exportReportToPdfFile(jasperPrint, destPathPdfReport);
			JasperExportManager.exportReportToHtmlFile(jasperPrint, destPathHtmlReport);

			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			File xlsOutputFile = new File(destPathXlsReport);
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsOutputFile));
			SimpleXlsReportConfiguration xlsConfig = new SimpleXlsReportConfiguration();
			xlsConfig.setDetectCellType(true);
			xlsConfig.setCollapseRowSpan(false);
			xlsExporter.setConfiguration(xlsConfig);
			xlsExporter.exportReport();

		} catch (JRException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
