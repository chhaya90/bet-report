package org.paddy.power.database.writer;

import java.util.List;

import org.paddy.power.dto.ReportDao;

public class ConsoleWrite implements Writer {
    private List<ReportDao> betData;

    @Override
    public void write() {

        System.out.println(String.format("%30s %5s %10s %5s %10s %5s %20s %5s %20s", "Selection Name", "|", "Currency", "|",
                "Number_Of_Bets", "|", "Total Stakes", "|", "Total Liability"));
        System.out.println(String.format("%s", "-------------------------------------------" +
                "------------------------------------------------------------------------------"));

        betData.forEach(x -> System.out.println(String.format("%30s %5s %10s %5s %14d %5s %20s %5s %20s", x.getSelectionName(), "|",
                x.getCurrency(), "|", x.getNumberOfBets(), "|", x.getTotalStakes(), "|", x.getTotalLiability())));
    }

    @Override
    public void writeTotalLiabilityReport() {

        System.out.println(String.format("%10s %5s %10s %5s %20s %5s %20s", "Currency", "|",
                "Number_Of_Bets", "|", "Total Stakes", "|", "Total Liability"));
        System.out.println(String.format("%s", "-------------------------------------------" +
                "------------------------------------------------------------------------------"));

        betData.forEach(x -> System.out.println(String.format("%10s %5s %14d %5s %20s %5s %20s",
                x.getCurrency(), "|", x.getNumberOfBets(), "|", x.getTotalStakes(), "|", x.getTotalLiability())));
    }

    @Override
    public void setList(final List<ReportDao> list) {
        this.betData = list;
    }
}
