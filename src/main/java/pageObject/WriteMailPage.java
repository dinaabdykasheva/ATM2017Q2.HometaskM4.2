package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Dina_Abdykasheva on 6/15/2017.
 */
public class WriteMailPage extends AbstractPage{
    private static final By TO_FIELD_LOCATOR = By.xpath(".//div[@class = 'fX aXjCH']");
    private static final By SUBJECT_FIELD_LOCATOR = By.name("subjectbox");
    private static final By BODY_FIELD_LOCATOR = By.xpath(".//div[@role='textbox']");
    private static final By CLOSE_WRITE_MAIL_WINDOW_LOCATOR = By.xpath(".//img[@class='Ha']");
    private static final By SEND_MAIL_BUTTON_LOCATOR = By.xpath(".//div[@class='T-I J-J5-Ji aoO T-I-atl L3']");

    public WriteMailPage(WebDriver driver) {
        super(driver);
    }

    public WriteMailPage fillToField(String to) {
        WebElement element = driver.findElement(TO_FIELD_LOCATOR);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(to);
        actions.build().perform();
        return this;
    }

    public WriteMailPage fillSubjectField(String subject) {
        driver.findElement(SUBJECT_FIELD_LOCATOR).sendKeys(subject);
        return this;
    }

    public WriteMailPage fillBodyField(String body) {
        driver.findElement(BODY_FIELD_LOCATOR).sendKeys(body);
        return this;
    }

    public WriteMailPage clickCloseButton() {
        driver.findElement(CLOSE_WRITE_MAIL_WINDOW_LOCATOR).click();
        return this;
    }

    public String getReceiver() {
        return driver.findElement(TO_FIELD_LOCATOR).getAttribute("email");
    }

    public String getSubject() {
        return driver.findElement(SUBJECT_FIELD_LOCATOR).getAttribute("value");
    }

    public String getBody() {
        return driver.findElement(BODY_FIELD_LOCATOR).getText();
    }

    public AccountPage sendMail() {
        driver.findElement(SEND_MAIL_BUTTON_LOCATOR).click();
        return new AccountPage(driver);
    }

}
