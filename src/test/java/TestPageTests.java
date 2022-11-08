import org.example.TestPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class TestPageTests {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    // проверка количества элементов на главной странице
    @Test
    public void contentVisibilityTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        Assert.assertTrue(tp.countElems(TestPage.CONTAINER_CONTENTS) > 0);
    }

    // проверка загрузки на странице 4 адресов банкоматов
    @Test
    public void countAtmTest() {
        driver.get("https://www.bspb.ru/map?is=bankomats");
        TestPage tp = new TestPage(driver);
        tp.clickElem(TestPage.BUTTON_LIST);
        wait.until(visibilityOfElementLocated(TestPage.BUTTON_ATM_LIST));
        Assert.assertEquals(4, tp.countElems(TestPage.BUTTON_ATM_LIST));
    }

    // проверка возможности перехода на страницу с банкоматами и загрузки на странице 4 адресов банкоматов
    @Test
    public void countAtmMainPageTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        tp.clickElem(TestPage.BUTTON_MAP);
        wait.until(visibilityOfElementLocated(TestPage.BUTTON_LIST));
        tp.clickElem(TestPage.BUTTON_LIST);
        wait.until(visibilityOfElementLocated(TestPage.BUTTON_ATM_LIST));
        Assert.assertEquals(4, tp.countElems(TestPage.BUTTON_ATM_LIST));
    }

    // проверка раскрывающегося меню, перехода на страницу faq и количества блоков тем по faq
    @Test
    public void openMenu_countBlocksTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        WebElement we = tp.getElem(TestPage.BUTTON_CARDS);
        Actions action = new Actions(driver);
        action.moveToElement(we).perform();
        action.moveToElement(tp.getElem(TestPage.BUTTON_FAQ)).click().perform();
        wait.until(elementToBeClickable(TestPage.CONTAINER_BLOCKS_ITEMS));
        Assert.assertEquals(17, tp.countElems(TestPage.CONTAINER_BLOCKS_ITEMS));
    }

    // проверка количества faq
    @Test
    public void countFaqTest() {
        driver.get("https://www.bspb.ru/retail/faq");
        TestPage tp = new TestPage(driver);
        wait.until(elementToBeClickable(TestPage.CONTAINER_QUESTIONS_ITEMS));
        Assert.assertEquals(100, tp.countElems(TestPage.CONTAINER_QUESTIONS_ITEMS));
    }

    // проверка фильтрации faq по блокам тем, при выборе первой темы - 2 faq
    @Test
    public void filterCountFaqTest() {
        driver.get("https://www.bspb.ru/retail/faq");
        TestPage tp = new TestPage(driver);
        wait.until(elementToBeClickable(TestPage.CONTAINER_QUESTIONS_ITEMS));
        int count_quest = tp.countElems(TestPage.CONTAINER_QUESTIONS_ITEMS);
        tp.clickElem(TestPage.BUTTON_BLOCK);
        wait.until((ExpectedCondition<Boolean>) driver -> tp.countElems(TestPage.CONTAINER_QUESTIONS_ITEMS) != count_quest);
        Assert.assertTrue(tp.countElems(TestPage.CONTAINER_QUESTIONS_ITEMS) < 100);
    }

    // прокрутка страницы вниз и проверка наличия кнопки "Связаться с нами"
    @Test
    public void footerContactTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        wait.until(elementToBeClickable(TestPage.BUTTON_CARDS));
        WebElement we = tp.getElem(TestPage.BUTTON_CONTACT);
        int deltaY = we.getRect().y;
        Actions action = new Actions(driver);
        action.scrollByAmount(0, deltaY).perform();
        Assert.assertTrue(tp.checkExist(TestPage.BUTTON_CONTACT));
    }

    // проверка работы DEMO личного кабинета
    @Test
    public void demoNameTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        Set<String> windowHandles = new HashSet<>();
        windowHandles.add(driver.getWindowHandle());
        // перешли к вкладке входа в личный кабинет
        tp.clickElem(TestPage.BUTTON_LOGIN);
        wait.until(numberOfWindowsToBe(windowHandles.size() + 1));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandles.contains(windowHandle)) {
                windowHandles.add(windowHandle);
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(visibilityOfElementLocated(TestPage.CONTAINER_LOGIN));
        // перешли к вкладке DEMO
        tp.clickElem(TestPage.CONTAINER_LOGIN);
        wait.until(numberOfWindowsToBe(windowHandles.size() + 1));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandles.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(visibilityOfElementLocated(TestPage.DEMO_LOGIN));
        // зашли в DEMO кабинет
        tp.clickElem(TestPage.DEMO_LOGIN);
        wait.until(visibilityOfElementLocated(TestPage.DEMO_OTP_LOGIN));
        tp.clickElem(TestPage.DEMO_OTP_LOGIN);
        // нашли и проверили имя
        Assert.assertEquals("Королёва Ольга", tp.getText(TestPage.USER_NAME).trim());
    }
}
