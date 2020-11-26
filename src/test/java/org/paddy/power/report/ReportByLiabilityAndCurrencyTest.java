package org.paddy.power.report;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
import org.paddy.power.database.reader.Reader;
import org.paddy.power.database.writer.ConsoleWrite;
import org.paddy.power.database.writer.TableDrawer;
import org.paddy.power.dto.BetReport;
import org.paddy.power.dto.CsvBetRecord;

/**
 * Unit test for {@link ReportByLiabilityAndCurrency} class.
 */
public class ReportByLiabilityAndCurrencyTest {
    @Rule
    public ExpectedException expectedExceptions = ExpectedException.none();
    @Mock
    public ConsoleWrite consoleMock;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private ReportByLiabilityAndCurrency objectUnderTest;
    private List<CsvBetRecord> betRecordList;

    @Before
    public void setUp() throws BetDataException {
        Reader read = new FileRead("/bet_data_test.csv");
        betRecordList = read.read();
        objectUnderTest = new ReportByLiabilityAndCurrency(consoleMock, betRecordList);
    }

    @Test
    public void whenGenerateReportByLiabilityAndCurrencyIsCalledWithValidData_thenSuccessFullExecution() throws BetDataException {
        objectUnderTest.generateReport();
        verify(consoleMock, times(1)).write();
        verify(consoleMock, times(1)).setList(any());
    }

    @Test
    public void whenGenerateReportListForSelectionLiabilityByCurrency_thenExpectedResult() throws BetDataException {
        final Map<String, Map<String, List<CsvBetRecord>>> map = objectUnderTest.groupBySelectionNameAndCurrency(betRecordList);
        List<BetReport> report = objectUnderTest.generateReportListForSelectionLiabilityByCurrency(map);
        assertThat(report.size(), is(5));
    }

    @Test
    public void whenGroupBySelectionNameAndCurrencyIsCalledWithNullList_thenExceptionIsThrown() throws BetDataException {
        expectedExceptions.expect(BetDataException.class);
        expectedExceptions.expectCause(is(NullPointerException.class));
        objectUnderTest.groupBySelectionNameAndCurrency(null);
    }

    @Test
    public void whenGroupBySelectionNameAndCurrencyIsCalledWithValidList_thenExpectedResult() throws BetDataException {
        Map<String, Map<String, List<CsvBetRecord>>> map = objectUnderTest.groupBySelectionNameAndCurrency(betRecordList);
        assertThat(map.size(), is(4));
        assertThat(map.get(" My Fair Lady").size(), is(2));
        assertThat(map.get(" My Fair Lady").get(" GBP").get(0).getPrice(), is(6.0));
        assertThat(map.get(" My Fair Lady").get(" EUR").get(0).getStake(), is(3.4));
        assertThat(map.get(" Bilbo's Adventure").size(), is(1));
    }

}
