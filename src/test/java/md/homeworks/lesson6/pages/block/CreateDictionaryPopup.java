package md.homeworks.lesson6.pages.block;

import md.homeworks.lesson6.Dictionary;
import md.homeworks.lesson6.pages.DictionariesPage;
import md.homeworks.lesson6.pages.DictionaryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

public class CreateDictionaryPopup implements WrapsElement {
    private final WebElement self;
    private WebDriver webDriver;

    // Окно с формой создания сдловаря
    public CreateDictionaryPopup(WebDriver webDriver) {
        this.self = webDriver.findElement(By.xpath("//div[@class='ui tiny modal transition visible active']"));
        this.webDriver = webDriver;
    }

    // Ввод названия словаря
    public CreateDictionaryPopup inputNameDictionary(Dictionary dictionary) {

        self.findElement(By.xpath(".//div[@class='ui input']/input")).click();
        self.findElement(By.xpath(".//div[@class='ui input']/input")).clear();
        self.findElement(By.xpath(".//div[@class='ui input']/input"))
                .sendKeys(dictionary.getNameDictionary());
        return this;
    }

    // Выбор направления перевода
    public CreateDictionaryPopup inputTranslationDirection(Dictionary dictionary) {
        self.findElement(By.xpath("//a[@class='ui basic label']")).click();
        self.findElement(By.xpath("//div[@class='ui bulleted divided relaxed list']//*[text()='"+ dictionary.getTranslationDirection() +"']"))
                .click();
        return this;
    }

    // Создание словаря
    public DictionaryPage createDictionary() {

        self.findElement(By.xpath("//button[text()='Создать']")).click();
        return new DictionaryPage(webDriver);
    }

    // Отмена создания словаря
    public DictionariesPage cancelCreatingDictionary() {

        self.findElement(By.xpath("//button[text()='Отмена']")).click();
        return new DictionariesPage(webDriver);
    }

    @Override
    public WebElement getWrappedElement() {
        return self;
    }
}
