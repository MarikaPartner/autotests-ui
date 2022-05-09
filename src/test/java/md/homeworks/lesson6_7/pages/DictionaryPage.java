package md.homeworks.lesson6_7.pages;

import io.qameta.allure.Step;
import md.homeworks.lesson6_7.Dictionary;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DictionaryPage extends BasePage {

    public DictionaryPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Проверить, что у созданного словаря название '{dictionary.nameDictionary}' и направление перевода '{dictionary.translationDirection}'")
    public DictionaryPage checkCreatedDictionary(Dictionary dictionary) {

        SoftAssertions softAssertionsAfter = new SoftAssertions();
        softAssertionsAfter.assertThat(webDriver.findElement(By.xpath("//span[contains(@class,'header')]")).getText())
                .isEqualTo(dictionary.getNameDictionary());
        softAssertionsAfter.assertThat(webDriver.findElement(By.xpath("//a[@class='ui blue basic label']")).getText())
                .isEqualTo(dictionary.getTranslationDirection());
        softAssertionsAfter.assertAll();
    return this;

    }

    @Step("Перейти в раздел 'Персональные словари'")
    public DictionariesPage goToDictionariesPageFromDictionaryPage() {

        webDriver.findElement(By.xpath("//i[@class='chevron left icon']")).click();
        return new DictionariesPage(webDriver);
    }
}