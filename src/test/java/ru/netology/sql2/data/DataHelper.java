package ru.netology.sql2.data;

import lombok.Data;
import lombok.Value;

import java.sql.DriverManager;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("Wrong", "123qwerty");
    }

    @Value
    public static class VerificationInfo {
        private String login;
        private String code;
    }


    public static String getLastVerificationCode() {
        var countSQL = "select code from auth_codes order by created desc limit 1;";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                var countStmt = conn.createStatement();
        ) {
            try (var rs = countStmt.executeQuery(countSQL)) {
                if (rs.next()) {
                    var count = rs.getString(1);
                    return count;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "0";
    }

    public static String getWrongVerificationCode() {
        Random random = new Random();
        while (true) {
            String randomNumber = String.valueOf(random.nextInt(900000) + 100000);

            var sql = "select code from auth_codes where code='" + randomNumber+ "';";
            try (
                    var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                    var stmt = connection.createStatement();
            ) {
                try (var rs = stmt.executeQuery(sql)) {
                    if (!rs.next()) {
                        // Результат пустой
                        return randomNumber;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }


    public static void cleanUP() {
        var countSQL1 = "delete from auth_codes;";
        var countSQL2 = "delete from cards;";
        var countSQL3 = "delete from users;";
        try {
            var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
            var statement = connection.createStatement();
            statement.executeUpdate(countSQL1);
            statement.executeUpdate(countSQL2);
            statement.executeUpdate(countSQL3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Data
    public static class Card {
        private String id;
        private String number;
        private int balance;
    }

    @Value
    public static class TransferInfo {
        private String from;
        private String to;
        private int amount;
    }

    public static String card1Info = "5559 0000 0000 0001";
    public static String card2Info = "5559 0000 0000 0002";


}
