package md.homeworks.lesson6.pages;

import md.homeworks.lesson6.Dictionary;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DictionaryPage extends BasePage {

    public DictionaryPage(WebDriver webDriver) {
        super(webDriver);
    }

    // Проверяем, что с указанным названием и выбранным направлением перевода
    public DictionaryPage checkCreatedDictionary(Dictionary dictionary) {

        SoftAssertions softAssertionsAfter = new SoftAssertions();
        softAssertionsAfter.assertThat(webDriver.findElement(By.xpath("//span[contains(@class,'header')]")).getText())
                .isEqualTo(dictionary.getNameDictionary());
        softAssertionsAfter.assertThat(webDriver.findElement(By.xpath("//a[@class='ui blue basic label']")).getText())
                .isEqualTo(dictionary.getTranslationDirection());
        softAssertionsAfter.assertAll();
    return this;

    }

    // Переход в раздел словарей со страницы словаря
    public DictionariesPage goToDictionariesPageFromDictionaryPage() {

        webDriver.findElement(By.xpath("//i[@class='chevron left icon']")).click();
        return new DictionariesPage(webDriver);
    }
}