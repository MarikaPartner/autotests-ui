package md.homeworks.lesson6_7.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPage extends BasePage {

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Авторизоваться: логин - {login}, пароль - {password}")
    public MainPage login(String login, String password) {

        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys(login);
        webDriver.findElement(By.name("pwd")).sendKeys(password);
        webDriver.findElement(By.name("wp-submit")).click();
        return this;
    }

    @Step("Выйти из режима авторизации")
    public MainPage logout() {

        webDriver.findElement(By.xpath("//div[@class='ui dropdown avatar item']")).click();
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//a[i[@class='sign out icon']]")))).click();
        return new MainPage(webDriver);
    }

    @Step("Проверить, что в шапке сайта отображается аватар пользователя")
    public MainPage checkAuthorizationAvatar() {

        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui dropdown avatar item']")));
        return this;
    }

    @Step("Проверить, что в шапке сайта появилась кнопка 'Войти'")
    public MainPage checkLoginButtonIsVisible() {

        new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[span[text()='Войти']]")));
        return this;
    }

    @Step("Перейти в раздел 'Персональные словари'")
    public DictionariesPage goToDictionariesPageFromTheMainPage() {

        webDriver.findElement(By.xpath("//h3[text()='Персональные словари']")).click();
        return new DictionariesPage(webDriver);
    }

    @Step("Проверить, что отображается сообщение: '{errorMessage}'")
    public void checkErrorIsVisible(String comment, String errorMessage) throws InterruptedException {

        assertThat(new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='login_error']")))
                .getText()).as(comment).isEqualTo(errorMessage);
    }
}