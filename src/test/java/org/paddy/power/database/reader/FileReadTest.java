package org.paddy.power.database.reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.paddy.power.dto.BetData;

@RunWith(JUnit4.class)
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
        final List<BetData> li = reader.read();
        assertThat(li.size(), is(24));
    }

    @Test
    public void whenIncorrectCsvFileIsProvided_thenRequiredExceptionIsThrown() throws Exception {
        reader = new FileRead("/et_data.csv");
        expectedExceptions.expect(IllegalArgumentException.class);
        expectedExceptions.expectMessage("InputStream is null for path");
        final List<BetData> li = reader.read();

    }

}
