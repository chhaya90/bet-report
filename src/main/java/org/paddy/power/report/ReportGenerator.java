package org.paddy.power.report;

import static org.paddy.power.utils.Constants.RESOURCE_FILE_PATH;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.Exception.BetDataExceptionCode;
import org.paddy.power.database.comparator.BetDataCurrencyLiabilityComparator;
import org.paddy.power.database.reader.Reader;
import org.paddy.power.database.writer.Writer;
import org.paddy.power.dto.CsvBetRecord;
import org.paddy.power.dto.BetReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Class to generate bet data report.
 */
public class ReportGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerator.class);
    private final Map<String, Locale> currencyLocaleMap;
    private final DecimalFormat decimalFormat;
    private List<CsvBetRecord> csvBetRecords;
    private final Reader reader;
    private final Writer writer;

    public ReportGenerator(final Reader reader, final Writer writer) {
        this.reader = reader;
        this.writer = writer;
        currencyLocaleMap = new HashMap<>();
        currencyLocaleMap.put("EUR", Locale.UK);
        currencyLocaleMap.put("GBP", Locale.UK);
        decimalFormat = new DecimalFormat("##.00");
    }

    public void readBetDataFromCsv() throws BetDataException {
        try {
            csvBetRecords = reader.read();
        } catch (BetDataException e) {
            LOGGER.error("Error reading csv from filepath: {}", RESOURCE_FILE_PATH, e);
            throw e;
        }
    }

    /**
     * Method generate report for selection liability by currency .The report is ordered by currency and total liability descending order.
     * @throws BetDataException
     *          this exception will be raised with valid error code when list of bet data records is null.
     */

    public void generateReportByLiabilityAndCurrency() throws BetDataException {
        final Map<String, Map<String, List<CsvBetRecord>>> map = groupBySelectionNameAndCurrency(csvBetRecords);
        final List<BetReport> list = generateReportListForSelectionLiabilityByCurrency(map);
        Collections.sort(list, new BetDataCurrencyLiabilityComparator());
        writer.setList(list);
        writer.write();
    }

    /**
     * Method generate report showing total liability by currency.
     */
    public void generateReportForTotalLiabilityByCurrency() {
        final List<BetReport> list = generateReportListForTotalLiabilityByCurrency();
        writer.setList(list);
        writer.writeTotalLiabilityReport();
    }
    /*
     Method generate list of Bet Report records for selection liability by currency.
     */
    List<BetReport> generateReportListForSelectionLiabilityByCurrency(final Map<String, Map<String, List<CsvBetRecord>>> map) {
        final List<BetReport> reportList = new ArrayList<>();
        for (String name : map.keySet()) {
            populateReport(map.get(name), reportList, name);
        }
        return reportList;
    }

    /**
     * Method sort csv bet Data records by currency and populate Bet Report list.
     * @return reportList list of Bet Report.
     */

    List<BetReport> generateReportListForTotalLiabilityByCurrency() {
        final Map<String, List<CsvBetRecord>> data = csvBetRecords.stream().collect(Collectors.groupingBy(CsvBetRecord::getCurrency));
        final List<BetReport> reportList = new ArrayList<>();
        populateReport(data, reportList, null);
        return reportList;
    }

    private void populateReport(final Map<String, List<CsvBetRecord>> data, final List<BetReport> reportList, final String name) {
        for (String currency : data.keySet()) {
            final List<CsvBetRecord> betRecords = data.get(currency);
            final BetReport rd = createBetReport(betRecords, currency, name);
            reportList.add(rd);
        }
    }

    /**
     * Method that creates single bet report from list of csv bet data.
     * @param csvBetRecordList list of csv bet records.
     * @param currency currency
     * @param name  selection name
     * @return  populated Bet report.
     */

    private BetReport createBetReport(final List<CsvBetRecord> csvBetRecordList, final String currency, final String name) {
        final double totalLiability = csvBetRecordList.stream().mapToDouble(x -> (x.getStake() * x.getPrice())).sum();
        final double totalStake = csvBetRecordList.stream().mapToDouble(x -> x.getStake()).sum();
        final String symbol = Currency.getInstance(currency.trim()).getSymbol(currencyLocaleMap.get(currency.trim()));

        final BetReport rd = new BetReport();
        rd.setSelectionName(name);
        rd.setCurrency(currency);
        rd.setNumberOfBets(csvBetRecordList.size());
        rd.setTotalStakes(symbol + decimalFormat.format(totalStake));
        rd.setTotalLiability(symbol + decimalFormat.format(totalLiability));
        return rd;
    }

    /**
     * Method to group bet data by selection name and currency.
     * @param csvBetRecordList list of csv bet records.
     * @return Map<String, Map<String, List<BetData>>>  map of csv BetData by selection name and then currency.
     * @throws BetDataException
     *           this exception will be raised with valid error code when list of bet data records is null.
     */

    Map<String, Map<String, List<CsvBetRecord>>> groupBySelectionNameAndCurrency(final List<CsvBetRecord> csvBetRecordList) throws BetDataException {
        if (csvBetRecordList == null) {
            throw new BetDataException(BetDataExceptionCode.PARAMETER_NULL_INSIDE_CODE, new NullPointerException());
        }
        return csvBetRecordList.stream().collect(Collectors.groupingBy(CsvBetRecord::getSelectionName,
                Collectors.groupingBy(CsvBetRecord::getCurrency)));
    }

    public List<CsvBetRecord> getCsvBetRecords() {
        return csvBetRecords;
    }
}