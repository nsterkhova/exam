package ru.inno.DBQueries;

import ru.inno.model.User;
import ru.inno.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUsersTable {
    private static final String SQL_SELECT_FROM_APP_USERS = "select * from app_users where login = ?";
    private static final String SQL_INSERT_INTO_APP_USERS = "insert into app_users(login, password, display_name, role) values (?, ?, ?, ?)";

    public static boolean isUserExist(User user) {
        try (ResultSet resultSet = DBUtils.selectFromDb(SQL_SELECT_FROM_APP_USERS, user.getUserLogin())){
            assert resultSet != null;
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e){
            System.err.println("ОШИБКА: Не удалось получить данные из БД!");
            System.out.println(e);
        }
        return false;
    }

    public static void createUser(User user){
        DBUtils.insertOrDelete(SQL_INSERT_INTO_APP_USERS, user.getUserLogin(), user.getUserPassword(), user.getDisplayName(), user.getRole());
    }
}
