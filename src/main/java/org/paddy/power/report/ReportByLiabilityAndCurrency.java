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
import org.paddy.power.dto.BetRecord;

/**
 * Class to generate report by liability and currency.
 */
public class ReportByLiabilityAndCurrency implements IReport {
    private final IWriter writer;
    private List<BetRecord> betRecords;
    private ReportGenerateUtil reportGenerateUtil = new ReportGenerateUtil();

    public ReportByLiabilityAndCurrency(IWriter writer, List<BetRecord> betRecords) {
        this.writer = writer;
        this.betRecords = betRecords;
    }

    /**
     * Method generate report for selection liability by currency .The report is ordered by currency and total liability descending order.
     * 
     * @throws BetDataException
     *             this exception will be raised with valid error code when list of bet data records is null.
     */

    @Override
    public void generateReport() throws BetDataException {
        final Map<String, Map<String, List<BetRecord>>> map = groupBySelectionNameAndCurrency(betRecords);
        final List<BetReport> list = generateReportListForSelectionLiabilityByCurrency(map);
        Collections.sort(list, new BetDataCurrencyLiabilityComparator());
        writer.setList(list);
        writer.write();
    }

    /**
     * Method to group bet data by selection name and currency.
     * 
     * @param betRecordList
     *            list of bet records.
     * @return Map<String, Map<String, List<BetData>>> map of BetData by selection name and then currency.
     * @throws BetDataException
     *             this exception will be raised with valid error code when list of bet data records is null.
     */

    Map<String, Map<String, List<BetRecord>>> groupBySelectionNameAndCurrency(final List<BetRecord> betRecordList) throws BetDataException {
        if (betRecordList == null) {
            throw new BetDataException(BetDataExceptionCode.PARAMETER_NULL_INSIDE_CODE, new NullPointerException());
        }
        return betRecordList.stream().collect(Collectors.groupingBy(BetRecord::getSelectionName,
                Collectors.groupingBy(BetRecord::getCurrency)));
    }

    /*
     * Method generate list of Bet Report records for selection liability by currency.
     */

    List<BetReport> generateReportListForSelectionLiabilityByCurrency(final Map<String, Map<String, List<BetRecord>>> map) {
        final List<BetReport> reportList = new ArrayList<>();
        for (String name : map.keySet()) {
            reportGenerateUtil.populateReport(map.get(name), reportList, name);
        }
        return reportList;
    }
}
