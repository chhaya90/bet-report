package org.paddy.power.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.paddy.power.database.writer.IWriter;
import org.paddy.power.dto.BetReport;
import org.paddy.power.dto.CsvBetRecord;

/**
 * Class to generate report for Total liability by currency.
 */
public class ReportForTotalLiabilityByCurrency implements IReport {
    private final IWriter writer;
    private List<CsvBetRecord> csvBetRecords;
    private ReportGenerateUtil reportGenerateUtil = new ReportGenerateUtil();

    public ReportForTotalLiabilityByCurrency(IWriter writer, List<CsvBetRecord> csvBetRecords) {
        this.writer = writer;
        this.csvBetRecords = csvBetRecords;
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
     * Method sort csv bet Data records by currency and populate Bet Report list.
     * 
     * @return reportList list of Bet Report.
     */

    List<BetReport> generateReportListForTotalLiabilityByCurrency() {
        final Map<String, List<CsvBetRecord>> data = csvBetRecords.stream().collect(Collectors.groupingBy(CsvBetRecord::getCurrency));
        final List<BetReport> reportList = new ArrayList<>();
        reportGenerateUtil.populateReport(data, reportList, null);
        return reportList;
    }
}
