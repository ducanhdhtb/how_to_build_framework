package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Chúng ta cần chỉ đường dẫn tới file config
            String path = "src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}