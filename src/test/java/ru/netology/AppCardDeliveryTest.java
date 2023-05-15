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

    @Test
    void shouldRegistered() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);
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
        LocalDate today = LocalDate.now().plusDays(10);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);
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
    @Test // дату встречи не трогаем - заполнена по умолчанию
    void shouldRegisteredDateDefault() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test // проверка на пустое поле Город
    void shouldRegisteredEmptyFieldCity() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='city' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }

    @Test //пустое поле даты
    void shouldRegisteredEmptyFieldDate() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        //Configuration.browserSize = "200x900";//размер браузера при открытии
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        //$("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input_invalid')]//span[contains(text(), 'Неверно')]").should(Condition.appear);
    }
    @Test //проверка на пустое поле Фамилия и имя
    void shouldRegisteredEmptyFieldName() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }
    @Test //пустое поле Номер телефона
    void shouldRegisteredEmptyFieldPhone() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }
    @Test //не проставлен чек-бокс
    void shouldRegisteredEmptyFieldCheckBox() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        //$("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//label[@data-test-id='agreement' and contains(@class, 'input_invalid')]//span[contains(text(), 'Я " +
                "соглашаюсь')]").shouldBe(Condition.visible);
    }

    @Test
    void shouldRegisteredCityIsNotCenter() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Новочебоксарск");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='city' and contains(@class, 'input_invalid')]//span[contains(text(), 'Доставка')]").should(Condition.appear);
    }
    @Test //Дата встречи сегодня
    void shouldRegisteredToday() {
        LocalDate today = LocalDate.now().plusDays(0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input_invalid')]//span[contains(text(), 'Заказ')]").should(Condition.appear);
    }

    @Test //Дата встречи через 2 дня
    void shouldRegisteredDateAfter2Days() {
        LocalDate today = LocalDate.now().plusDays(2);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input_invalid')]//span[contains(text(), 'Заказ')]").should(Condition.appear);
    }
    @Test //невалидные значения для фамилии латиница
    void shouldRegisteredNameLatin() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Grigoriev Ivan");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Допустимы')]").should(Condition.appear);
    }

    @Test //невалидные значения для фамилии спецсимволы
    void shouldRegisteredNameSpecialSymbol() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев_Василий");
        $("[data-test-id='phone'] .input__control").setValue("+79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Допустимы')]").should(Condition.appear);
    }
    @Test //пустое поле Номер телефона 10 цифр
    void shouldRegisteredEmptyFieldPhone10Numb() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+7987987112");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Телефон')]").should(Condition.appear);
    }
    @Test //пустое поле Номер телефона 12 цифр
    void shouldRegisteredEmptyFieldPhone12Numb() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("+798798711222");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Телефон')]").should(Condition.appear);
    }
    @Test //пустое поле Номер телефона без плюса
    void shouldRegisteredEmptyFieldPhoneWhithOutAPlus() {
        LocalDate today = LocalDate.now().plusDays(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = today.format(dateTimeFormatter);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Чебоксары");
        $("[data-test-id='date'] [formnovalidate]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] [formnovalidate]").setValue(date).sendKeys(Keys.ENTER);
        $("[data-test-id='name'] .input__control").setValue("Васильев Василий");
        $("[data-test-id='phone'] .input__control").setValue("79879871122");
        $("[data-test-id='agreement']").click();
        $$("[role='button']").filter(Condition.visible).last().click();
        $x("//span[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[contains(text(), 'Телефон')]").should(Condition.appear);
    }
}