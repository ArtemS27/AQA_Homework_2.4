package ru.netology.appibank.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.appibank.data.UserData;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement numberField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private String transferAmount;
    private String cardNumber;
    public void transferFromSecondCardToFirst(){
        var dashboard = new DashboardPage();
        dashboard.pushFirstCardButton();
        amountField.setValue(transferAmount);
        numberField.setValue(cardNumber);
        transferButton.click();
    }

    public void transferFromFirstCardToSecond() {
        var dashboard = new DashboardPage();
        dashboard.pushSecondCardButton();
        amountField.setValue(transferAmount);
        numberField.setValue(cardNumber);
        transferButton.click();
    }

    public void makeInvalidTransfer() {
        var dashboard = new DashboardPage();
        dashboard.pushSecondCardButton();
        amountField.setValue(transferAmount);
        numberField.setValue(cardNumber);
        transferButton.click();
    }

    public int getTransferAmount(){
        return Integer.parseInt(transferAmount);
    }

    public String setTransferAmount(String amount){
        transferAmount = amount;
        return transferAmount;
    }

    public String setCardNumber(String num){
        cardNumber = num;
        return cardNumber;
    }
}
