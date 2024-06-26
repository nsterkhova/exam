package ru.inno.tests;

import com.alibaba.fastjson.JSON;
import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.DBQueries.EmployeeTable;
import ru.inno.helpers.TokenHelper;
import ru.inno.model.Company;
import ru.inno.model.Employee;
import ru.inno.model.User;
import ru.inno.testData.CommonTestData;
import ru.inno.testData.CommonUsers;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.inno.services.EmployeeService.createEmployeeResponse;

@Epic("Работа с сотрудниками")
@Feature("Создание сотрудника")
@Story("Неуспешное создание сотрудника")
@Owner("Natalia Sterkhova")
@DisplayName("Проверка создания сотрудника")
public class CreateEmployeeTest {

    private final Company companyForTest = CommonTestData.NOT_EXIST_COMPANY;
    private final Employee employeeToCreate = CommonTestData.NEW_EMPLOYEE;
    private final User userForTest = CommonUsers.ADMIN;

    @Test
    @DisplayName("Проверка создания сотрудника несуществующей компании")
    @Description("Сотрудник для несуществующей компании не создается")
    @Severity(SeverityLevel.CRITICAL)
    public void createEmployeeTestN(){
        String token = TokenHelper.generateToken(userForTest);
        employeeToCreate.setCompanyId(companyForTest.getId());
        String requestBody = JSON.toJSONString(employeeToCreate);
        step("Проверяем возврат ошибки в ответе на запрос создания сотрудника несуществующей компании", () -> {
            createEmployeeResponse(token, requestBody)
                            .then().log().all()
                            .statusCode(500)
                            .body("statusCode", Matchers.is(500))
                            .body("message", Matchers.is("Internal server error"));
        });
        step("Проверяем, что пользователь в БД не создался", () -> {
            assertThat("В БД отсутствует пользователь из запроса", EmployeeTable.checkEmployeeExist(companyForTest), is(false));
        });
    }
}
