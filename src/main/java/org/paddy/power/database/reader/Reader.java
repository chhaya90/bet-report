package org.paddy.power.database.reader;

import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.dto.BetData;

/**
 * Interface defining the methods which we can get the {@link org.paddy.power.dto.ReportDao} object(s) from Input Source.
 */
public interface Reader {
    /**
     * Read bet data from input source .
     *
     * @return List<BetData> list of betData object
     * @throws BetDataException
     *             this exception will be raised with valid error code specified if any error occurs while reading from source
     */
    List<BetData> read() throws BetDataException;
}
