package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dina_Abdykasheva on 6/15/2017.
 */
public class WriteMailPage extends AbstractPage{
    private static final By TO_FIELD_LOCATOR = By.xpath(".//div[@class = 'fX aXjCH']");
    private static final By SUBJECT_FIELD_LOCATOR = By.name("subjectbox");
    private static final By BODY_FIELD_LOCATOR = By.xpath(".//div[@role='textbox']");
    private static final By CLOSE_WRITE_MAIL_WINDOW_LOCATOR = By.xpath(".//img[@class='Ha']");
    private static final By SEND_MAIL_BUTTON_LOCATOR = By.xpath(".//div[contains(text(), '���������')]");
    private static final By DRAFTS_FOLDER_LOCATOR = By.xpath(".//a[contains(text(), 'Черновики')]");
    private static final By SAVING_LABEL_LOCATOR = By.xpath(".//span[@class = 'oG aOy']");
    private static final By TO_FIELD_IN_DRAFT_LOCATOR = By.xpath(".//span[@class='vN bfK a3q']");
    private static final By MAIL_IS_SENT_LOCATOR = By.xpath(".//div[contains(text(), '������ ����������')]");

    public WriteMailPage(WebDriver driver) {
        super(driver);
    }

    public DraftsFolderPage writeMailAndSaveToDraft(String to, String subject, String body) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(TO_FIELD_LOCATOR));
        actions.click();
        actions.sendKeys(to);
        actions.build().perform();
        driver.findElement(SUBJECT_FIELD_LOCATOR).sendKeys(subject);
        driver.findElement(BODY_FIELD_LOCATOR).sendKeys(body);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SAVING_LABEL_LOCATOR));
        driver.findElement(CLOSE_WRITE_MAIL_WINDOW_LOCATOR).click();
        driver.findElement(DRAFTS_FOLDER_LOCATOR).click();
        return new DraftsFolderPage(driver);
    }

    public String getReceiver() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(TO_FIELD_IN_DRAFT_LOCATOR));
        return driver.findElement(TO_FIELD_IN_DRAFT_LOCATOR).getAttribute("email");
    }

    public String getSubject() {
        return driver.findElement(SUBJECT_FIELD_LOCATOR).getAttribute("value");
    }

    public String getBody() {
        return driver.findElement(BODY_FIELD_LOCATOR).getText();
    }

    public AccountPage sendMail() {
        driver.findElement(SEND_MAIL_BUTTON_LOCATOR).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(MAIL_IS_SENT_LOCATOR));
        return new AccountPage(driver);
    }

}
