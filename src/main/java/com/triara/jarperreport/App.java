package com.triara.jarperreport;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.constants.Constants;
import com.triara.jarperreport.utils.ArgumentsUtils;
import com.triara.jarperreport.utils.JdbcUtils;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class App {
	public static void main(String[] args) {
		String fileName = Constants.EMPTY_STRING;
		try {
			ArgumentDestinationBean argBean = ArgumentsUtils.validateArguments(args);
			JdbcUtils.loadJdbcClass(argBean);
			Connection conn = DriverManager.getConnection(JdbcUtils.generateJdbcUrl(argBean), argBean.getUsername(),
					argBean.getPassword());

			JasperPrint jasperPrint = JasperFillManager.fillReport(argBean.getJasperFilePath(), null, conn);
			fileName = jasperPrint.getName();
			Path baseDestPath = Paths.get(argBean.getDestinationPathReport());
			Path absoluteDestPath = Paths.get(baseDestPath.toString(), fileName);
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, absoluteDestPath.toString().concat(".pdf"));
			JasperExportManager.exportReportToHtmlFile(jasperPrint, absoluteDestPath.toString().concat(".html"));

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
