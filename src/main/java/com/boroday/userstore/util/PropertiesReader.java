package com.boroday.userstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private String path;
    private Properties properties;

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
        Properties properties = new Properties();
        try {
            InputStream resourceInputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path);//todo clarify getClassLoader()
            if (resourceInputStream == null) {
                throw new IllegalArgumentException("No properties by the path " + path);
            }
            properties.load(resourceInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Not possible read properties file", e);
        }
    }
}
