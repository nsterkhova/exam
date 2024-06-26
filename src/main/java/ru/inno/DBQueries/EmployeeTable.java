package ru.inno.DBQueries;

import ru.inno.model.Company;
import ru.inno.model.Employee;
import ru.inno.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EmployeeTable {
    private static final String SQL_INSERT_INTO_EMPLOYEE = "insert into employee(is_active, first_name, last_name, middle_name, phone, email, birthdate, avatar_url, company_id) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_FROM_EMPLOYEE = "select * from employee where first_name = ? and company_id = ?;";
    private static final String SQL_DELETE_FROM_EMPLOYEE = "delete from employee where company_id = ?;";

    private static final String SQL_SELECT_FROM_EMPLOYEE_BY_COMPANY_ID = "select * from employee where company_id = ?;";

    public static boolean checkEmployeeExist (Company company){
        boolean result = false;
        try (ResultSet resultSet = DBUtils.selectFromDb(SQL_SELECT_FROM_EMPLOYEE_BY_COMPANY_ID, String.valueOf(company.getId()))){
            assert resultSet != null;
            if (resultSet.next()) result = true;
        } catch (SQLException e) {
            System.err.println("ОШИБКА: Не удалось получить данные сотрудника!");
            System.out.println(e);
        }
        return result;
    }

    public static void createEmployee(Employee employee, Company company){
        employee.setCompanyId(company.getId());
        DBUtils.insertOrDelete(SQL_INSERT_INTO_EMPLOYEE, String.valueOf(employee.getIsActive()), employee.getFirstName(), employee.getLastName(), employee.getMiddleName(),
                employee.getPhone(), employee.getEmail(), employee.getBirthdate(), employee.getAvatar_url(), String.valueOf(employee.getCompanyId()));
        try (ResultSet resultSet = DBUtils.selectFromDb(SQL_SELECT_FROM_EMPLOYEE, employee.getFirstName(), String.valueOf(employee.getCompanyId()))) {
            assert resultSet != null;
            resultSet.next();
            employee.setId(resultSet.getInt("id"));
        }catch (SQLException e){
            System.err.println("ОШИБКА: Не удалось получить id компании!");
            System.out.println(e);
        }
    }

    public static void deleteAllEmployees(Company company){
        DBUtils.insertOrDelete(SQL_DELETE_FROM_EMPLOYEE, String.valueOf(company.getId()));
    }
}
