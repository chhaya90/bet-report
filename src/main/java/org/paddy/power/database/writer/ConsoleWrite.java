package org.paddy.power.database.writer;

import java.util.List;

import org.paddy.power.dto.BetReport;

/**
 * Class to write bet report to console.
 */
public class ConsoleWrite implements IWriter {
    private List<BetReport> betData;
    private final TableDrawer tableDrawer;

    public ConsoleWrite(final TableDrawer tableDrawer) {
        this.tableDrawer = tableDrawer;
    }

    /**
     * Method to write Bet report.
     */
    @Override
    public void write() {
        tableDrawer.drawTableRow(18, "Selection Name", "Currency", "Number_Of_Bets", "Total Stakes", "Total Liability");
        tableDrawer.drawLine(120);
        betData.forEach(x ->
                tableDrawer.drawTableRow(18,
                        x.getSelectionName(),
                        x.getCurrency(),
                        x.getNumberOfBets().toString(),
                        x.getTotalStakes(),
                        x.getTotalLiability())
        );
    }

    /**
     * Method to write betLiability Report.
     */

    @Override
    public void writeTotalLiabilityReport() {
        tableDrawer.drawTableRow(15, "Currency", "Number_Of_Bets", "Total Stakes", "Total Liability");
        tableDrawer.drawLine(80);
        betData.forEach(x ->
                tableDrawer.drawTableRow(15,
                        x.getCurrency(),
                        x.getNumberOfBets().toString(),
                        x.getTotalStakes(),
                        x.getTotalLiability())
        );
    }

    @Override
    public void setList(final List<BetReport> list) {
        this.betData = list;
    }
}
