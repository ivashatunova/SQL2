package ru.netology.sql2.api;

import ru.netology.sql2.data.DataHelper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.netology.sql2.api.ApiHelper.requestSpec;

public class CardsApi {
    public static DataHelper.Card[] getCards(String token) {
        DataHelper.Card[] cards = given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .header("Authorization", "Bearer " + token)
                .when() // "когда"
                .get("/api/cards") // на какой путь относительно BaseUri отправляем запрос
                .as(DataHelper.Card[].class);
        return cards;
    }

    public static int getBalance(String cardNumber, DataHelper.Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getNumber().substring(14).equals(cardNumber.substring(14))) {
                return cards[i].getBalance();
            }
        }
        throw new RuntimeException("Card " + cardNumber + " not found");
    }


}
