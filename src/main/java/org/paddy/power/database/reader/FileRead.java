package org.paddy.power.database.reader;

import java.util.ArrayList;
import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.dto.CsvBetRecord;
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
    public List<CsvBetRecord> read() throws BetDataException {
        List<CsvBetRecord> csvBetRecordList = new ArrayList<>();
        csvBetRecordList = CsvUtils.readCsvToBeanList(new ResourceLoaderUtils().getClasspathResourceAsStream(filePath), CsvBetRecord.class, csvBetRecordList);
        return csvBetRecordList;
    }
}
