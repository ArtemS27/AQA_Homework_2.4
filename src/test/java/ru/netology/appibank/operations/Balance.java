package ru.netology.appibank.operations;

import lombok.val;

import static com.codeborne.selenide.Selenide.$;

public class Balance {
    private String firstCardBalance = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").text();
    private String secondCardBalance = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").text();
    private String balanceStart = "баланс: ";
    private String balanceFinish = " р.";

    public Balance(){}

    public int getFirstCardBalance(){
        return extractBalance(firstCardBalance);
    }

    public int getSecondCardBalance(){
        return extractBalance(secondCardBalance);
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
