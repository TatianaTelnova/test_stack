package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestPage {
    protected WebDriver driver;

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
