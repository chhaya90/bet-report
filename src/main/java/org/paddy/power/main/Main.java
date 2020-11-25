package org.paddy.power.main;

import static org.paddy.power.utils.Constants.RESOURCE_FILE_PATH;

import org.paddy.power.database.reader.FileRead;
import org.paddy.power.database.writer.ConsoleWrite;
import org.paddy.power.report.ReportGenerator;

/***
 * Main application class that generate required report from Bet Data.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ReportGenerator rp = new ReportGenerator(new FileRead(RESOURCE_FILE_PATH), new ConsoleWrite());
        rp.readBetDataFromCsv();
        rp.reportGeneratedByLiabilityAndCurrency();
        System.out.println();
        System.out.println();
        rp.reportGeneratedForTotalLiabilityByCurrency();

    }
}
