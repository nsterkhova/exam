package ru.inno.testData;

import ru.inno.model.User;

public class CommonUsers {
    /**
     * Пользователь с правами админа для внесения изменений в базу
     */
    public static final User ADMIN = new User("testadmin", "P@ssw0rd", "ADMIN FOR TESTS", "admin");
}
