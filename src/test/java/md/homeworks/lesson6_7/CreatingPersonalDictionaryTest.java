package md.homeworks.lesson6_7;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import md.homeworks.lesson6_7.listener.AllureListener;
import md.homeworks.lesson6_7.pages.DictionariesPage;
import md.homeworks.lesson6_7.pages.MainPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@DisplayName("Создание словарей")
public class CreatingPersonalDictionaryTest extends BaseTest {

    @BeforeAll
    static void setUP() {
        webDriver = new EventFiringWebDriver(WebDriverManager.chromedriver().create());
        webDriver.register(new AllureListener());
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
    @DisplayName("Создание словаря через кнопку в главном меню")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Создание персонального словаря через кнопку в главном меню")
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
    @Disabled("Тест отключен для разнообразия в отчете")
    @ValueSource(strings = {"Словарь для отмены создания"})
    @DisplayName("Отмена создания словаря")
    @Severity(SeverityLevel.MINOR)
    @Description("Отмена создания словаря в процессе создания словаря")
     void cancelCreatingDictionaryTest(Dictionary dictionary) throws InterruptedException {

        webDriver.get("https://myefe.ru/pd");
        new DictionariesPage(webDriver)
                .openCreatingDictionaryForm()
                .inputNameDictionary(dictionary)
                .cancelCreatingDictionary()
                .checkDictionaryListIsNotContainsDictionary(dictionary);
    }
}