package md.homeworks.lesson6.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver webDriver;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}