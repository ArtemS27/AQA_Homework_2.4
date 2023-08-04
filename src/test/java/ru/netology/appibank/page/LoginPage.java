package ru.netology.appibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.appibank.data.UserData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement notificationMessage = $("[data-test-id=error-notification]");
    private final SelenideElement mainPage = $(".heading");

    public VerificationPage validLogin(UserData.AuthInfo authInfo){
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void findErrorMessage(String expectedText){
        notificationMessage.shouldHave(Condition.text(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public void returnToMainPage(){
        mainPage.shouldBe(Condition.visible);
    }
}
