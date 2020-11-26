package org.paddy.power.database.reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.paddy.power.dto.BetRecord;

/**
 * Unit Tests for {@link FileRead} class.
 */
public class FileReadTest {
    @Rule
    public ExpectedException expectedExceptions = ExpectedException.none();

    private Reader reader;

    @Before
    public void setUp() {

    }

    @Test
    public void whenReadingBetData_thenRequiredNumberOfRecordIsRead() throws Exception {
        reader = new FileRead("/bet_data_test.csv");
        final List<BetRecord> li = reader.read();
        assertThat(li.size(), is(6));
    }

    @Test
    public void whenIncorrectCsvFileIsProvided_thenRequiredExceptionIsThrown() throws Exception {
        reader = new FileRead("/et_data.csv");
        expectedExceptions.expect(IllegalArgumentException.class);
        expectedExceptions.expectMessage("InputStream is null for path");
        final List<BetRecord> li = reader.read();

    }

}
