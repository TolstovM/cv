package ru.vsu.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PropertiesLoaderFromFile implements PropertiesLoader {

    private String propertyPath;
    private Map<String, String> data = new HashMap<>();

    private static final String FILE_NOT_FOUND_MESSAGE = "File with path:\"%s\" does not exist";
    private static final String NO_SUCH_FIELD_MESSAGE = "Property \"%s\" does not found";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoaderFromFile.class);
    private static final String LOGGER_OPEN_INPUT_STREAM_MESSAGE = "Input stream opened";
    private static final String LOGGER_CLOSE_INPUT_STREAM_MESSAGE = "Input stream closed";
    private static final String LOGGER_WRONG_LINE_MESSAGE = "Cannot parse line: %s";

    private static final int INDEX_OF_KEY = 0;
    private static final int INDEX_OF_VALUE = 1;
    private static final int LIMIT = 2;

    public String getPropertyPath() {
        return propertyPath;
    }

    public Map<String, String> getData() {
        return new HashMap<>(data);
    }

    public PropertiesLoaderFromFile(String propertyPath) throws IOException {
        this.propertyPath = propertyPath;
        init();
    }

    public String getProperty(String key) throws NoSuchFieldException {
        String value = this.data.get(key);
        if (value != null) {
            return value;
        } else {
            throw new NoSuchFieldException(String.format(NO_SUCH_FIELD_MESSAGE, key));
        }
    }

    private void init() throws IOException {
        try(
                InputStream inputStream = this.getClass().getResourceAsStream(this.propertyPath);
                Scanner scanner = new Scanner(inputStream);
        ) {
            LOGGER.info(LOGGER_OPEN_INPUT_STREAM_MESSAGE);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tmp = line.split("=", LIMIT);
                if (tmp.length != 2) {
                    LOGGER.warn(String.format(LOGGER_WRONG_LINE_MESSAGE, line));
                    continue;
                }
                this.data.put(tmp[INDEX_OF_KEY].trim(), tmp[INDEX_OF_VALUE].trim());
            }
        }
        LOGGER.info(LOGGER_CLOSE_INPUT_STREAM_MESSAGE);
    }
}
