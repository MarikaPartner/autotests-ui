package md.homeworks.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class CreatingPersonalDictionary {
    public static void main(String[] args) throws InterruptedException {

        WebDriver webDriver = WebDriverManager.chromedriver().create();
        webDriver.get("https://myefe.ru/");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация
        webDriver.findElement(By.xpath("//a[span[text()='Войти']]")).click();
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

//      Переход в раздел "Все словари"
        WebElement mainMenu = webDriver.findElement(By.id("msui-sidebar"));    // Открываем главное меню
        mainMenu.findElement(By.xpath("//a[contains(text(),'Персональные словари')]")).click();    // Переходим по ссылке "персональные словари"

//      Создание нового словаря
        webDriver.findElement(By.xpath("//button[contains(text(),'Новый словарь')]")).click();
        WebElement modalWinNewDictionary = webDriver.findElement(By.xpath("//div[@class='ui tiny modal transition visible active']"));
        modalWinNewDictionary.findElement(By.xpath("//div[@class='header'][text()='Новый словарь']"));  // Проверяем заголовок "Новый словарь"
        modalWinNewDictionary.findElement(By.xpath("//input[contains(@value,'Словарь №')]"));  //   Проверяем подстановку названия по умолчанию
        modalWinNewDictionary.findElement(By.xpath("//button[text()='Создать']")).click();
        webDriver.findElement(By.xpath("//span[@class='ui medium header'][contains(text(),'Словарь №')]"));

        //Thread.sleep(4000);
        webDriver.quit();
    }
}