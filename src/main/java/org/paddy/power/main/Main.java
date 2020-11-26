package org.paddy.power.main;

import static org.paddy.power.utils.Constants.RESOURCE_FILE_PATH;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.paddy.power.database.reader.FileRead;
import org.paddy.power.database.writer.ConsoleWrite;
import org.paddy.power.database.writer.TableDrawer;
import org.paddy.power.dto.BetRecord;
import org.paddy.power.report.IReport;
import org.paddy.power.report.ReportByLiabilityAndCurrency;
import org.paddy.power.report.ReportForTotalLiabilityByCurrency;

/***
 * Main application class that generate required report from Bet Data.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
        final FileRead fd = new FileRead(RESOURCE_FILE_PATH);
        final List<BetRecord> list = fd.read();
        final IReport rp1 = new ReportByLiabilityAndCurrency(new ConsoleWrite(new TableDrawer(pw)), list);
        final IReport rp2 = new ReportForTotalLiabilityByCurrency(new ConsoleWrite(new TableDrawer(pw)), list);

        rp1.generateReport();
        System.out.println();
        System.out.println();
        rp2.generateReport();
    }
}
