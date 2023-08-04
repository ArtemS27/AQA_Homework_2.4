package ru.netology.appibank.operations;
import ru.netology.appibank.data.UserData;

import static com.codeborne.selenide.Selenide.$;

public class Transfer {
    public void transferFromSecondCardToFirst(){
        $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id=action-deposit]").click();
        $("[data-test-id=amount] input").setValue(UserData.transferAmount("500").getTransferAmount());
        $("[data-test-id=from] input").setValue(UserData.getSecondCardNumber().getNumber());
        $("[data-test-id=action-transfer]").click();
        $("[data-test-id=action-reload]").click();
    }

    public void transferFromFirstCardToSecond() {
        $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id=action-deposit]").click();
        $("[data-test-id=amount] input").setValue(UserData.transferAmount("500").getTransferAmount());
        $("[data-test-id=from] input").setValue(UserData.getFirstCardNumber().getNumber());
        $("[data-test-id=action-transfer]").click();
        $("[data-test-id=action-reload]").click();
    }
}
