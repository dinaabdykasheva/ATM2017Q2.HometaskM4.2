package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dina_Abdykasheva on 6/15/2017.
 */
public class UsernameVerificationPage extends AbstractPage{
    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 10;
    private static final By USERNAME_INPUT_LOCATOR = By.name("identifier");
    private static final By NEXT_BUTTON_LOCATOR = By.id("identifierNext");

    public UsernameVerificationPage(WebDriver driver) {
        super(driver);
    }

    public UsernameVerificationPage googleMailOpen() {
        driver.get("https://www.google.com/gmail");
        return this;
    }

    public UsernameVerificationPage fillUsername(String username) {
        driver.findElement(USERNAME_INPUT_LOCATOR).sendKeys(username);
        return this;
    }

    public PasswordVerificationPage clickNextButton() {
        driver.findElement(NEXT_BUTTON_LOCATOR).click();
        return new PasswordVerificationPage(driver);
    }

    protected void waitForElementEnabled(By locator) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON_LOCATOR));
    }
}
