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
import org.paddy.power.dto.BetData;
import org.paddy.power.dto.ReportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerator.class);
    private final Map<String, Locale> currencyLocaleMap;
    private final DecimalFormat decimalFormat;
    private List<BetData> betDataRecords;
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
            betDataRecords = reader.read();
        } catch (BetDataException e) {
            LOGGER.error("Error reading csv from filepath: {}", RESOURCE_FILE_PATH, e);
            throw e;
        }
    }

    public void reportGeneratedByLiabilityAndCurrency() throws BetDataException {
        final Map<String, Map<String, List<BetData>>> map = groupBySelectionNameAndCurrency(betDataRecords);
        final List<ReportDao> list = generateReportListForSelectionLiabilityByCurrency(map);
        Collections.sort(list, new BetDataCurrencyLiabilityComparator());
        writer.setList(list);
        writer.write();
    }

    public void reportGeneratedForTotalLiabilityByCurrency() {
        final List<ReportDao> list = generateReportListForTotalLiabilityByCurrency();
        writer.setList(list);
        writer.writeTotalLiabilityReport();
    }

    List<ReportDao> generateReportListForSelectionLiabilityByCurrency(final Map<String, Map<String, List<BetData>>> map) {
        final List<ReportDao> reportList = new ArrayList<>();
        for (String name : map.keySet()) {
            populateReport(map.get(name), reportList, name);
        }
        return reportList;
    }

    List<ReportDao> generateReportListForTotalLiabilityByCurrency() {
        final Map<String, List<BetData>> data = betDataRecords.stream().collect(Collectors.groupingBy(BetData::getCurrency));
        final List<ReportDao> reportList = new ArrayList<>();
        populateReport(data, reportList, null);
        return reportList;
    }

    private void populateReport(final Map<String, List<BetData>> data, final List<ReportDao> reportList, final String name) {
        for (String currency : data.keySet()) {
            final List<BetData> betData = data.get(currency);
            final ReportDao rd = createReportDao(betData, currency, name);
            reportList.add(rd);
        }
    }

    private ReportDao createReportDao(final List<BetData> betData, final String currency, final String name) {
        final double totalLiability = betData.stream().mapToDouble(x -> (x.getStake() * x.getPrice())).sum();
        final double totalStake = betData.stream().mapToDouble(x -> x.getStake()).sum();
        final String symbol = Currency.getInstance(currency.trim()).getSymbol(currencyLocaleMap.get(currency.trim()));

        final ReportDao rd = new ReportDao();
        rd.setSelectionName(name);
        rd.setCurrency(currency);
        rd.setNumberOfBets(betData.size());
        rd.setTotalStakes(symbol + decimalFormat.format(totalStake));
        rd.setTotalLiability(symbol + decimalFormat.format(totalLiability));
        return rd;
    }

    Map<String, Map<String, List<BetData>>> groupBySelectionNameAndCurrency(final List<BetData> betData) throws BetDataException {
        if (betData == null) {
            throw new BetDataException(BetDataExceptionCode.PARAMETER_NULL_INSIDE_CODE, new NullPointerException());
        }
        return betData.stream().collect(Collectors.groupingBy(BetData::getSelectionName,
                Collectors.groupingBy(BetData::getCurrency)));
    }

    public List<BetData> getBetDataRecords() {
        return betDataRecords;
    }
}