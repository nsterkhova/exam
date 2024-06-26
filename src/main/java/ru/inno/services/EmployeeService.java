package ru.inno.services;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.inno.model.Company;
import ru.inno.utils.PropertiesManager;
import ru.inno.utils.UrnNames;

import static io.restassured.RestAssured.given;

public class EmployeeService {

    private static final String baseUri = PropertiesManager.getUrl(UrnNames.EMPLOYEE);

    @Step("Отправляем запрос на получение списка сотрудников компании")
    public static Response getEmployeeListResponse(Company company) {
        return given()
                .log().all()
                .baseUri(baseUri)
                .queryParam("company", company.getId())
                .get()
                .thenReturn();
    }

    @Step("Отправляем запрос на создание сотрудника компании")
    public static Response createEmployeeResponse(String token, String requestBody) {
        return given().header("x-client-token", token)
                .log().all()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .post(baseUri)
                .thenReturn();
    }

}
