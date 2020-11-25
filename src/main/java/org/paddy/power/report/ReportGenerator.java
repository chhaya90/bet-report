package org.paddy.power.report;

import static org.paddy.power.utils.Constants.RESOURCE_FILE_PATH;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.Exception.BetDataExceptionCode;
import org.paddy.power.database.comparator.BetDataCurrencyLiabilityComparator;
import org.paddy.power.database.reader.Reader;
import org.paddy.power.database.writer.Writer;
import org.paddy.power.dto.BetData;
import org.paddy.power.dto.ReportDao;
import org.paddy.power.utils.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtils.class);
    private final Map<String, String> currencySymbolMap;
    private final DecimalFormat decimalFormat;
    private List<BetData> betDataRecords;
    private Reader reader;
    private Writer writer;

    ReportGenerator(final Reader reader, final Writer writer) {
        this.reader = reader;
        this.writer = writer;
        currencySymbolMap = new HashMap<>();
        currencySymbolMap.put(" GBP", "£");
        currencySymbolMap.put(" EUR", "€");
        decimalFormat = new DecimalFormat("##.00");
    }

    public void readBetDataFromCsv() throws IOException {
        try {
            betDataRecords = reader.read(RESOURCE_FILE_PATH);
        } catch (final IOException e) {
            LOGGER.error("Error reading csv from filepath: {}", RESOURCE_FILE_PATH, e);
            throw e;
        }
    }

    public void reportGeneratedByLiabilityAndCurrency() throws BetDataException {
        final Map<String, Map<String, List<BetData>>> map = groupBySelectionNameAndCurrency(betDataRecords);
        List<ReportDao> list = getReportListForSelectionLiabilityByCurrency(map);
        Collections.sort(list, new BetDataCurrencyLiabilityComparator());
        writer.setList(list);
        writer.write();
    }

    public void reportGeneratedForTotalLiabilityByCurrency() {
        List<ReportDao> list = generateReportListForTotalLiabilityByCurrency();
        writer.setList(list);
        writer.writeTotalLiabilityReport();
    }

    public List<ReportDao> getReportListForSelectionLiabilityByCurrency(final Map<String, Map<String, List<BetData>>> map) {
        List<ReportDao> list = new ArrayList<>();
        for (String s : map.keySet()) {
            Map<String, List<BetData>> valMap = map.get(s);
            for (String currency : valMap.keySet()) {
                List<BetData> li = valMap.get(currency);
                ReportDao rd = createReportDao(li, currency, s);
                list.add(rd);
            }

        }
        return list;
    }

    public List<ReportDao> generateReportListForTotalLiabilityByCurrency() {
        Map<String, List<BetData>> data = betDataRecords.stream().collect(
                Collectors.groupingBy(BetData::getCurrency));
        List<ReportDao> reportList = new ArrayList<>();
        for (String currency : data.keySet()) {

            List<BetData> betData = data.get(currency);
            ReportDao rd = createReportDao(betData, currency);
            reportList.add(rd);
        }
        return reportList;

    }

    private ReportDao createReportDao(final List<BetData> betData, final String currency, final String name) {
        ReportDao rd = new ReportDao();
        double totalLiability = betData.stream().mapToDouble(x -> (x.getStake() * x.getPrice())).sum();
        double totalStake = betData.stream().mapToDouble(x -> x.getStake()).sum();
        rd.setSelectionName(name);
        rd.setCurrency(currency);
        rd.setNumberOfBets(betData.size());
        rd.setTotalStakes(currencySymbolMap.get(currency) + decimalFormat.format(totalStake));
        rd.setTotalLiability(currencySymbolMap.get(currency) + decimalFormat.format(totalLiability));
        return rd;
    }

    private ReportDao createReportDao(final List<BetData> betData, final String currency) {
        return createReportDao(betData, currency, null);
    }

    public Map<String, Map<String, List<BetData>>> groupBySelectionNameAndCurrency(final List<BetData> betData) throws BetDataException {
        if (betData == null) {
            throw new BetDataException(BetDataExceptionCode.PARAMETER_NULL_INSIDE_CODE, new NullPointerException());
        }
        return betData.stream().collect(Collectors.groupingBy(BetData::getSelectionName,
                Collectors.groupingBy(BetData::getCurrency)));
    }

}
