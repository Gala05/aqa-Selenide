package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    LocalDate today = LocalDate.now().plusDays(3);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String date = today.format(dateTimeFormatter);

    @Test
    void shouldRegistered() {
        Configuration.holdBrowserOpen = true; //не закрывать автоматически браузер после теста
//        Configuration.browserSize = "200x900";//размер браузера при открытии
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
    @Test
    void shouldRegisteredNameWithDash() {
        Configuration.holdBrowserOpen = true; //не закрывать автоматически браузер после теста
//        Configuration.browserSize = "200x900";//размер браузера при открытии
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Якутск");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев-Иванов Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}