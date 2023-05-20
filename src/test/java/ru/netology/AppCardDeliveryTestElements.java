package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class AppCardDeliveryTestElements {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisteredByCalendar() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Че");
        $$(".menu-item__control").first().click();
        String defaultMonth = generateDate(3,"MM");
        String planningMonth = generateDate(7,"MM");
        String planningDate = generateDate(7, "dd.MM.yyyy");
        int day = Integer.parseInt(generateDate(7,"dd"));
        if (!defaultMonth.equals(planningMonth)){
            $(".calendar-input [role='button']").click();
            $("[data-step='1']").click();
            $$x("//td[@data-day]").get(day - 1).click();
        } else {
            $(".calendar-input [role='button']").click();
            $$x("//td[contains(@class, 'calendar__day')]").get(day - 1).click();
        }
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}