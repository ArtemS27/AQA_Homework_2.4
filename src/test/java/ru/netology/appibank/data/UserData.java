package ru.netology.appibank.data;

import lombok.Value;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

public class UserData {
    private UserData() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoWithInvalidLogin() {
        return new AuthInfo("petya", "qwerty123");
    }

    public static AuthInfo getAuthInfoWithInvalidPassword() {
        return new AuthInfo("vasya", "asdfg456");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static VerificationCode getInvalidVerificationCode() {
        return new VerificationCode("54321");
    }

    @Value
    public static class CardNumber {
        private String number;
    }

    public static CardNumber getFirstCardNumber() {
        return new CardNumber("5559 0000 0000 0001");
    }

    public static CardNumber getSecondCardNumber() {
        return new CardNumber("5559 0000 0000 0002");
    }

    @Value
    public static class TransferAmount {
        private String transferAmount;
    }

    public static TransferAmount transferAmount(String amount){
        return new TransferAmount(amount);
    }
}
