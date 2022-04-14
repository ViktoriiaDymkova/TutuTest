package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @ParameterizedTest(name = "Тест на проверку наличия билетов по маршруту: {0}-{1}- с датами:  {2}-{3}")
    void findPriceTest(String first, String second, String third, String fourth) {
        open(baseUrl);
        $(".input_field.j-city_input.j-city_from.j-main").setValue(first).pressEnter();
        $(".input_field.j-city_input.j-city_to.j-main").setValue(second).pressEnter();
        $(".j-date_from").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + third +"')]").click();
        $(".j-date_back").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + fourth +"')]").click();
        $(".button_wrp.j-buttons_block").click();
    }

    @CsvSource({
            "Москва, Казань, 18"
    })
    @ParameterizedTest(name = "Тест на проверку наличия билетов по маршруту: {0}-{1}- с датой:  {2}")
    void findPriceTestOneWayTicket(String first, String second, String third) {
        open(baseUrl);
        $(".input_field.j-city_input.j-city_from.j-main").setValue(first).pressEnter();
        $(".input_field.j-city_input.j-city_to.j-main").setValue(second).pressEnter();
        $(".j-date_from").shouldBe(Condition.visible).click();
        $x("//a[contains(text(),'" + third +"')]").click();
        /// при запуске на IOS  без слипа тест отображается как пройденный, но фактически в хроме не
        ///происходит нажатия на кнопку! Не нашла способа кроме слип. На машине с WINDWS тест отрабатывает без слип.
        //sleep(500);
        $(".button_wrp.j-buttons_block").click();
    }
}
