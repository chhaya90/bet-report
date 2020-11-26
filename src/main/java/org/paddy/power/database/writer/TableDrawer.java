package org.paddy.power.database.writer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class to draw table report.
 */
public class TableDrawer {
    private final PrintWriter myWriter;

    public TableDrawer(final PrintWriter myWriter) {
        this.myWriter = myWriter;
    }

    public void drawLine(final int size) {
        for (int i = 0; i < size; i++) {
            myWriter.print("-");
        }
        myWriter.println();
    }

    public void drawTableRow(final int columnWidth, final String... columnValues) {
        myWriter.println(getTableRow(columnWidth, columnValues));
    }

    String getTableRow(final int columnWidth, final String... columnValues) {
        final StringBuilder format = new StringBuilder();
        final List<String> separatedList = new ArrayList<>();
        for (int i = 0; i < columnValues.length; i++) {
            format.append("%").append(columnWidth).append("s");
            if (i < columnValues.length - 1) {
                format.append(" %5s ");
            }

            separatedList.add(columnValues[i]);
            separatedList.add("|");
        }

        return String.format(format.toString(), separatedList.toArray());
    }
}
