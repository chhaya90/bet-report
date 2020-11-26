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
import org.paddy.power.dto.BetReport;
import org.paddy.power.dto.CsvBetRecord;

/**
 * Unit test for {@link ReportByLiabilityAndCurrencyTest} class.
 */
public class ReportForTotalLiabilityByCurrencyTest {
    @Rule
    public ExpectedException expectedExceptions = ExpectedException.none();
    @Mock
    public ConsoleWrite consoleMock;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private ReportForTotalLiabilityByCurrency objectUnderTest;
    private List<CsvBetRecord> csvBetRecordList;

    @Before
    public void setUp() throws BetDataException {
        Reader read = new FileRead("/bet_data_test.csv");
        csvBetRecordList = read.read();
        objectUnderTest = new ReportForTotalLiabilityByCurrency(consoleMock, csvBetRecordList);
    }

    @Test
    public void whenGenerateReportForTotalLiabilityByCurrencyIsCalledWithValidData_thenSuccessFullExecution() throws BetDataException {
        objectUnderTest.generateReport();
        verify(consoleMock, times(1)).writeTotalLiabilityReport();
        verify(consoleMock, times(1)).setList(any());

    }

    @Test
    public void whenGenerateReportListForTotalLiabilityByCurrencyIsCalled_thenExpectedResult() {
        List<BetReport> report = objectUnderTest.generateReportListForTotalLiabilityByCurrency();
        assertThat(report.size(), is(2));
        assertThat(report.get(0).getNumberOfBets(), is(3));
        assertThat(report.get(0).getTotalStakes(), is("£10.00"));
        assertThat(report.get(0).getTotalLiability(), is("£50.25"));
        assertThat(report.get(1).getNumberOfBets(), is(3));
        assertThat(report.get(1).getTotalStakes(), is("€12.55"));
        assertThat(report.get(1).getTotalLiability(), is("€82.40"));

    }
}
