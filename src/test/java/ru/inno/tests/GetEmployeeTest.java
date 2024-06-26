package ru.inno.tests;

import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.DBQueries.CompanyTable;
import ru.inno.DBQueries.EmployeeTable;
import ru.inno.model.Company;
import ru.inno.model.Employee;
import ru.inno.testData.CommonTestData;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.inno.services.EmployeeService.getEmployeeListResponse;

@Epic("Работа с сотрудниками")
@Feature("Получение списка сотрудников")
@Story("Проверка статуса сотрудника в списке сотружников")
@Owner("Natalia Sterkhova")
@DisplayName("Проверка списка сотрудников")
public class GetEmployeeTest {

    private final Company companyForTest = CommonTestData.NEW_COMPANY;
    private final Employee activeEmployee = CommonTestData.NEW_EMPLOYEE;
    private final Employee notActiveEmployee = CommonTestData.NOT_ACTIVE_EMPLOYEE;

    @BeforeEach
    public void SetUp(){
        CompanyTable.createCompany(companyForTest);
        EmployeeTable.createEmployee(activeEmployee, companyForTest);
        EmployeeTable.createEmployee(notActiveEmployee, companyForTest);
    }

    @AfterEach()
    public void TearDown(){
        EmployeeTable.deleteAllEmployees(companyForTest);
        CompanyTable.deleteCompany(companyForTest);
    }

    @Test
    @DisplayName("Проверка статусов сотрудников в ответе на запрос списка сотрудников")
    @Description("В ответе должны возвращаться только активные сотрудники. " +
            "\nВ тесте запрашивается список сотрудников компании, у которой есть 1 активный и 1 неактивный сотрудник")
    @Severity(SeverityLevel.NORMAL)
    public void getOnlyActiveUsersTestN(){
        List<Employee> employee = getEmployeeListResponse(companyForTest)
                .then().log().all()
                .body("", Matchers.hasSize(2))
                .extract().jsonPath()
                .getList("$", Employee.class);
        step("Проверяем, что в ответе присутствует активный пользователь и отсутсвует неактивный", () -> {
            assertThat("В списке сотрудников получен активный пользователь", employee.contains(activeEmployee), is(true));
            assertThat("В списке сотрудников отсутствует неактивный пользователь", employee.contains(notActiveEmployee), is(false));
        });
    }
}
