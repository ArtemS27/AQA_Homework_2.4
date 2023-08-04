package ru.netology.appibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.appibank.data.UserData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    public void validVerificationCode(UserData.VerificationCode verificationCode){
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
    }

    public void cleanVerificationCodeField(){
        codeField.doubleClick();
        codeField.sendKeys(Keys.BACK_SPACE);
    }

}
