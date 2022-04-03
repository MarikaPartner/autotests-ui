package md.homeworks.lesson5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

public class DeletingPersonalDictionaryTest extends BaseTest {

    @Test
    @DisplayName("Удаление персонального словаря из раздела 'Все словари'")
    void deletingPersonalDictionaryTest() throws InterruptedException {

        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

//      Переход в раздел "Все словари"
        WebElement mainMenu = webDriver.findElement(By.id("msui-sidebar"));    // Открываем главное меню
        mainMenu.findElement(By.xpath("//a[contains(text(),'Персональные словари')]")).click();

//      Создание тестового словаря
        webDriver.findElement(By.xpath("//button[contains(text(),'Новый словарь')]")).click();
        WebElement modalWinNewDictionary = webDriver.findElement(By.xpath("//div[@class='ui tiny modal transition visible active']"));  // Модальное окно
        WebElement inputDictionaryName = modalWinNewDictionary.findElement(By.xpath("//div[@class='ui input']/input"));  // Поле ввода названия словаря
        inputDictionaryName.click();
        inputDictionaryName.clear();
        String nameDictionary = "Тестовый словарь " + nextInt();
        inputDictionaryName.sendKeys(nameDictionary);
        modalWinNewDictionary.findElement(By.xpath("//button[text()='Создать']")).click();

        webDriver.findElement(By.xpath("//a[text()='Меню']")).click();
        webDriver.findElement(By.xpath("//span[text()='Все словари']")).click();

//      Удаление выбранного словаря

        // Находим созданный словарь и нажимаем на иконку "x"
        webDriver.findElement(By.xpath("//div[@class='ui stackable grid'][contains(.,'" + nameDictionary + "')]"))
                .findElement(By.xpath(".//button[@class='ui red tiny basic icon button']")).click();

        // Проверяем, что модальное окно удаления словаря содержит название нужного словаря и предупреждение о удалении
        assertThat(webDriver.findElement(By.xpath("//div[@class='ui header']//div[@class='content']")).getText())
                .isEqualTo(nameDictionary);
        assertThat(webDriver.findElement(By.xpath("//div[@class='content']//p")).getText())
                .isEqualTo("Вы действительно хотите удалить словать безвозвратно?");

        // Удаляем словарь и проверяем, что напротив названия словаря есть пометка "Удален"
        webDriver.findElement(By.xpath("//button[text()='Удалить']")).click();
        List<String> elements = webDriver.findElements(By.xpath("//div[text()='" + nameDictionary + "']/ancestor::div[@class='row']//*"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertThat(elements).contains("Удален");

        // Обновляем страницу и проверяем, что удаленного словаря нет в списке словарей
        webDriver.navigate().refresh();
        List<String> dictionaries = webDriver.findElements(By.xpath("//a[@class='ui blue medium header']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertThat(dictionaries).doesNotContain(nameDictionary);
    }
}