package org.paddy.power.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.Exception.BetDataExceptionCode;
import org.paddy.power.database.comparator.BetDataCurrencyLiabilityComparator;
import org.paddy.power.database.writer.IWriter;
import org.paddy.power.dto.BetReport;
import org.paddy.power.dto.CsvBetRecord;

/**
 * Class to generate report by liability and currency.
 */
public class ReportByLiabilityAndCurrency implements IReport {
    private final IWriter writer;
    private List<CsvBetRecord> csvBetRecords;
    private ReportGenerateUtil reportGenerateUtil = new ReportGenerateUtil();

    public ReportByLiabilityAndCurrency(IWriter writer, List<CsvBetRecord> csvBetRecords) {
        this.writer = writer;
        this.csvBetRecords = csvBetRecords;
    }

    /**
     * Method generate report for selection liability by currency .The report is ordered by currency and total liability descending order.
     * 
     * @throws BetDataException
     *             this exception will be raised with valid error code when list of bet data records is null.
     */

    @Override
    public void generateReport() throws BetDataException {
        final Map<String, Map<String, List<CsvBetRecord>>> map = groupBySelectionNameAndCurrency(csvBetRecords);
        final List<BetReport> list = generateReportListForSelectionLiabilityByCurrency(map);
        Collections.sort(list, new BetDataCurrencyLiabilityComparator());
        writer.setList(list);
        writer.write();
    }

    /**
     * Method to group bet data by selection name and currency.
     * 
     * @param csvBetRecordList
     *            list of csv bet records.
     * @return Map<String, Map<String, List<BetData>>> map of csv BetData by selection name and then currency.
     * @throws BetDataException
     *             this exception will be raised with valid error code when list of bet data records is null.
     */

    Map<String, Map<String, List<CsvBetRecord>>> groupBySelectionNameAndCurrency(final List<CsvBetRecord> csvBetRecordList) throws BetDataException {
        if (csvBetRecordList == null) {
            throw new BetDataException(BetDataExceptionCode.PARAMETER_NULL_INSIDE_CODE, new NullPointerException());
        }
        return csvBetRecordList.stream().collect(Collectors.groupingBy(CsvBetRecord::getSelectionName,
                Collectors.groupingBy(CsvBetRecord::getCurrency)));
    }

    /*
     * Method generate list of Bet Report records for selection liability by currency.
     */

    List<BetReport> generateReportListForSelectionLiabilityByCurrency(final Map<String, Map<String, List<CsvBetRecord>>> map) {
        final List<BetReport> reportList = new ArrayList<>();
        for (String name : map.keySet()) {
            reportGenerateUtil.populateReport(map.get(name), reportList, name);
        }
        return reportList;
    }
}
