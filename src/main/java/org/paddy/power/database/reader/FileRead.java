package org.paddy.power.database.reader;

import static org.paddy.power.utils.Constants.COMMA_DELIMITER;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.Exception.BetDataExceptionCode;
import org.paddy.power.dto.BetRecord;
import org.paddy.power.utils.ResourceLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * Class to read Bet Data from file.
 */
public class FileRead implements IReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRead.class);
    private String filePath;

    public FileRead(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * Read bet data from input source .
     *
     * @return List<BetData> list of bet Record object
     * @throws BetDataException
     *             this exception will be raised with valid error code if any error occurs while reading from source
     */
    public List<BetRecord> read() throws BetDataException {
        try {
            final InputStreamReader fileReader = new InputStreamReader(new ResourceLoaderUtils().getClasspathResourceAsStream(filePath));
            final CSVParser parser = new CSVParserBuilder().withSeparator(COMMA_DELIMITER).build();
            final CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).withSkipLines(1).build();
            final List<String[]> allData = csvReader.readAll();
            return getBetRecords(allData);
        } catch (final IOException ioException) {
            LOGGER.error("Error loading records through filepath  to bean", ioException);
            throw new BetDataException(BetDataExceptionCode.ERROR_READING_FROM_FILE, ioException);
        }
    }

    private List<BetRecord> getBetRecords(final List<String[]> allData) {
        final List<BetRecord> recordList = new ArrayList<>(allData.size());
        for (String[] row : allData) {
            BetRecord record = new BetRecord.BetRecordBuilder().setBetId(row[0].trim()).setBetTimestamp(row[1].trim())
                    .setSelectionId(Integer.parseInt(row[2].trim())).setSelectionName(row[3].trim()).setStake(Double.parseDouble(row[4].trim()))
                    .setPrice(Double.parseDouble(row[5].trim())).setCurrency(row[6].trim()).build();
            recordList.add(record);
        }
        return recordList;
    }

}
