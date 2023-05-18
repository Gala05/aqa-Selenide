package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class AppCardDeliveryTestElements {
    private String planningDate;

    @Test
    void shouldRegisteredByCalendar() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Че");
        $$(".menu-item__control").first().click();
        $(".calendar-input [role='button']").click();
        $("[data-step='1']").click();
        $$(".popup td[data-day]:not([data-day=''])").last().click();
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        String date = $("[data-test-id='date'] [formnovalidate]").getValue();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
