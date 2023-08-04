package ru.netology.appibank.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.appibank.data.UserData;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement firstCardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id=action-deposit]");
    private final SelenideElement secondCardButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id=action-deposit]");
    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement numberField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private String transferAmount;
    public void transferFromSecondCardToFirst(){
        var dashboard = new DashboardPage();
        transferAmount = UserData.generateValidAmount(dashboard.getSecondCardBalance());
        firstCardButton.click();
        amountField.setValue(transferAmount);
        numberField.setValue(UserData.getSecondCardNumber().getNumber());
        transferButton.click();
    }

    public void transferFromFirstCardToSecond() {
        var dashboard = new DashboardPage();
        transferAmount = UserData.generateValidAmount(dashboard.getFirstCardBalance());
        secondCardButton.click();
        amountField.setValue(transferAmount);
        numberField.setValue(UserData.getFirstCardNumber().getNumber());
        transferButton.click();
    }

    public void makeInvalidTransfer() {
        var dashboard = new DashboardPage();
        transferAmount = UserData.generateInvalidAmount(dashboard.getFirstCardBalance());
        secondCardButton.click();
        amountField.setValue(transferAmount);
        numberField.setValue(UserData.getFirstCardNumber().getNumber());
        transferButton.click();
    }

    public int getTransferAmount(){
        return Integer.parseInt(transferAmount);
    }
}
