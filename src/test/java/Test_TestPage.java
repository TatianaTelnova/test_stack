import org.example.TestPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Test_TestPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final By BUTTON_CARDS = By.xpath("//*[@id='header-menu-submenu_height']//*[@id='popover-trigger-4']");
    // кнопка перехода на сраницу с банкоматами
    public static final By BUTTON_MAP = By.xpath("//*[@id='header-menu-submenu_height']//*[@class='chakra-link css-1juz3op']");
    public static final By BUTTON_OFFICES = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='chakra-button css-1qyytnp']");
    public static final By BUTTON_LIST = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='chakra-button css-1frjy8u']");
    public static final By BUTTON_ATM_LIST = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='css-uxn78q']");
    public static final By BUTTON_FAQ = By.xpath("//*[@id='header-menu-submenu_height']//*[@class='css-8cu9ab']");
    public static final By CONTAINER_BLOCKS_ITEMS = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-1y2j3ap']//*[@class='css-6amlrk']//*[@class='chakra-accordion__item css-1sfe36n']");
    public static final By CONTAINER_QUESTIONS_ITEMS = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-1y2j3ap']//*[@class='css-1id61i2']//*[@class='chakra-accordion__item css-1cw6qg0']");

    public static final By BUTTON_BLOCK = By.xpath("(//*[@id='app-wrapper']//*[@class='chakra-container css-1y2j3ap']//*[@class='css-6amlrk']//*[@class='chakra-accordion__item css-1sfe36n'])[1]//*[@id='accordion-button-17']");

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

    @Test
    public void fifthTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        WebElement we = driver.findElement(BUTTON_CARDS);
        Actions action = new Actions(driver);
        action.moveToElement(we).moveToElement(driver.findElement(BUTTON_FAQ));
        System.out.println(tp.countElems(BUTTON_FAQ));
    }

    // проверка количества блоков тем по faq
    @Test
    public void sixthTest() {
        driver.get("https://www.bspb.ru/retail/faq");
        TestPage tp = new TestPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(CONTAINER_BLOCKS_ITEMS));
        Assert.assertEquals(17, tp.countElems(CONTAINER_BLOCKS_ITEMS));
    }

    // проверка количества faq
    @Test
    public void seventhTest() {
        driver.get("https://www.bspb.ru/retail/faq");
        TestPage tp = new TestPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(CONTAINER_QUESTIONS_ITEMS));
        Assert.assertEquals(100, tp.countElems(CONTAINER_QUESTIONS_ITEMS));
    }

    @Test
    public void eighthTest() {
        driver.get("https://www.bspb.ru/retail/faq");
        TestPage tp = new TestPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(CONTAINER_QUESTIONS_ITEMS));
        int count_quest = tp.countElems(CONTAINER_QUESTIONS_ITEMS);
        tp.clickElem(BUTTON_BLOCK);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                if (tp.countElems(CONTAINER_QUESTIONS_ITEMS) == count_quest)
                    return false;
                else
                    return true;
            }
        });
        Assert.assertEquals(2, tp.countElems(CONTAINER_QUESTIONS_ITEMS));
    }
}
