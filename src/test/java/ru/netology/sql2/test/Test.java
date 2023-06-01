package ru.netology.sql2.test;

import org.junit.jupiter.api.AfterAll;
import ru.netology.sql2.data.DataHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Test {
    @Test
    void EchoTest() {

        // Given - When - Then
        // Предусловия
        given()
                .baseUri("https://postman-echo.com")
                .body("hello world") // отправляемые данные (заголовки и query можно выставлять аналогично)
// Выполняемые действия
                .when()
                .post("/post")
// Проверки
                .then()
                .statusCode(200)
                .body("data", equalTo("hello world"))
        ;
    }

    @AfterAll
    static void cleanUP() {
        DataHelper.cleanUP();
    }

//    @org.junit.jupiter.api.Test
//    void shouldLogin() {
//        open("http://localhost:9999");
//        var loginPage = new LoginPageV1();
//        var authInfo = DataHelper.getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getLastVerificationCode();
//        var dashboardPage = verificationPage.validVerify(verificationCode);
//    }

}
