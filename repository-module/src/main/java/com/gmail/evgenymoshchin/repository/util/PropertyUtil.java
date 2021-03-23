package com.gmail.evgenymoshchin.repository.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static final String CONFIG_PROPERTY_FILE_LOCATION = "config.properties";

    private static PropertyUtil instance;
    private final Properties properties;

    private PropertyUtil() {
        this.properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILE_LOCATION)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Property file is not valid", e);
        }
    }

    public static PropertyUtil getInstance() {
        if (instance == null) {
            instance = new PropertyUtil();
        }
        return instance;
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
