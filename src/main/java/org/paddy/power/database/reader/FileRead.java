package org.paddy.power.database.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.dto.BetData;
import org.paddy.power.utils.CsvUtils;
import org.paddy.power.utils.ResourceLoaderUtils;

/**
 * Class to read Bet Data from file.
 */
public class FileRead implements Reader {
    private String filePath;

    public FileRead(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<BetData> read() throws BetDataException {
        List<BetData> betDataList = new ArrayList<>();
        betDataList = CsvUtils.readCsvToBeanList(new ResourceLoaderUtils().getClasspathResourceAsStream(filePath), BetData.class, betDataList);
        return betDataList;
    }
}
