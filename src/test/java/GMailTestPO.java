import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObject.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dina_Abdykasheva on 6/15/2017.
 */
public class GMailTestPO {
    private WebDriver driver;

    @BeforeClass(description = "StartBrowser")
    private void startBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(description = "GMailTestPO")
    public void loginToAccountTest() {
        AccountPage accountPage = new UsernameVerificationPage(driver).googleMailOpen().fillUsername("test.da.10062017").clickNextButton().fillPassword("testtest01").clickNextButton();
        boolean isAccountIconPresent = new AccountPage(driver).isElementPresent();
        Assert.assertTrue(isAccountIconPresent, "User isn't logged in");
    }

    @Test(description = "SaveToDraftTest", dependsOnMethods = "loginToAccountTest")
    public void saveToDraftTest() {
        WriteMailPage writeMail = new AccountPage(driver).clickWriteMailButton().fillToField("dina_abdykasheva@mail.ru").fillSubjectField("mentoring task").fillBodyField("text").clickCloseButton();
        DraftsFolderPage draftsFolder = new AccountPage(driver).openDrafts();
        boolean isDraftMailSaved = new DraftsFolderPage(driver).isDraftMailSaved();
        Assert.assertTrue(isDraftMailSaved, "Mail isn't saved in drafts");
    }

    @Test(description = "VerifySavedDraftTest", dependsOnMethods = "saveToDraftTest")
    public void verifySavedDraftTest() {
        WriteMailPage openSavedDraft = new DraftsFolderPage(driver).openDraftMail();
        String receiver = new WriteMailPage(driver).getReceiver();
        Assert.assertEquals("dina_abdykasheva@mail.ru", receiver, "Receiver isn't valid");
        String subject = new WriteMailPage(driver).getSubject();
        Assert.assertEquals("mentoring task", subject, "Subject isn't valid");
        String body = new WriteMailPage(driver).getBody();
        Assert.assertEquals("text", body, "Body isn't valid");
    }

    @Test(description = "SendMailTest", dependsOnMethods = "verifySavedDraftTest")
    public void sendMailTest() {
        AccountPage sendMail = new WriteMailPage(driver).sendMail();
    }

    @Test(description = "MailIsDeletedFromDraftsTest", dependsOnMethods = "sendMailTest")
    public void mailIsDeletedFromDraftsTest() {
        DraftsFolderPage openDraftFolder = new AccountPage(driver).openDrafts();
        boolean isMailDeletedFromDrafts = new DraftsFolderPage(driver).isDraftMailDeleted();
        Assert.assertTrue(isMailDeletedFromDrafts, "Mail isn't deleted from drafts");
    }

    @Test(description = "VerifySentFolderTest", dependsOnMethods = "sendMailTest")
    public void verifySentFolderTest() {
        SentFolderPage openSentFolder = new AccountPage(driver).openSentMail();
        boolean isMailSent = new SentFolderPage(driver).isMailSent();
        Assert.assertTrue(isMailSent, "Mail isn't sent");
    }

    @Test(description = "ExitGMailTest", dependsOnMethods = "verifySentFolderTest")
    public void exitGMailTest() {
        AccountPage exitGMail = new AccountPage(driver).exitGMail();
    }

    @AfterClass(description = "closeDriver")
    public void closeDriver() {
        driver.close();
    }
}
