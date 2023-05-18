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
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(4, "dd.MM.yyyy");

    @Test
    void shouldRegistered() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldRegisteredNameWithDash() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Якутск");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев-Иванов Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test // дату встречи не трогаем - заполнена по умолчанию
    void shouldRegisteredDateDefault() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        String date = $("[data-test-id='date'] [formnovalidate]").getValue();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void checkingDateWithThePopUpMessage() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Якутск");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев-Иванов Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test // проверка на пустое поле Город
    void shouldRegisteredEmptyFieldCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='city' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }

    @Test //пустое поле даты
    void shouldRegisteredEmptyFieldDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input_invalid')]//span[contains(text(), 'Неверно')]").should(Condition.appear);
    }

    @Test //проверка на пустое поле Фамилия и имя
    void shouldRegisteredEmptyFieldName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }

    @Test //пустое поле Номер телефона
    void shouldRegisteredEmptyFieldPhone() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }

    @Test //не проставлен чек-бокс
    void shouldRegisteredEmptyFieldCheckBox() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        //$("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//label[@data-test-id='agreement' and contains(@class, 'input_invalid')]//span[contains(text(), 'Я " +
                "соглашаюсь')]").shouldBe(Condition.visible);
    }

    @Test
    void shouldRegisteredCityIsNotCenter() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Новочебоксарск");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='city' and contains(@class, 'input_invalid')]//span[contains(text(), 'Доставка')]").should(Condition.appear);
    }

    @Test //Дата встречи сегодня
    void shouldRegisteredToday() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(generateDate(0, "dd.MM.yyyy")).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input_invalid')]//span[contains(text(), 'Заказ')]").should(Condition.appear);
    }

    @Test //Дата встречи через 2 дня
    void shouldRegisteredDateAfter2Days() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(generateDate(2, "dd.MM.yyyy")).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input_invalid')]//span[contains(text(), 'Заказ')]").should(Condition.appear);
    }

    @Test //невалидные значения для фамилии латиница
    void shouldRegisteredNameLatin() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Grigoriev Ivan");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Допустимы')]").should(Condition.appear);
    }

    @Test //невалидные значения для фамилии спецсимволы
    void shouldRegisteredNameSpecialSymbol() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев_Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Допустимы')]").should(Condition.appear);
    }

    @Test //пустое поле Номер телефона 10 цифр
    void shouldRegisteredEmptyFieldPhone10Numb() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+7987987112");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Телефон')]").should(Condition.appear);
    }

    @Test //пустое поле Номер телефона 12 цифр
    void shouldRegisteredEmptyFieldPhone12Numb() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+798798711222");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Телефон')]").should(Condition.appear);
    }

    @Test //пустое поле Номер телефона без плюса
    void shouldRegisteredEmptyFieldPhoneWhithOutAPlus() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(planningDate).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Телефон')]").should(Condition.appear);
    }
}