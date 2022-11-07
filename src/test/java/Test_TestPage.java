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

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Test_TestPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final By BUTTON_LOGIN = By.xpath("//*[@id='app-wrapper']//*[@class='css-fjazzb']//*[@class='chakra-link css-sy2ljg']");
    public static final By BUTTON_CARDS = By.xpath("//*[@id='header-menu-submenu_height']//*[@id='popover-trigger-4']");
    public static final By BUTTON_MAP = By.xpath("//*[@id='header-menu-submenu_height']//*[@class='chakra-link css-1juz3op']");
    public static final By BUTTON_LIST = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='chakra-button css-1frjy8u']");
    public static final By BUTTON_ATM_LIST = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-jf7n8r']//*[@class='css-uxn78q']");
    public static final By BUTTON_FAQ = By.xpath("(//*[@id='header-menu-submenu_height']//*[@class='css-8cu9ab']//*[@class='chakra-link css-16fpbj'])[9]");
    public static final By BUTTON_BLOCK = By.xpath("(//*[@id='app-wrapper']//*[@class='chakra-container css-1y2j3ap']//*[@class='css-6amlrk']//*[@class='chakra-accordion__item css-1sfe36n'])[1]//*[@id='accordion-button-17']");
    public static final By BUTTON_CONTACT = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-button css-ndfch2']");
    public static final By CONTAINER_CONTENTS = By.xpath("//*[@id='app-wrapper']/main/div/div");
    public static final By CONTAINER_BLOCKS_ITEMS = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-1y2j3ap']//*[@class='css-6amlrk']//*[@class='chakra-accordion__item css-1sfe36n']");
    public static final By CONTAINER_QUESTIONS_ITEMS = By.xpath("//*[@id='app-wrapper']//*[@class='chakra-container css-1y2j3ap']//*[@class='css-1id61i2']//*[@class='chakra-accordion__item css-1cw6qg0']");
    public static final By CONTAINER_LOGIN = By.xpath("(//*[@class='container']//*[@class='secondary-links']//*[@class='chevron inline-block'])[2]");
    public static final By DEMO_LOGIN = By.xpath("//*[@class='container']//form[@id='login-form']//*[@id='login-button']");
    public static final By DEMO_OTP_LOGIN = By.xpath("//*[@class='container']//form[@id='login-form']//*[@id='login-otp-button']");
    public static final By USER_NAME = By.xpath("//*[@id='inner-wrapper']//*[@class='nav-user']//*[@id='representee-name']");

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

    // проверка количетсва элементов на главной странице
    @Test
    public void contentVisibilityTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        Assert.assertEquals(6, tp.countElems(CONTAINER_CONTENTS));
    }

    // проверка загрузки на странице 4 адресов банкоматов
    @Test
    public void countAtmTest() {
        driver.get("https://www.bspb.ru/map?is=bankomats");
        TestPage tp = new TestPage(driver);
        tp.clickElem(BUTTON_LIST);
        wait.until(visibilityOfElementLocated(BUTTON_ATM_LIST));
        Assert.assertEquals(4, tp.countElems(BUTTON_ATM_LIST));
    }

    // проверка возможности перехода на страницу с банкоматами и загрузки на странице 4 адресов банкоматов
    @Test
    public void countAtmMainPageTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        tp.clickElem(BUTTON_MAP);
        wait.until(visibilityOfElementLocated(BUTTON_LIST));
        tp.clickElem(BUTTON_LIST);
        wait.until(visibilityOfElementLocated(BUTTON_ATM_LIST));
        Assert.assertEquals(4, tp.countElems(BUTTON_ATM_LIST));
    }

    // проверка раскрывающегося меню, перехода на страницу faq и количества блоков тем по faq
    @Test
    public void openMenu_countBlocksTest() throws InterruptedException {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        WebElement we = driver.findElement(BUTTON_CARDS);
        Actions action = new Actions(driver);
        action.moveToElement(we).perform();
        action.moveToElement(driver.findElement(BUTTON_FAQ)).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(CONTAINER_BLOCKS_ITEMS));
        Assert.assertEquals(17, tp.countElems(CONTAINER_BLOCKS_ITEMS));
    }

    // проверка количества faq
    @Test
    public void countFaqTest() {
        driver.get("https://www.bspb.ru/retail/faq");
        TestPage tp = new TestPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(CONTAINER_QUESTIONS_ITEMS));
        Assert.assertEquals(100, tp.countElems(CONTAINER_QUESTIONS_ITEMS));
    }

    // проверка фильтрации faq по блокам тем, при выборе первой темы - 2 faq
    @Test
    public void filterCountFaqTest() {
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

    // прокрутка страницы вниз и проверка наличия кнопки "Связаться с нами"
    @Test
    public void footerContactTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(BUTTON_CARDS));
        WebElement elem = driver.findElement(BUTTON_CONTACT);
        int deltaY = elem.getRect().y;
        Actions action = new Actions(driver);
        action.scrollByAmount(0, deltaY).perform();
        Assert.assertEquals(true, tp.checkExist(BUTTON_CONTACT));
    }

    @Test
    public void demoNameTest() {
        driver.get("https://www.bspb.ru/");
        TestPage tp = new TestPage(driver);
        String originalWindow = driver.getWindowHandle();
        tp.clickElem(BUTTON_LOGIN);
        wait.until(numberOfWindowsToBe(2));
        // перешли к вкладке входа в личный кабинет
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(visibilityOfElementLocated(CONTAINER_LOGIN));
        String secondWindow = driver.getWindowHandle();
        tp.clickElem(CONTAINER_LOGIN);
        wait.until(numberOfWindowsToBe(3));
        // перешли к вкладке DEMO
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle) && !secondWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(visibilityOfElementLocated(DEMO_LOGIN));
        tp.clickElem(DEMO_LOGIN);
        // зашли в DEMO кабинет
        tp.clickElem(DEMO_OTP_LOGIN);
        // нашли и проверили имя
        Assert.assertEquals("Королёва Ольга", tp.getText(USER_NAME).trim());
    }
}
