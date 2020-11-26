package org.paddy.power.main;

import static org.paddy.power.utils.Constants.RESOURCE_FILE_PATH;

import org.paddy.power.database.reader.FileRead;
import org.paddy.power.database.writer.ConsoleWrite;
import org.paddy.power.database.writer.TableDrawer;
import org.paddy.power.report.ReportGenerator;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/***
 * Main application class that generate required report from Bet Data.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        PrintWriter pw =  new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
        ReportGenerator rp = new ReportGenerator(new FileRead(RESOURCE_FILE_PATH), new ConsoleWrite(new TableDrawer(pw)));

        rp.readBetDataFromCsv();
        rp.generateReportByLiabilityAndCurrency();
        System.out.println();
        System.out.println();
        rp.generateReportForTotalLiabilityByCurrency();
    }
}
