package org.paddy.power.report;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.paddy.power.dto.BetReport;
import org.paddy.power.dto.BetRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to generate bet data report.
 */
public class ReportGenerateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerateUtil.class);
    private final Map<String, Locale> currencyLocaleMap;
    private final DecimalFormat decimalFormat;
    private List<BetRecord> betRecords;

    public ReportGenerateUtil() {
        currencyLocaleMap = new HashMap<>();
        currencyLocaleMap.put("EUR", Locale.UK);
        currencyLocaleMap.put("GBP", Locale.UK);
        decimalFormat = new DecimalFormat("##.00");
    }

    public void populateReport(final Map<String, List<BetRecord>> data, final List<BetReport> reportList, final String name) {
        for (String currency : data.keySet()) {
            final List<BetRecord> betRecords = data.get(currency);
            final BetReport rd = createBetReport(betRecords, currency, name);
            reportList.add(rd);
        }
    }

    /**
     * Method that creates single bet report from list of csv bet data.
     * 
     * @param betRecordList
     *            list of csv bet records.
     * @param currency
     *            currency
     * @param name
     *            selection name
     * @return populated Bet report.
     */

    private BetReport createBetReport(final List<BetRecord> betRecordList, final String currency, final String name) {
        final double totalLiability = betRecordList.stream().mapToDouble(x -> (x.getStake() * x.getPrice())).sum();
        final double totalStake = betRecordList.stream().mapToDouble(x -> x.getStake()).sum();
        final String symbol = Currency.getInstance(currency.trim()).getSymbol(currencyLocaleMap.get(currency.trim()));

        final BetReport rd = new BetReport();
        rd.setSelectionName(name);
        rd.setCurrency(currency);
        rd.setNumberOfBets(betRecordList.size());
        rd.setTotalStakes(symbol + decimalFormat.format(totalStake));
        rd.setTotalLiability(symbol + decimalFormat.format(totalLiability));
        return rd;
    }
}