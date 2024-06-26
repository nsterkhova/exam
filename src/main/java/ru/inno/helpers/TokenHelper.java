package ru.inno.helpers;

import ru.inno.DBQueries.AppUsersTable;
import ru.inno.model.User;
import ru.inno.services.AuthService;

public class TokenHelper {

    public static String generateToken(User user) {
        if (!AppUsersTable.isUserExist(user)) {
            AppUsersTable.createUser(user);
        }
        return AuthService.getToken(user);
    }
}
