import org.example.TestPage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class Test_TestPage {
    protected WebDriver driver;
    @Test
    public void firstTest() {
        new TestPage(driver);
    }
}
