package org.paddy.power.database.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.paddy.power.dto.BetData;
import org.paddy.power.utils.CsvUtils;

public class FileRead implements Reader {

    public List<BetData> read(final String file) throws IOException {
        List<BetData> betDataList = new ArrayList<>();
        betDataList = CsvUtils.readCsvToBeanList(ClassLoader.getSystemResource(file).getFile(), BetData.class, betDataList);
        return betDataList;
    }
}
