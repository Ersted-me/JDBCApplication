package com.ersted.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        loadProperties();
    }

    public static String getProperty(String property) {
        return PROPERTIES.getProperty(property);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
