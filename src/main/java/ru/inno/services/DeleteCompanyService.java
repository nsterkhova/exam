package ru.inno.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.inno.model.Company;
import ru.inno.utils.PropertiesManager;
import ru.inno.model.UrnNames;

import static io.restassured.RestAssured.given;

public class DeleteCompanyService {
    private static final String baseUri = PropertiesManager.getUrl(UrnNames.DELETE_COMPANY);

    @Step("Отправляем запрос на удаление компании")
    public static Response deleteCompanyResponse(String token, Company company) {
        return given().header("x-client-token", token)
                .log().all()
                .baseUri(baseUri + company.getId())
                .get()
                .thenReturn();
    }
}
