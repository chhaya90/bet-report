package org.paddy.power.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class CsvUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtils.class);

    public static <T> List<T> readCsvToBeanList(final String file, final Class clazz, List list) throws IOException {
        final HeaderColumnNameMappingStrategy mappingStrategy = new HeaderColumnNameMappingStrategy();
        mappingStrategy.setType(clazz);

        try (Reader reader = new BufferedReader(new FileReader(file))) {
            CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz).withMappingStrategy(mappingStrategy).withIgnoreLeadingWhiteSpace(true)
                    .build();

            list = cb.parse();
        } catch (final IOException e) {
            LOGGER.warn("Error loading resource through filepath: {}", file, e);
            throw e;
        }
        return list;
    }
}
