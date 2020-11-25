package org.paddy.power.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.paddy.power.Exception.BetDataException;
import org.paddy.power.Exception.BetDataExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * Utility class to data read from csv file.
 */
public class CsvUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtils.class);

    /***
     * method that to map data read from input stream to list of dao.
     * 
     * @param ip
     *            input stream reader.
     * @param bean
     *            dao class
     * @param list
     *            list of dao class to be populated
     * @param <T>
     * @return list of dao class
     * @throws BetDataException
     *             this exception will be raised for failure or interrupted input/output operation
     */
    public static <T> List<T> readCsvToBeanList(final InputStreamReader ip, final Class bean, List list) throws BetDataException {
        final HeaderColumnNameMappingStrategy mappingStrategy = new HeaderColumnNameMappingStrategy();
        mappingStrategy.setType(bean);

        try (Reader reader = new BufferedReader(ip)) {
            CsvToBean cb = new CsvToBeanBuilder(reader).withType(bean).withMappingStrategy(mappingStrategy).withIgnoreLeadingWhiteSpace(true)
                    .build();

            list = cb.parse();
        } catch (final IOException ioException) {
            LOGGER.error("Error loading resource through csv filepath  to bean", ioException);
            throw new BetDataException(BetDataExceptionCode.PARAMETER_NULL_INSIDE_CODE, ioException);
        }
        return list;
    }
}
