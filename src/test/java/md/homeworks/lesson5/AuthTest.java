package md.homeworks.lesson5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class AuthTest extends BaseTest {

    @Test
    @Disabled("Отложен до следующего курса")
    @DisplayName("Авторизация через UI существующего пользователя с валидными паролем")
    void authWithUiTest() {

        webDriver.get("https://myefe.ru/wp-login.php?redirect_to=https%3A%2F%2Fmyefe.ru");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui dropdown avatar item']")));
    }

    @Test
    @DisplayName("Авторизация через cookie существующего пользователя с валидными паролем")
    void authWithCookieTest() {
        webDriver.get("https://myefe.ru/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        webDriver.manage().addCookie(new Cookie("wordpress_logged_in_804dc4e975fa5b0a7ea6a4d127420162","mariapuzakova%7C1648729641%7CjJ1qMzEa0TqU4wUXC8TV0AyKRaMpSeeITHEXN3NNjCI%7Ce5135428cad7aef562053bd76475e4204b172addef26cc93a02f7427cc661173"));
        webDriver.navigate().refresh();
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui dropdown avatar item']")));
    }
}