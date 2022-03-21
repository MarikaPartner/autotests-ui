package md.homeworks.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class LoginAndLogout {
    public static void main( String[] args ) throws InterruptedException {

        WebDriver webDriver = WebDriverManager.chromedriver().create();
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

//      Выход из режима авторизации
        webDriver.findElement(By.xpath("//div[@class='ui dropdown avatar item']")).click();
        //new WebDriverWait(webDriver, 5).
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//a[i[@class='sign out icon']]")))).click();
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[span[text()='Войти']]")));

        //Thread.sleep(5000);
        webDriver.quit();
    }
}
