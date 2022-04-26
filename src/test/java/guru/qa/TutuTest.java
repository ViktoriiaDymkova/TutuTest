package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;


public class TutuTest {

    @BeforeEach
    public void beforeEach() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://www.tutu.ru/";
        Configuration.browserSize = "1920x1080";
    }

    //поиск наличия ресурсов
    @ValueSource(strings = {"Отели",
            "Туры"})
    @ParameterizedTest(name = "Тест на проверку наличия {0}")
    void findOtherOptions(String value) {
        open(baseUrl);
        $x("//a[.//*[@class='link' and contains(text(),'" + value + "')]]").click();
        $("button.order-group-element div").shouldHave(text(value));
    }

    ///  запускаю 2 маршрута через массив
    @CsvSource({
            "Москва, Казань, 29, 30",
            "Москва, Самара, 29, 30"
    })
    @DisplayName("Тест на проверку наличия билетов по маршруту: {0}-{1} с датами:  {2}-{3}")
    @ParameterizedTest(name = "Тест на проверку наличия билетов по маршруту: {0}-{1} с датами:  {2}-{3}")
    void findPriceTest2(String cityfrom, String cityTo, int dateFrom, int dateTo) {
        open(baseUrl);
        Configuration.holdBrowserOpen = true;
        //выбор городов
        $(".input_field.j-city_input.j-city_from.j-main").setValue(cityfrom).pressEnter();
        $(".input_field.j-city_input.j-city_to.j-main").setValue(cityTo).pressEnter();
        //нажатие на календарь
        $(".j-date_from").shouldBe(visible).click();
        //проверяем видимость календаря тк этот селектор не виден без вызова календаря
        $(".datepicker_wrapper div#ui-datepicker-div").shouldBe(visible);
        //поставновка даты туда
        $x("//a[contains(text(),'" + dateFrom + "')]").click();
        // жмем 2 календ
        $(".j-date_back").shouldBe(visible).click();
        //проверяем видимость календаря тк этот селектор так же не виден без вызова календаря
        $(".datepicker_wrapper div#ui-datepicker-div").shouldBe(visible);
        //поставновка даты обратно
        $x("//a[contains(text(),'" + dateTo + "')]").click();
        //клик
        $(".button_wrp.j-buttons_block").click();
        //проверка
        $("#root").shouldHave(text("предлож"));
    }

    @DisplayName("Отправление поездов")
    static Stream<Arguments> parametrizeTestMethodSource() {
        return Stream.of(
                Arguments.of("в Москву ", List.of(6, 7)),
                Arguments.of("в СПб ", List.of(8, 10))
        );
    }

    @MethodSource("parametrizeTestMethodSource")
    @ParameterizedTest
    void parametrizeTestMethodSource(String first, List<Integer> second) {
        System.out.println(first + "c платформы №" + second);
    }
}
