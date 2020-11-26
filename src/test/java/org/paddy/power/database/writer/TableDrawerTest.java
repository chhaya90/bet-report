package org.paddy.power.database.writer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

/**
 *  Unit test for {@link TableDrawer} class.
 */
public class TableDrawerTest {

    private TableDrawer objectUnderTest;

    @Before
    public void setUp() {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
        objectUnderTest = new TableDrawer(pw);
    }

    @Test
    public void whenGetTableRow_thenExpectedResult() {
        final String tableDraw = objectUnderTest.getTableRow(5, "col1", "col2");
        assertThat(tableDraw, is(" col1     |  col2"));
    }

}
