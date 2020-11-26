package org.paddy.power.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to load data from specified resources.
 */
public class ResourceLoaderUtils {
    /**
     * Method returns an input stream from given source path.
     * 
     * @param path
     *            resource path name
     * @return An input stream for reading the resource, or <tt>null</tt> if the resource could not be found
     */
    public InputStreamReader getClasspathResourceAsStream(String path) {
        InputStream inputStream = this.getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalArgumentException(String.format("InputStream is null for path '%s'", path));
        }
        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);

    }
}
