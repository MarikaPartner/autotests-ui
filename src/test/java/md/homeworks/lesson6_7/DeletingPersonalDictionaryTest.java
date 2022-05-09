package md.homeworks.lesson6_7;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import md.homeworks.lesson6_7.listener.AllureListener;
import md.homeworks.lesson6_7.pages.MainPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.util.concurrent.TimeUnit;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

@DisplayName("Удаление словарей")
public class DeletingPersonalDictionaryTest extends BaseTest {
    @BeforeAll
    static void setUP() {
        webDriver = new EventFiringWebDriver(WebDriverManager.chromedriver().create());
        webDriver.register(new AllureListener());
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1800, 1300));
        new MainPage(webDriver).login(LOGIN, PASSWORD);
    }
    @AfterEach
    void recordLogs() {
        step("Логи браузера", () -> {
            webDriver.manage().logs().get(LogType.BROWSER)
                    .forEach(log -> addAttachment("logs", log.getMessage()));
        });
    }
    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @ParameterizedTest(name = "Удаление словаря из раздела 'Все словари'")
    @ValueSource(strings = {"Словарь для удаления"})
    @DisplayName( "Удаление словаря {dictionary.nameDictionary} из раздела 'Все словари'")
    @Severity(SeverityLevel.NORMAL)
    @Description("Создание персонального словаря и его удаление из раздела 'Все словари'")
    void deletingPersonalDictionaryTest(Dictionary dictionary) throws InterruptedException {

        new MainPage(webDriver)
                .goToDictionariesPageFromTheMainPage()
                .openCreatingDictionaryForm()
                .inputNameDictionary(dictionary)
                .createDictionary()
                .goToDictionariesPageFromDictionaryPage()
                .deleteDictionary(dictionary)
                .checkDictionaryListIsNotContainsDictionary(dictionary);
    }
}