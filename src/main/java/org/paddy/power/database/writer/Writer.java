package org.paddy.power.database.writer;

import java.util.List;

import org.paddy.power.dto.BetReport;

/**
 * Interface to write BetData to output.
 */
public interface Writer {
    /***
     *
     */
    void write();

    void writeTotalLiabilityReport();

    void setList(List<BetReport> list);
}
