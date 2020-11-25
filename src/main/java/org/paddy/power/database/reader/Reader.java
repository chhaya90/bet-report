package org.paddy.power.database.reader;

import java.io.IOException;
import java.util.List;

import org.paddy.power.dto.BetData;

public interface Reader {
    List<BetData> read(String file) throws IOException;
}
