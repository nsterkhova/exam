package ru.inno.utils;

import ru.inno.model.UrnNames;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    static Properties properties = new Properties();
    private static void readPropertiesFile() {
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream("src/main/resources/config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
    public static String getPropertyValue (String propertyName) {
        if (properties.isEmpty()) {
            readPropertiesFile();
        }
        return properties.getProperty(propertyName);
    }

    public static String getUrl(UrnNames urnName) {
        String urn = switch (urnName) {
            case COMPANY -> getPropertyValue("api.company");
            case EMPLOYEE -> getPropertyValue("api.employee");
            case AUTH -> getPropertyValue("api.login");
            case DELETE_COMPANY -> getPropertyValue("api.company.delete");
        };
        return getPropertyValue("app.url") + urn;
    }
}
