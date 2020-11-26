package org.paddy.power.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.paddy.power.database.writer.IWriter;
import org.paddy.power.dto.BetReport;
import org.paddy.power.dto.BetRecord;

/**
 * Class to generate report for Total liability by currency.
 */
public class ReportForTotalLiabilityByCurrency implements IReport {
    private final IWriter writer;
    private List<BetRecord> betRecords;
    private ReportGenerateUtil reportGenerateUtil = new ReportGenerateUtil();

    public ReportForTotalLiabilityByCurrency(IWriter writer, List<BetRecord> betRecords) {
        this.writer = writer;
        this.betRecords = betRecords;
    }

    /**
     * Method generate report showing total liability by currency.
     */
    @Override
    public void generateReport() {
        final List<BetReport> list = generateReportListForTotalLiabilityByCurrency();
        writer.setList(list);
        writer.writeTotalLiabilityReport();
    }

    /**
     * Method sort bet Data records by currency and populate Bet Report list.
     * 
     * @return reportList list of Bet Report.
     */

    List<BetReport> generateReportListForTotalLiabilityByCurrency() {
        final Map<String, List<BetRecord>> data = betRecords.stream().collect(Collectors.groupingBy(BetRecord::getCurrency));
        final List<BetReport> reportList = new ArrayList<>();
        reportGenerateUtil.populateReport(data, reportList, null);
        return reportList;
    }
}
