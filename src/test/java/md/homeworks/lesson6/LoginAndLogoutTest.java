package md.homeworks.lesson6;

import io.github.bonigarcia.wdm.WebDriverManager;
import md.homeworks.lesson6.pages.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import java.util.concurrent.TimeUnit;

public class LoginAndLogoutTest extends BaseTest {
    
    @BeforeEach
    void setUP() {
        webDriver = WebDriverManager.chromedriver().create();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
    }

    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Авторизация существующего пользователя с валидным паролем и выход из режима авторизации")
    void loginAndLogoutTest() {
        new MainPage(webDriver)
                .login(LOGIN, PASSWORD)
                .checkAuthorizationAvatar()
                .logout()
                .checkLoginButtonIsVisible();
    }

    @Test
    @DisplayName("Авторизация существующего пользователя с невалидным паролем")
    void incorrectPasswordTest() throws InterruptedException {
        new MainPage(webDriver)
                .login(LOGIN, "incorrect_password")
                .checkErrorIsVisible("Был указан неверный пароль","ERROR: The username or password you entered is incorrect. Lost your password?");
    }

    @Test
    @DisplayName("Авторизация существующего пользователя с незаполненным полем ввода пароля")
    void notEnteredPasswordTest() throws InterruptedException {
        new MainPage(webDriver)
                .login(LOGIN, "")
                .checkErrorIsVisible("Пароль не был введен","ОШИБКА: Вы не ввели пароль.");
    }
}