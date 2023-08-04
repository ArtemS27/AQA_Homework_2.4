package ru.netology.appibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private String firstCardBalance = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").text();
    private String secondCardBalance = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").text();
    private String balanceStart = "баланс: ";
    private String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage(){
        heading.shouldBe(Condition.visible);
    }

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
