package md.homeworks.lesson6.pages;

import md.homeworks.lesson6.Dictionary;
import md.homeworks.lesson6.pages.block.CreateDictionaryPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

public class DictionariesPage extends BasePage {

    public DictionariesPage(WebDriver webDriver) {
        super(webDriver);
        webDriver.get("https://myefe.ru/pd");
    }

    // Открытие формы создания словаря
    public CreateDictionaryPopup openCreatingDictionaryForm() {
        webDriver.findElement(By.xpath("//button[contains(text(),'Новый словарь')]")).click();
        return new CreateDictionaryPopup(webDriver);
    }

    // Проверка, что в списоке словарей есть указанный словарь
    public void checkDictionaryListContainsDictionary(Dictionary dictionary) {
        List<String> dictionariesList = webDriver.findElements(By.xpath("//div[@class='ui stackable vertically divided very relaxed grid mld-list']//a[contains(@class,'header')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertThat(dictionariesList).contains(dictionary.getNameDictionary());
    }

    // Проверка, что в списоке словарей указанный словарь отсутствует
    public void checkDictionaryListIsNotContainsDictionary(Dictionary dictionary) {
        List<String> dictionariesList = webDriver.findElements(By.xpath("//div[@class='ui stackable vertically divided very relaxed grid mld-list']//a[contains(@class,'header')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertThat(dictionariesList).doesNotContain(dictionary.getNameDictionary());
    }

    // Удаление словаря
    public DictionariesPage deleteDictionary(Dictionary dictionary) {
        webDriver.findElement(By.xpath("//div[@class='ui stackable grid'][contains(.,'" + dictionary.getNameDictionary() + "')]"))
                .findElement(By.xpath(".//button[contains(@class,'red')]")).click();
        webDriver.findElement(By.xpath("//button[text()='Удалить']")).click();
        return new DictionariesPage(webDriver);
    }
}