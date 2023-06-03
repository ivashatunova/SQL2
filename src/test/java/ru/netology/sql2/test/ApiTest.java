package ru.netology.sql2.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.sql2.api.AuthApi;
import ru.netology.sql2.api.CardsApi;
import ru.netology.sql2.data.DataHelper;

import static io.restassured.RestAssured.given;


public class ApiTest {
    @Test
    void testAuthorization() {
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        AuthApi.login(authInfo);
        DataHelper.VerificationInfo verificationInfo = new DataHelper.VerificationInfo(authInfo.getLogin(), DataHelper.getLastVerificationCode());
        String token = AuthApi.verification(verificationInfo);
    }
    @Test
    void testGetCards() {
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        AuthApi.login(authInfo);
        DataHelper.VerificationInfo verificationInfo = new DataHelper.VerificationInfo(authInfo.getLogin(), DataHelper.getLastVerificationCode());
        String token = AuthApi.verification(verificationInfo);
        var cards = CardsApi.getCards(token);
        Assertions.assertNotEquals(0, cards.length);
        Assertions.assertNotNull(cards[0].getId());
        Assertions.assertNotNull(cards[0].getNumber());
        Assertions.assertNotEquals(0,cards[0].getBalance());
    }


//    void EchoTest() {
//
//        // Given - When - Then
//        // Предусловия
//        given()
//                .baseUri("https://postman-echo.com")
//                .body("hello world") // отправляемые данные (заголовки и query можно выставлять аналогично)
//// Выполняемые действия
//                .when()
//                .post("/post")
//// Проверки
//                .then()
//                .statusCode(200)
//                .body("data", equalTo("hello world"))
//        ;
//    }

//    @AfterAll
//    static void cleanUP() {
//        DataHelper.cleanUP();
//    }

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
