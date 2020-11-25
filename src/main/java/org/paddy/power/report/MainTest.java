package org.paddy.power.report;

import org.paddy.power.database.reader.FileRead;
import org.paddy.power.database.writer.ConsoleWrite;

public class MainTest {

    public static void main(String[] args) throws Exception {
     ReportGenerator rp =  new ReportGenerator(new FileRead(),new ConsoleWrite());
     rp.readBetDataFromCsv();
     rp.reportGeneratedByLiabilityAndCurrency();
        System.out.println();
        System.out.println();
     rp.reportGeneratedForTotalLiabilityByCurrency();


    }
}
