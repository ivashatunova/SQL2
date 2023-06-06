package ru.netology.sql2.test;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.sql2.api.AuthApi;
import ru.netology.sql2.api.CardsApi;
import ru.netology.sql2.api.TransferApi;
import ru.netology.sql2.data.DataHelper;

import static io.restassured.RestAssured.given;
import static ru.netology.sql2.api.CardsApi.getBalance;


public class ApiTest {
    private static String token;

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        token =  getToken(DataHelper.getAuthInfo());
    }


    @AfterAll
    static void cleanUP() {
        DataHelper.cleanUP();
    }

    private static String getToken(DataHelper.AuthInfo authInfo) {
        AuthApi.login(authInfo);
        DataHelper.VerificationInfo verificationInfo = new DataHelper.VerificationInfo(authInfo.getLogin(), DataHelper.getLastVerificationCode());
        String token = AuthApi.verification(verificationInfo);
        return token;

    }

    @Test
    void testAuthorization() {
        getToken(DataHelper.getAuthInfo());
    }

    @Test
    void testNotAuthorization() {
        DataHelper.AuthInfo authInfo = DataHelper.getWrongAuthInfo();
        AuthApi.notLogin(authInfo);
    }

    @Test
    void testNotVerification() {
        DataHelper.AuthInfo authInfo = DataHelper.getOtherAuthInfo();
        AuthApi.login(authInfo);
        DataHelper.VerificationInfo verificationInfo = new DataHelper.VerificationInfo(authInfo.getLogin(), DataHelper.getWrongVerificationCode());
        AuthApi.notVerification(verificationInfo);
    }

    @Test
    void testGetCards() {
        var cards = CardsApi.getCards(token);
        Assertions.assertNotEquals(0, cards.length);
        Assertions.assertNotNull(cards[0].getId());
        Assertions.assertNotNull(cards[0].getNumber());
        Assertions.assertNotEquals(0, cards[0].getBalance());
    }

    @Test
    void testTransfer() {
        var cardsBefore = CardsApi.getCards(token);
        int balance1Before = getBalance(DataHelper.card1Info, cardsBefore);
        int balance2Before = getBalance(DataHelper.card2Info, cardsBefore);

        int amount = 5000;

        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(DataHelper.card1Info, DataHelper.card2Info, amount);
        TransferApi.transfer(token, transferInfo);

        var cardsAfter = CardsApi.getCards(token);
        int balance1After = getBalance(DataHelper.card1Info, cardsAfter);
        int balance2After = getBalance(DataHelper.card2Info, cardsAfter);

        Assertions.assertEquals(balance1Before - amount, balance1After);
        Assertions.assertEquals(balance2Before + amount, balance2After);
    }
}
