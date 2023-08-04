package ru.netology.appibank.page;

import ru.netology.appibank.data.UserData;
import static com.codeborne.selenide.Selenide.*;
public class Login {

    public void validLogin(UserData.AuthInfo authInfo){
        $("[data-test-id=login] input").setValue(authInfo.getLogin());
        $("[data-test-id=password] input").setValue(authInfo.getPassword());
        $("[data-test-id=action-login]").click();
    }

}
