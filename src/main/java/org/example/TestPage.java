package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
