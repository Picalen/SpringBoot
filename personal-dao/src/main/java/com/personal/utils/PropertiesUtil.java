package com.personal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties prop = new Properties();
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private PropertiesUtil() {
    }

    public static String getString(String key) {
        try {
            prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties"));
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static int getInt(String key) {
        String value = getString(key);
        if (!(value == null || "".equals(value))) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static boolean getBoolean(String key) {
        return "true".equals(getString(key));
    }

    public static Properties newInstance(String propertiesName) {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            logger.error("读取配置文件出错", e);
        }
        return p;
    }
}