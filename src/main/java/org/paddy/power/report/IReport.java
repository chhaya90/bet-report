package org.paddy.power.report;

import org.paddy.power.Exception.BetDataException;

/**
 * Interface to generate Report.
 */
public interface IReport {
    /**
     * Method generate report for BetData.
     * 
     * @throws BetDataException
     *             this exception will be raised with valid error code when there is error generating report.
     */
    void generateReport() throws BetDataException;
}
