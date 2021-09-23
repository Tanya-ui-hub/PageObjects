package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static String getFirstCard() {
        return "5559 0000 0000 0001";
    }

    public static String getSecondCard() {
        return "5559 0000 0000 0002";
    }

    public static String getInvalidCard() {
        return "1111 1111 1111 1111";
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @Value
    public static class Card {
        private String card;
    }
}