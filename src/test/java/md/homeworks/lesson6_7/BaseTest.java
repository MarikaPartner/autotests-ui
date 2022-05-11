package md.homeworks.lesson6_7;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

public class BaseTest {
    protected static EventFiringWebDriver webDriver;
    public static final String LOGIN = "mariapuzakova";
    public static final String PASSWORD = "K0G8MhIrUzHq";

    @AfterEach
    void recordLogs() {
        step("Логи браузера", () -> {
            webDriver.manage().logs().get(LogType.BROWSER)
                    .forEach(log -> addAttachment("logs", log.getMessage()));
        });
    }
}


