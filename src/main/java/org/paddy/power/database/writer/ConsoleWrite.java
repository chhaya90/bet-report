package org.paddy.power.database.writer;

import java.util.List;

import org.paddy.power.dto.ReportDao;

public class ConsoleWrite implements Writer {
    private List<ReportDao> betData;
    private final TableDrawer tableDrawer;

    public ConsoleWrite(final TableDrawer tableDrawer) {
        this.tableDrawer = tableDrawer;
    }

    @Override
    public void write() {
        tableDrawer.drawTableRaw(18, "Selection Name", "Currency", "Number_Of_Bets", "Total Stakes", "Total Liability");
        tableDrawer.drawLine(120);
        betData.forEach(x ->
                tableDrawer.drawTableRaw(18,
                        x.getSelectionName(),
                        x.getCurrency(),
                        x.getNumberOfBets().toString(),
                        x.getTotalStakes(),
                        x.getTotalLiability())
        );
    }

    @Override
    public void writeTotalLiabilityReport() {
        tableDrawer.drawTableRaw(15, "Currency", "Number_Of_Bets", "Total Stakes", "Total Liability");
        tableDrawer.drawLine(80);
        betData.forEach(x ->
                tableDrawer.drawTableRaw(15,
                        x.getCurrency(),
                        x.getNumberOfBets().toString(),
                        x.getTotalStakes(),
                        x.getTotalLiability())
        );
    }

    @Override
    public void setList(final List<ReportDao> list) {
        this.betData = list;
    }
}
