package md.homeworks.lesson6_7;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import md.homeworks.lesson6_7.listener.AllureListener;
import md.homeworks.lesson6_7.pages.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.util.concurrent.TimeUnit;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

@DisplayName("Авторизация")
public class LoginAndLogoutTest extends BaseTest {

    @BeforeEach
    void setUP() {
        webDriver = new EventFiringWebDriver(WebDriverManager.chromedriver().create());
        webDriver.register(new AllureListener());
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
    }

    @AfterEach
    void tearDown() {
        step("Логи браузера", () -> {
            webDriver.manage().logs().get(LogType.BROWSER)
                    .forEach(log -> addAttachment("logs", log.getMessage()));
        });
        webDriver.quit();
    }

    @Test
    @DisplayName("Успешные авторизация и выход из режима авторизации")
    @Severity(SeverityLevel.BLOCKER)
    void loginAndLogoutTest() {
        new MainPage(webDriver)
                .login(LOGIN, PASSWORD)
                .checkAuthorizationAvatar()
                .logout()
                .checkLoginButtonIsVisible();
    }

    @Test
    @DisplayName("Авторизация: Негативный сценарий: неверный пароль")
    @Severity(SeverityLevel.NORMAL)
    @Description("Успешная авторизация под существующем пользователем и выход из системы")
    void incorrectPasswordTest() throws InterruptedException {
        new MainPage(webDriver)
                .login(LOGIN, "incorrect_password")
                .checkErrorIsVisible("Был указан неверный пароль","ERROR: The username or password you entered is incorrect. Lost your password??");
    }

    @Test
    @DisplayName("Авторизация: Негативный сценарий: не введен пароль")
    @Severity(SeverityLevel.NORMAL)
    @Description("Авторизация под существующем пользователем с незаполненным полем ввода пароля")
    void notEnteredPasswordTest() throws InterruptedException {
        new MainPage(webDriver)
                .login(LOGIN, "")
                .checkErrorIsVisible("Пароль не был введен","ОШИБКА: Вы не ввели пароль.");
    }
}