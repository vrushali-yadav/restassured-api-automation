package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public static String propertyReader(String filePath, String key) {
        String value = null;

        // InputStream is required while loading into properties
        try(InputStream input = new FileInputStream(filePath)) {

            // object creation for property class
            Properties prop = new Properties();

            // load properties file
            prop.load(input);

            // getProperty() method will fetch the value according to the given key
            value = prop.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }
}
