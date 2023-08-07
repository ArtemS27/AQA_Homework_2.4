package ru.netology.appibank.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.appibank.data.UserData;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement numberField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void makeTransfer(String transferAmount, String cardNumber){
        amountField.setValue(transferAmount);
        numberField.setValue(cardNumber);
        transferButton.click();
    }
}
