package org.paddy.power.database.reader;

import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.dto.CsvBetRecord;
import org.paddy.power.dto.BetReport;

/**
 * Interface defining the methods which we can get the {@link BetReport} object(s) from Input Source.
 */
public interface Reader {
    /**
     * Read bet data from input source .
     *
     * @return List<BetData> list of betData object
     * @throws BetDataException
     *             this exception will be raised with valid error code if any error occurs while reading from source
     */
    List<CsvBetRecord> read() throws BetDataException;
}
