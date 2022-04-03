package md.homeworks.lesson5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatingPersonalDictionaryTest extends BaseTest {

    @Test
    @DisplayName("Создание персонального словаря через кнопку в главном меню: название по умолчанию, направление перевода по умолчанию")
    void createPersonalDictionaryWithDefaultNameAndDefaultDirectionOfTranslationTest() {

        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

//      Переход в раздел "Все словари"
        webDriver.findElement(By.id("msui-sidebar"))
                .findElement(By.xpath("//a[contains(text(),'Персональные словари')]")).click();

//      Создание нового словаря
        webDriver.findElement(By.xpath("//button[contains(text(),'Новый словарь')]")).click();
        WebElement modalWinNewDictionary = webDriver.findElement(By.xpath("//div[@class='ui tiny modal transition visible active']"));
        String windowHeader = modalWinNewDictionary.findElement(By.className("header")).getText();
        assertThat(windowHeader).isEqualTo("Новый словарь");  //  Проверяем что модальное окно создания словаря содержит заголовок "Новый словарь"

//      Проверяем, что по умолчанию подставляется название "Словарь №<..>"
        String valueInput = modalWinNewDictionary.findElement(By.xpath("//div[@class='ui input']//input")).getAttribute("value");
        assertThat(valueInput).contains("Словарь №");

//      Проверяем, что на странице есть заголовок с названием созданного словаря
        modalWinNewDictionary.findElement(By.xpath("//button[text()='Создать']")).click();
        String a = webDriver.findElement(By.xpath("//span[@class='ui medium header']")).getText();
        assertThat(webDriver.findElement(By.xpath("//span[@class='ui medium header']")).getText()).isEqualTo(valueInput);

//      Проверяем, что в списке словарей появился созданный словарь
        webDriver.findElement(By.xpath("//a[text()='Меню']")).click();
        webDriver.findElement(By.xpath("//span[text()='Все словари']")).click();
        List<String> dictionaries = webDriver.findElements(By.xpath("//a[@class='ui blue medium header']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        int v;
        assertThat(dictionaries).contains(valueInput);
    }

    @Test
    @DisplayName("Отмена создания словаря")
    void cancelCreatingPersonalDictionaryTest() throws InterruptedException {

        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

        webDriver.get("https://myefe.ru/pd/");

//      Запоминаем список словарей
        List<String> dictionaryBefore = webDriver.findElements(By.xpath("//div[@class='row pd-dict-header']//a[contains(@class, 'header')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

//      Отмена создания нового словаря
        webDriver.findElement(By.xpath("//button[contains(text(),'Новый словарь')]")).click();
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui tiny modal transition visible active']")));
        webDriver.findElement(By.xpath("//button[text()='Отмена']")).click();

//      Проверяем, что список словарей не изменился
        List<String> dictionaryAfter = webDriver.findElements(By.xpath("//div[@class='row pd-dict-header']//a[contains(@class, 'header')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertThat(dictionaryAfter).isEqualTo(dictionaryBefore);
    }
}

