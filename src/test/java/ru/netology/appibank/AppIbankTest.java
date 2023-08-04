package ru.netology.appibank;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.appibank.data.UserData;
import ru.netology.appibank.operations.Balance;
import ru.netology.appibank.operations.Transfer;
import ru.netology.appibank.page.Login;
import ru.netology.appibank.page.Verification;

import static com.codeborne.selenide.Selenide.*;

public class AppIbankTest {
    @BeforeEach
    void setup(){
        open("http://localhost:9999");
    }

    @Test
    public void shouldLogIn(){
        var login = new Login();
        var authInfo = UserData.getAuthInfo();
        var verification = new Verification();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);

        $("[data-test-id=dashboard]").shouldBe(Condition.visible);
    }

    @Test
    public void shouldThrowNotificationIfLoginInvalid(){
        var login = new Login();
        var authInfo = UserData.getAuthInfoWithInvalidLogin();
        login.validLogin(authInfo);

        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldThrowNotificationIfPasswordInvalid(){
        var login = new Login();
        var authInfo = UserData.getAuthInfoWithInvalidPassword();
        login.validLogin(authInfo);

        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldThrowNotificationIfVerificationCodeInvalid() {
        var login = new Login();
        var authInfo = UserData.getAuthInfo();
        var verification = new Verification();
        var verificationCode = UserData.getInvalidVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);

        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    @Test
    public void shouldTransferMoneyFromFirstCardToSecond(){
        var login = new Login();
        var authInfo = UserData.getAuthInfo();
        var verification = new Verification();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
        var balanceActual = new Balance();
        int firstCardBalance = balanceActual.getFirstCardBalance();
        int secondCardBalance = balanceActual.getSecondCardBalance();
        var transfer = new Transfer();
        transfer.transferFromFirstCardToSecond();
        var balanceExpected = new Balance();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance-Integer.parseInt(UserData.transferAmount("500").getTransferAmount()));
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance+Integer.parseInt(UserData.transferAmount("500").getTransferAmount()));
    }

    @Test
    public void shouldTransferMoneyFromSecondCardToFirst(){
        var login = new Login();
        var authInfo = UserData.getAuthInfo();
        var verification = new Verification();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
        var balanceActual = new Balance();
        int firstCardBalance = balanceActual.getFirstCardBalance();
        int secondCardBalance = balanceActual.getSecondCardBalance();
        var transfer = new Transfer();
        transfer.transferFromSecondCardToFirst();
        var balanceExpected = new Balance();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance+Integer.parseInt(UserData.transferAmount("500").getTransferAmount()));
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance-Integer.parseInt(UserData.transferAmount("500").getTransferAmount()));
    }

    @Test
    public void shouldThrowToMainPageIfTooManyAttempts(){
        var login = new Login();
        var authInfo = UserData.getAuthInfo();
        var verification = new Verification();
        var verificationCode = UserData.getInvalidVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
        verification.cleanVerificationCodeField();
        verification.validVerificationCode(verificationCode);
        verification.cleanVerificationCodeField();
        verification.validVerificationCode(verificationCode);
        verification.cleanVerificationCodeField();
        verification.validVerificationCode(verificationCode);

        $("[data-test-id=error-notification]").shouldHave(Condition.text("Превышено количество попыток ввода кода!"));
        $(".heading").shouldBe(Condition.visible);
    }
}
