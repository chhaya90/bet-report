package org.paddy.power.report;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.paddy.power.Exception.BetDataException;
import org.paddy.power.database.reader.FileRead;
import org.paddy.power.database.writer.ConsoleWrite;
import org.paddy.power.dto.BetData;
import org.paddy.power.dto.ReportDao;

public class ReportGeneratorTest {
    @Rule
    public ExpectedException expectedExceptions = ExpectedException.none();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public ConsoleWrite consoleMock;
    private ReportGenerator objectUnderTest;

    @Before
    public void setUp() throws BetDataException {
        objectUnderTest = new ReportGenerator(new FileRead("/bet_data_test.csv"), consoleMock);
        objectUnderTest.readBetDataFromCsv();
    }

    @Test
    public void whenReportGeneratedByLiabilityAndCurrencyIsCalledWithValidData_thenSuccessFullExecution() throws BetDataException {
        objectUnderTest.reportGeneratedByLiabilityAndCurrency();
        verify(consoleMock, times(1)).write();
        verify(consoleMock, times(1)).setList(any());
    }

    @Test
    public void whenReportGeneratedForTotalLiabilityByCurrencyIsCalledWithValidData_thenSuccessFullExecution() throws BetDataException {
        objectUnderTest.reportGeneratedForTotalLiabilityByCurrency();
        verify(consoleMock, times(1)).writeTotalLiabilityReport();
        verify(consoleMock, times(1)).setList(any());

    }

    @Test
    public void whenGenerateReportListForSelectionLiabilityByCurrency_thenExpectedResult() throws BetDataException {
        final List<BetData> dataList =  objectUnderTest.getBetDataRecords();
        final Map<String, Map<String, List<BetData>>> map = objectUnderTest.groupBySelectionNameAndCurrency(dataList);
        List<ReportDao> report = objectUnderTest.generateReportListForSelectionLiabilityByCurrency(map);
        assertThat(report.size(),is(5));
    }

    @Test
    public void whenGenerateReportListForTotalLiabilityByCurrencyIsCalled_thenExpectedResult() {
        final List<BetData> dataList =  objectUnderTest.getBetDataRecords();
        List<ReportDao> report = objectUnderTest.generateReportListForTotalLiabilityByCurrency();
        assertThat(report.size(),is(2));
        assertThat(report.get(0).getNumberOfBets(),is(3));
        assertThat(report.get(0).getTotalStakes(),is("£10.00"));
        assertThat(report.get(0).getTotalLiability(),is("£50.25"));
        assertThat(report.get(1).getNumberOfBets(),is(3));
        assertThat(report.get(1).getTotalStakes(),is("€12.55"));
        assertThat(report.get(1).getTotalLiability(),is("€82.40"));

    }

    @Test
    public void whenGroupBySelectionNameAndCurrencyIsCalledWithNullList_thenExceptionIsThrown() throws BetDataException {
        expectedExceptions.expect(BetDataException.class);
        expectedExceptions.expectCause(is(NullPointerException.class));
        objectUnderTest.groupBySelectionNameAndCurrency(null);
    }

    @Test
    public void whenGroupBySelectionNameAndCurrencyIsCalledWithValidList_thenExpectedResult() throws BetDataException {
        final List<BetData> dataList =  objectUnderTest.getBetDataRecords();
        Map<String, Map<String, List<BetData>>> map = objectUnderTest.groupBySelectionNameAndCurrency(dataList);
        assertThat(map.size(),is(4));
        assertThat(map.get(" My Fair Lady").size(),is(2));
    }



}
