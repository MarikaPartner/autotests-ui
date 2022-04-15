package md.homeworks.lesson6;

import io.github.bonigarcia.wdm.WebDriverManager;
import md.homeworks.lesson6.pages.DictionariesPage;
import md.homeworks.lesson6.pages.MainPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Dimension;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class CreatingPersonalDictionaryTest extends BaseTest {

    @BeforeAll
    static void setUP() {
        webDriver = WebDriverManager.chromedriver().create();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        new MainPage(webDriver).login(LOGIN, PASSWORD);
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    //  Тестовые данные
    public static Stream<Dictionary> newDictionaries() {
        return Stream.of(new Dictionary("Тестовый словарь испанский", "Испанский-Русский"),
                new Dictionary("Тестовый словарь немецкий", "Немецкий-Русский"),
                new Dictionary("Тестовый словарь французкий", "Французский-Русский"),
                new Dictionary("Тестовый словарь чешский", "Чешский-Русский"));
    }

    @ParameterizedTest(name = "Создание словаря через кнопку в главном меню {0}")
    @MethodSource("newDictionaries")
    void createDictionaryFromMainMenuTest(Dictionary dictionary)  throws InterruptedException {

        webDriver.get("https://myefe.ru/pd");
        new DictionariesPage(webDriver)
                .openCreatingDictionaryForm()
                .inputNameDictionary(dictionary)
                .inputTranslationDirection(dictionary)
                .createDictionary()
                .checkCreatedDictionary(dictionary)
                .goToDictionariesPageFromDictionaryPage()
                .checkDictionaryListContainsDictionary(dictionary);
    }

    @ParameterizedTest (name = "Отмена создания словаря")
    @ValueSource(strings = {"Словарь для отмены создания", "Английский-Русский"})
     void cancelCreatingDictionaryTest(Dictionary dictionary) throws InterruptedException {

        webDriver.get("https://myefe.ru/pd");
        new DictionariesPage(webDriver)
                .openCreatingDictionaryForm()
                .inputNameDictionary(dictionary)
                .cancelCreatingDictionary()
                .checkDictionaryListIsNotContainsDictionary(dictionary);
    }
}