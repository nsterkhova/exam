package ru.inno.utils;

import java.sql.*;

public class DBUtils {
    private static final String connectionString = PropertiesManager.getPropertyValue("db.host");
    private static final String user = PropertiesManager.getPropertyValue("db.login");
    private static final String pass = PropertiesManager.getPropertyValue("db.password");

    public static ResultSet selectFromDb(String executeQuery, String... params) {
        try (Connection connection = DriverManager.getConnection(connectionString, user, pass)) {
            PreparedStatement statement = connection.prepareStatement(executeQuery);
            if(params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i], Types.OTHER);
                }
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("ОШИБКА: Не удалось получить данные из БД!");
            System.out.println(e);
        }
        return null;
    }

    public static void insertOrDelete(String executeQuery, String... params) {
        try (Connection connection = DriverManager.getConnection(connectionString, user, pass)) {
            PreparedStatement statement = connection.prepareStatement(executeQuery);
            if(params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i], Types.OTHER);
                }
            }
            statement.executeUpdate();
        }catch (SQLException e) {
            System.err.println("ОШИБКА: Не удалось записать данные в БД!");
            System.out.println(e);
        }
    }
}
