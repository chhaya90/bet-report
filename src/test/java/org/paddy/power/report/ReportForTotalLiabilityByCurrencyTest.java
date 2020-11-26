package org.paddy.power.report;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
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
import org.paddy.power.database.reader.IReader;
import org.paddy.power.database.writer.ConsoleWrite;
import org.paddy.power.dto.BetRecord;
import org.paddy.power.dto.BetReport;

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
    private List<BetRecord> betRecordList;

    @Before
    public void setUp() throws BetDataException, IOException {
        IReader reader = new FileRead("/bet_data_test.csv");
        betRecordList = reader.read();
        objectUnderTest = new ReportForTotalLiabilityByCurrency(consoleMock, betRecordList);
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
        BetReport expectedObj = new BetReport();
        expectedObj.setCurrency("EUR");
        expectedObj.setNumberOfBets(3);
        expectedObj.setTotalStakes("€12.55");
        expectedObj.setTotalLiability("€82.40");
        assertThat(report, hasItem(expectedObj));

    }
}
