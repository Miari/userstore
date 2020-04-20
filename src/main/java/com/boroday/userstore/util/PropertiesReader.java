package com.boroday.userstore.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesReader {

    private Properties properties;

    public PropertiesReader(String path) {
        properties = readProperties(path);
    }

    public Properties getProperties() {
        return new Properties(properties); // TODO объясни, пожалуйста, почему return properties было плохо и зачем каждый раз создавать новый объект
    }

    public Integer getPropertyInt(String propertyName) {
        String property = properties.getProperty(propertyName);
        return property == null ? null : Integer.valueOf(property);
    }

    private Properties readProperties(String path) {
        log.info("Trying to get properties");
        Properties properties = new Properties();
        try (InputStream resourceInputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path);) {
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