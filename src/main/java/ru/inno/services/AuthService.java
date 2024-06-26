package ru.inno.services;

import com.alibaba.fastjson.JSON;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import ru.inno.model.AuthRequest;
import ru.inno.model.User;
import ru.inno.utils.PropertiesManager;
import ru.inno.utils.UrnNames;

import static io.restassured.RestAssured.given;

public class AuthService {
    public static final String baseUrl = PropertiesManager.getUrl(UrnNames.AUTH);

    public static String getToken(User user){
        String requestBody = JSON.toJSONString(new AuthRequest(user));
        return given()
                .log().all()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when().post(baseUrl)
                .then().log().all()
                .extract().path("userToken");
    }
}
