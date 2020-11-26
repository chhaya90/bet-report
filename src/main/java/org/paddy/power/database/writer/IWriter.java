package org.paddy.power.database.writer;

import java.util.List;

import org.paddy.power.dto.BetReport;

/**
 * Interface to write BetData to output.
 */
public interface IWriter {
    /**
     * Method to write bet report.
     */
    void write();

    /**
     * Method to write total liability report.
     */
    void writeTotalLiabilityReport();

    /**
     * Method to set list of Bet Report.
     * @param list bet report list.
     */
    void setList(List<BetReport> list);
}
