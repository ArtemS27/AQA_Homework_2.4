package ru.netology.appibank.data;

import com.codeborne.selenide.commands.ToString;
import lombok.Value;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.util.Random;

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
        private String cardId;
    }

    public static CardNumber getFirstCardNumber() {
        return new CardNumber("5559 0000 0000 0001", "'92df3f1c-a033-48e6-8390-206f6b1f56c0'");
    }

    public static CardNumber getSecondCardNumber() {
        return new CardNumber("5559 0000 0000 0002", "'0f3f5c2a-249e-4c3d-8287-09f7a039391d'");
    }

    public static String generateValidAmount(int balance){
        String amount = String.valueOf(new Random().nextInt(Math.abs(balance)+1));
        return amount;
    }

    public static String generateInvalidAmount(int balance){
        String amount = String.valueOf(Math.abs(balance) + new Random().nextInt(10000));
        return amount;
    }

}
