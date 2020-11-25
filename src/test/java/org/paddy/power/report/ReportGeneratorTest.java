package org.paddy.power.report;

import java.sql.Timestamp;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.opencsv.bean.CsvBindByName;

public class ReportGeneratorTest {
    @Rule
    public ExpectedException expectedExceptions = ExpectedException.none();

    @Before
    public void setUp() {

    }

    @Test
    public void whenReportGeneratedByLiabilityAndCurrencyIsCalledWithValidData_thenExpectedResponseIsRecived() {

    }

   /* static class RetDataBuilder {
        private Integer selectionId;
        private String selectionName;
        private double stake;
        private double price;
        private String currency;

        protected RetDataBuilder stateSelectionId(final Timestamp timeStamp) {
            this.stateModifiedTime = timeStamp;
            return this;
        }

    }
*/
}
