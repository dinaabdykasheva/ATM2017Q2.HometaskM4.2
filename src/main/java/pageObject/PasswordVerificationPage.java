package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dina_Abdykasheva on 6/15/2017.
 */
public class PasswordVerificationPage extends AbstractPage{
    private static final By PASSWORD_INPUT_LOCATOR = By.name("password");
    private static final By NEXT_BUTTON_LOCATOR = By.xpath(".//*[@id='passwordNext']/div[2]");

    public PasswordVerificationPage(WebDriver driver) {
        super(driver);
    }

    public PasswordVerificationPage fillPassword(String password) {
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(password);
        return this;
    }

    public AccountPage clickNextButton() {
        WebElement element = driver.findElement(NEXT_BUTTON_LOCATOR);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);
        return new AccountPage(driver);
    }
}
