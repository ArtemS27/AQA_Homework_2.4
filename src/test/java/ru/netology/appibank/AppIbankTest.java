package ru.netology.appibank;

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
        String transferAmount = UserData.generateValidAmount(balanceActual.getFirstCardBalance());
        balanceActual.pushSecondCardButton();
        transfer.makeTransfer(transferAmount, UserData.getFirstCardNumber().getNumber());
        var balanceExpected = new DashboardPage();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance-Integer.parseInt(transferAmount));
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance+Integer.parseInt(transferAmount));
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
        String transferAmount = UserData.generateValidAmount(balanceActual.getSecondCardBalance());
        balanceActual.pushFirstCardButton();
        transfer.makeTransfer(transferAmount, UserData.getSecondCardNumber().getNumber());
        var balanceExpected = new DashboardPage();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance+Integer.parseInt(transferAmount));
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance-Integer.parseInt(transferAmount));
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
        balanceActual.pushSecondCardButton();
        transfer.makeTransfer(UserData.generateInvalidAmount(balanceActual.getFirstCardBalance()), UserData.getFirstCardNumber().getNumber());
        login.findErrorMessage("Выполнена попытка превода суммы, превышающей остаток на карте списания");
        var balanceExpected = new DashboardPage();
        Assertions.assertEquals(balanceExpected.getFirstCardBalance(), firstCardBalance);
        Assertions.assertEquals(balanceExpected.getSecondCardBalance(), secondCardBalance);
    }
}
