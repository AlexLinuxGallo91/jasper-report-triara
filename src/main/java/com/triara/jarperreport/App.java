package com.triara.jarperreport;

import com.triara.jarperreport.cmd.CmdHelper;
import com.triara.jarperreport.constants.CmdChoices;
import com.triara.jarperreport.constants.Constants;
import com.triara.jarperreport.reporter.JasperReporter;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        String messageError = Constants.EMPTY_STRING;
        boolean executionWasSuccess = true;

        try {
            CmdHelper.initCmdArguments(args);
            CmdChoices cmdChoice = CmdHelper.validateCmdArgs();
            JasperReporter jasperReporter = new JasperReporter();
            jasperReporter.verifyEnumTask(cmdChoice, CmdHelper.getCmd());

        } catch (MissingArgumentException e) {
            messageError = e.getMessage();
            executionWasSuccess = false;
        } catch (ParseException e) {
            messageError = e.getMessage();
            executionWasSuccess = false;
        } catch (JRException e) {
            messageError = e.getMessage();
            executionWasSuccess = false;
        } catch (SQLException e) {
            messageError = e.getMessage();
            executionWasSuccess = false;
        }

        if (!executionWasSuccess) {
            System.out.println(messageError);
            System.exit(1);
        }
    }
}
