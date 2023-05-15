package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class AppCardDeliveryTestElements {
    @Test
    void shouldRegisteredByCalendar() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $(".calendar-input [role='button']").click();
        $("[data-step='1']").click();
        $$(".popup td[data-day]:not([data-day=''])").last().click();
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
    @Test
    void shouldRegisteredByDropDownList() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Че");
        $$(".menu-item__control").first().click();
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
