package md.homeworks.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;

public class DeletingPersonalDictionary {
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

//      Создание тестового словаря
        webDriver.findElement(By.xpath("//button[contains(text(),'Новый словарь')]")).click();
        WebElement modalWinNewDictionary = webDriver.findElement(By.xpath("//div[@class='ui tiny modal transition visible active']"));  // Модальное окно
        WebElement inputDictionaryName = modalWinNewDictionary.findElement(By.xpath("//div[@class='ui input']/input"));  // Поле ввода названия словаря
        inputDictionaryName.click();
        inputDictionaryName.clear();
        inputDictionaryName.sendKeys("Тестовый словарь");
        modalWinNewDictionary.findElement(By.xpath("//button[text()='Создать']")).click();
        webDriver.get("https://myefe.ru/pd");

//      Удаление выбранного словаря
        WebElement divTestDictionary = webDriver.findElement(By.xpath("//div[@class='ui stackable grid'][contains(.,'Тестовый словарь')]"));   // Находим блок тестового словаря
        divTestDictionary.findElement(By.xpath("//button[@class='ui red tiny basic icon button']")).click();
        WebElement modalWinDeleting = webDriver.findElement(By.xpath("//div[contains(@class,'tiny basic')]"));
        modalWinDeleting.findElement(By.xpath("//button[text()='Удалить']")).click();

        //Thread.sleep(5000);
        webDriver.quit();
    }
}