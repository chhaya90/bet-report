package org.paddy.power.database.reader;

import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.dto.BetRecord;

/**
 * Interface defining the methods from which we can get the {@link BetRecord} object(s) from Input Source.
 */
public interface Reader {
    /**
     * Read bet data from input source .
     *
     * @return List<BetData> list of bet Record object
     * @throws BetDataException
     *             this exception will be raised with valid error code if any error occurs while reading from source
     */
    List<BetRecord> read() throws BetDataException;
}
