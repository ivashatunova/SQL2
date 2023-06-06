package ru.netology.sql2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.sql2.data.DataHelper;

import static io.restassured.RestAssured.given;
import static ru.netology.sql2.api.ApiHelper.requestSpec;

public class AuthApi {

    public static void login(DataHelper.AuthInfo authInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(authInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/auth") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static void notLogin(DataHelper.AuthInfo authInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(authInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/auth") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(400); // код 200 OK
    }

    public static String verification(DataHelper.VerificationInfo verificationInfo) {
        String token = given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(verificationInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/auth/verification") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200) // код 200 OK
                .extract()
                .path("token");
        return token;
    }

    public static void notVerification(DataHelper.VerificationInfo verificationInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(verificationInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/auth/verification") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(400); // код 200 OK

    }


}
