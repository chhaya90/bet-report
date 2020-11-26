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

    private IReader IReader;

    @Before
    public void setUp() {

    }

    @Test
    public void whenReadingBetData_thenRequiredNumberOfRecordIsRead() throws Exception {
        IReader = new FileRead("/bet_data_test.csv");
        final List<BetRecord> li = IReader.read();
        assertThat(li.size(), is(6));
    }

    @Test
    public void whenIncorrectCsvFileIsProvided_thenRequiredExceptionIsThrown() throws Exception {
        IReader = new FileRead("/et_data.csv");
        expectedExceptions.expect(IllegalArgumentException.class);
        expectedExceptions.expectMessage("InputStream is null for path");
        final List<BetRecord> li = IReader.read();

    }

}
