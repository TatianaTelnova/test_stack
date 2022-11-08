package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestPage {
    protected WebDriver driver;
    public static final By BUTTON_LOGIN = By.xpath("//*[contains(@class,'css-sy2ljg')]");
    public static final By BUTTON_CARDS = By.xpath("//*[@id='popover-trigger-4']");
    public static final By BUTTON_MAP = By.xpath("//*[contains(@class,'css-1juz3op')]");
    public static final By BUTTON_LIST = By.xpath("//*[contains(@class,'css-1frjy8u')]");
    public static final By BUTTON_ATM_LIST = By.xpath("//*[@class='css-uxn78q']");
    public static final By BUTTON_FAQ = By.xpath("(//*[contains(@class,'css-16fpbj')])[9]");
    public static final By BUTTON_BLOCK = By.xpath("//*[@id='accordion-button-17']");
    public static final By BUTTON_CONTACT = By.xpath("//*[contains(@class,'css-ndfch2')]");
    public static final By CONTAINER_CONTENTS = By.xpath("//*[@id='app-wrapper']/main/div/div");
    public static final By CONTAINER_BLOCKS_ITEMS = By.xpath("//*[contains(@class,'css-1sfe36n')]");
    public static final By CONTAINER_QUESTIONS_ITEMS = By.xpath("//*[contains(@class,'css-1cw6qg0')]");
    public static final By CONTAINER_LOGIN = By.xpath("(//*[contains(@class,'inline-block')])[3]");
    public static final By DEMO_LOGIN = By.xpath("//*[@id='login-button']");
    public static final By DEMO_OTP_LOGIN = By.xpath("//*[@id='login-otp-button']");
    public static final By USER_NAME = By.xpath("//*[@id='representee-name']");


    public TestPage(WebDriver driver) {
        this.driver = driver;
    }

    public int countElems(By elem) {
        return driver.findElements(elem).size();
    }

    public void clickElem(By elem) {
        driver.findElement(elem).click();
    }

    public boolean checkExist(By elem) {
        try {
            driver.findElement(elem);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getText(By elem) {
        return driver.findElement(elem).getAttribute("innerText");
    }

    public WebElement getElem(By elem) {
        return driver.findElement(elem);
    }
}
