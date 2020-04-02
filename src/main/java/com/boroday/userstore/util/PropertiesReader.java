package com.boroday.userstore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private String path;
    private Properties properties;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public PropertiesReader(String path) {
        this.path = path;
        properties = readProperties();
    }

    public Properties getProperties() {
        return properties;
    }

    public Integer getPropertyInt(String propertyName) {
        String property = properties.getProperty(propertyName);
        return property == null ? null : Integer.valueOf(property);
    }

    private Properties readProperties() {
        log.info("Trying to get properties");
        Properties properties = new Properties();
        try {
            InputStream resourceInputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path);//todo clarify getClassLoader()
            if (resourceInputStream == null) {
                log.error("Property file is not found by the path {}", path);
                throw new IllegalArgumentException("No properties by the path " + path);
            }
            properties.load(resourceInputStream);
            return properties;
        } catch (IOException e) {
            log.error("Not possible to read properties file");
            throw new RuntimeException("Not possible to read properties file", e);
        }
    }
}
