package ru.inno.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.inno.utils.PropertiesManager;
import ru.inno.utils.UrnNames;

import static io.restassured.RestAssured.given;

public class CompanyService {
    private static final String baseUri = PropertiesManager.getUrl(UrnNames.COMPANY);

    @Step("Отправляем запрос на получение списка компаний с признаком active = {activeParamValue}")
    public static Response getCompanyListResponse (String activeParamValue) {
        return given()
                .log().all()
                .baseUri(baseUri)
                .queryParam("active", activeParamValue)
                .get()
                .thenReturn();
    }
}
