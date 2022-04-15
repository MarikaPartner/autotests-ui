package md.homeworks.lesson6;

import io.github.bonigarcia.wdm.WebDriverManager;
import md.homeworks.lesson6.pages.MainPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Dimension;
import java.util.concurrent.TimeUnit;

public class DeletingPersonalDictionaryTest extends BaseTest {

    @BeforeAll
    static void setUP() {
        webDriver = WebDriverManager.chromedriver().create();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1800, 1300));
        new MainPage(webDriver).login(LOGIN, PASSWORD);
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @ParameterizedTest(name = "Удаление персонального словаря из раздела 'Все словари'")
    @ValueSource(strings = {"Словарь для удаления"})
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