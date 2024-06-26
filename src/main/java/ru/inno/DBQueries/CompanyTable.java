package ru.inno.DBQueries;

import ru.inno.model.Company;
import ru.inno.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CompanyTable {
    private static final String SQL_INSERT_INTO_COMPANY = "insert into company (is_active, name, description) values(?, ?, ?);";
    private static final String SQL_SELECT_FROM_COMPANY = "select * from company where name = ?;";
    private static final String SQL_SELECT_FROM_COMPANY_BY_ID = "select * from company where id = ?;";
    private static final String SQL_DELETE_FROM_COMPANY = "delete from company where name = ?;";

    public static Company createCompany(Company company) {
        DBUtils.insertOrDelete(SQL_INSERT_INTO_COMPANY, String.valueOf(company.getIsActive()), company.getName(), company.getDescription());
        try (ResultSet resultSet = DBUtils.selectFromDb(SQL_SELECT_FROM_COMPANY, company.getName())) {
            assert resultSet != null;
            resultSet.next();
            company.setId(resultSet.getInt("id"));
        }catch (SQLException e){
            System.err.println("ОШИБКА: Не удалось получить id компании!");
            System.out.println(e);
        }
        return company;
    }

    public static Timestamp getCompanyDeletedDate(Company company){
        try (ResultSet resultSet = DBUtils.selectFromDb(SQL_SELECT_FROM_COMPANY_BY_ID, String.valueOf(company.getId()))) {
            assert resultSet != null;
            resultSet.next();
            return resultSet.getTimestamp("deleted_at");
        }catch (SQLException e){
            System.err.println("ОШИБКА: Не удалось получить id компании!");
            System.out.println(e);
        }
        return null;
    }

    public static void deleteCompany(Company company){
        DBUtils.insertOrDelete(SQL_DELETE_FROM_COMPANY, company.getName());
    }

}
