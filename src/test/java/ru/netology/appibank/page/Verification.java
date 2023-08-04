package ru.netology.appibank.page;

import org.openqa.selenium.Keys;
import ru.netology.appibank.data.UserData;

import static com.codeborne.selenide.Selenide.$;

public class Verification {
    public void validVerificationCode(UserData.VerificationCode verificationCode){
        $("[data-test-id=code] input").setValue(verificationCode.getCode());
        $("[data-test-id=action-verify]").click();
    }

    public void cleanVerificationCodeField(){
        $("[data-test-id=code] input").doubleClick();
        $("[data-test-id=code] input").sendKeys(Keys.BACK_SPACE);
    }
}
