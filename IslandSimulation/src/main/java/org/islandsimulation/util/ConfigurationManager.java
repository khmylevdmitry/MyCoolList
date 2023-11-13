package org.islandsimulation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final String CONFIG_DIRECTORY = "config/";
    public static Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = ConfigurationManager.class.getClassLoader().getResourceAsStream(CONFIG_DIRECTORY + fileName)) {
            if (input == null) {
                throw new IOException("File not found: " + fileName);
            }
            properties.load(input);
        }
        return properties;
    }
}

