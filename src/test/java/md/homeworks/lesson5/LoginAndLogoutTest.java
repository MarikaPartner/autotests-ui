package md.homeworks.lesson5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.TimeUnit;

public class LoginAndLogoutTest extends BaseTest {

    @Test
    @DisplayName("Авторизация существующего пользователя с валидным паролем и выход из режима авторизации")
    void loginAndLogoutTest() {

        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui dropdown avatar item']")));

//      Выход из режима авторизации
        webDriver.findElement(By.xpath("//div[@class='ui dropdown avatar item']")).click();
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//a[i[@class='sign out icon']]")))).click();
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[span[text()='Войти']]")));
    }

    @Test
    @DisplayName("Авторизация существующего пользователя с невалидным паролем")
    void incorrectPasswordTest() {

        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация c неверным паролем
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("incorrect_password");
        webDriver.findElement(By.name("wp-submit")).click();
        assertThat(new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='login_error']")))
                .getText()).as("Был указан неверный пароль").isEqualTo("ERROR: The username or password you entered is incorrect. Lost your password?");
    }

    @Test
    @DisplayName("Авторизация существующего пользователя с незаполненным полем ввода пароля")
    void notEnteredPasswordTest() {

        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация без ввода пароля
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("");
        webDriver.findElement(By.name("wp-submit")).click();
        assertThat(new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='login_error']")))
                .getText()).as("Пароль не был введен").isEqualTo("ОШИБКА: Вы не ввели пароль.");
    }
}