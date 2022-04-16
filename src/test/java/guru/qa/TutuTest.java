package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;


public class TutuTest {

    @BeforeEach
    public void beforeEach() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://www.tutu.ru/";
        Configuration.browserSize = "1920x1080";
    }
    @CsvSource({
            "Москва, Казань, 18, 21"
    })
    @DisplayName("Тест на проверку наличия билетов по маршруту: {0}-{1}- с датами:  {2}-{3}")
    @ParameterizedTest(name = "Тест на проверку наличия билетов по маршруту: {0}-{1}- с датами:  {2}-{3}")
    void findPriceTest(String cityfrom, String cityTo, String dateFrom, String dateTo) {
        open(baseUrl);
        $(".input_field.j-city_input.j-city_from.j-main").setValue(cityfrom).pressEnter();
        $(".input_field.j-city_input.j-city_to.j-main").setValue(cityTo).pressEnter();
        $(".j-date_from").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + dateFrom +"')]").click();
        $(".j-date_back").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + dateTo +"')]").click();
        $(".button_wrp.j-buttons_block").click();
        $x("//span[contains(text(), 'предлож')]").shouldHave(text("предлож"));
    }

//запускаю поиск без обратного билета
    @CsvSource({
            "Москва, Казань, 18"
    })
    @ParameterizedTest(name = "Тест на проверку наличия билетов по маршруту: {0}-{1}- на дату:  {2}")
    void findPriceTestOneWayTicket(String cityfrom, String cityTo, String dateFrom) {
        open(baseUrl);
        $(".input_field.j-city_input.j-city_from.j-main").setValue(cityfrom).pressEnter();
        $(".input_field.j-city_input.j-city_to.j-main").setValue(cityTo).pressEnter();
        $(".j-date_from").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + dateFrom +"')]").click();
        //При запуске на IOS без слипа тест отображается как пройденный, но фактически в хроме не происходит нажатия
        // на кнопку! Не нашла способа кроме слип. На машине с WINDWS тест отрабатывает без слип.
        sleep(500);
        $(".button_wrp.j-buttons_block").click();
        $x("//span[contains(text(), 'предлож')]").shouldHave(text("предлож"));
    }

///  запускаю 2 маршрута через массив
    @CsvSource({
            "Москва, Казань, 18, 21",
            "Москва, Самара, 21, 25"
    })
    @DisplayName("Тест на проверку наличия билетов по маршруту: {0}-{1} с датами:  {2}-{3}")
    @ParameterizedTest(name = "Тест на проверку наличия билетов по маршруту: {0}-{1} с датами:  {2}-{3}")
    void findPriceTest2(String cityfrom, String cityTo, String dateFrom, String dateTo) {
        open(baseUrl);
        Configuration.holdBrowserOpen = true;
        $(".input_field.j-city_input.j-city_from.j-main").setValue(cityfrom).pressEnter();
        $(".input_field.j-city_input.j-city_to.j-main").setValue(cityTo).pressEnter();
        $(".j-date_from").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + dateFrom +"')]").click();
        $(".j-date_back").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + dateTo +"')]").click();
        sleep(1000);
        $(".button_wrp.j-buttons_block").click();
        $x("//span[contains(text(), 'предлож')]").shouldHave(text("предлож"));
    }

}
