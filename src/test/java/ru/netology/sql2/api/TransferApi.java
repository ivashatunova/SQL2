package ru.netology.sql2.api;

import ru.netology.sql2.data.DataHelper;

import static io.restassured.RestAssured.given;
import static ru.netology.sql2.api.ApiHelper.requestSpec;

public class TransferApi {
    public static void transfer(String token, DataHelper.TransferInfo transferInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .header("Authorization", "Bearer " + token)
                .body(transferInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/transfer") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

}

