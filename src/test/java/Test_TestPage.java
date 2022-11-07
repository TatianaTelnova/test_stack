

import org.example.TestPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Test_TestPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final By BUTTON = By.xpath("//*[@id='popover-trigger-4']");
    // кнопка перехода на сраницу с банкоматами
    public static final By BUTTON_MAP = By.xpath("//*[@id='header-menu-submenu_height']//*[@class='chakra-link css-1juz3op']");
    public static final By BUTTON_OFFICES = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='chakra-button css-1qyytnp']");

    public static final By BUTTON_LIST = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='chakra-button css-1frjy8u']");

    public static final By BUTTON_ATM_LIST = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='css-uxn78q']");


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void firstTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        tp.clickElem(BUTTON_MAP);
    }

    @Test
    public void secondTest() {
        driver.get("https://www.bspb.ru/map?is=bankomats");
        TestPage tp = new TestPage(driver);
        tp.clickElem(BUTTON_LIST);
    }

    // проверка загрузки на странице 4 адресов банкоматов
    @Test
    public void thirdTest() {
        driver.get("https://www.bspb.ru/map?is=bankomats");
        TestPage tp = new TestPage(driver);
        tp.clickElem(BUTTON_LIST);
        wait.until(visibilityOfElementLocated(BUTTON_ATM_LIST));
        Assert.assertEquals(4, tp.countElems(BUTTON_ATM_LIST));
    }

    // проверка возможности перехода на страницу с банкоматами и загрузки на странице 4 адресов банкоматов
    @Test
    public void forthTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        tp.clickElem(BUTTON_MAP);
        wait.until(visibilityOfElementLocated(BUTTON_LIST));
        tp.clickElem(BUTTON_LIST);
        wait.until(visibilityOfElementLocated(BUTTON_ATM_LIST));
        Assert.assertEquals(4, tp.countElems(BUTTON_ATM_LIST));
    }
}
