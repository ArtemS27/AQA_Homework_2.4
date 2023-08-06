package ru.netology.appibank;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.appibank.data.UserData;
import ru.netology.appibank.page.*;

import static com.codeborne.selenide.Selenide.*;

public class AppIbankTest {
    @BeforeEach
    void setup(){
        open("http://localhost:9999");
    }

    @Test
    public void shouldLogIn(){
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfo();
        var verification = new VerificationPage();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
    }

    @Test
    public void shouldThrowNotificationIfLoginInvalid(){
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfoWithInvalidLogin();
        login.validLogin(authInfo);

        login.findErrorMessage("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    public void shouldThrowNotificationIfPasswordInvalid(){
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfoWithInvalidPassword();
        login.validLogin(authInfo);

        login.findErrorMessage("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    public void shouldThrowNotificationIfVerificationCodeInvalid() {
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfo();
        var verification = new VerificationPage();
        var verificationCode = UserData.getInvalidVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);

        login.findErrorMessage("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    public void shouldTransferMoneyFromFirstCardToSecond(){
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfo();
        var verification = new VerificationPage();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
        var balanceActual = new DashboardPage();
        int firstCardBalance = balanceActual.getFirstCardBalance();
        int secondCardBalance = balanceActual.getSecondCardBalance();
        var transfer = new TransferPage();
        transfer.setTransferAmount(UserData.generateValidAmount(balanceActual.getFirstCardBalance()));
        transfer.setCardNumber(UserData.getFirstCardNumber().getNumber());
        transfer.transferFromFirstCardToSecond();
        var balanceExpected = new DashboardPage();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance-transfer.getTransferAmount());
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance+transfer.getTransferAmount());
    }

    @Test
    public void shouldTransferMoneyFromSecondCardToFirst(){
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfo();
        var verification = new VerificationPage();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
        var balanceActual = new DashboardPage();
        int firstCardBalance = balanceActual.getFirstCardBalance();
        int secondCardBalance = balanceActual.getSecondCardBalance();
        var transfer = new TransferPage();
        transfer.setTransferAmount(UserData.generateValidAmount(balanceActual.getFirstCardBalance()));
        transfer.setCardNumber(UserData.getSecondCardNumber().getNumber());
        transfer.transferFromSecondCardToFirst();
        var balanceExpected = new DashboardPage();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance+transfer.getTransferAmount());
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance-transfer.getTransferAmount());
    }

    @Test
    public void shouldThrowMessageIfAmountMoteThanBalance(){
        var login = new LoginPage();
        var authInfo = UserData.getAuthInfo();
        var verification = new VerificationPage();
        var verificationCode = UserData.getVerificationCode();
        login.validLogin(authInfo);
        verification.validVerificationCode(verificationCode);
        var balanceActual = new DashboardPage();
        int firstCardBalance = balanceActual.getFirstCardBalance();
        int secondCardBalance = balanceActual.getSecondCardBalance();
        var transfer = new TransferPage();
        transfer.setTransferAmount(UserData.generateValidAmount(balanceActual.getFirstCardBalance()));
        transfer.setCardNumber(UserData.getFirstCardNumber().getNumber());
        transfer.makeInvalidTransfer();
        login.findErrorMessage("Выполнена попытка превода суммы, превышающей остаток на карте списания");
        var balanceExpected = new DashboardPage();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance);
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance);
    }
}
