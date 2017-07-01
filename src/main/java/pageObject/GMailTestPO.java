package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

    @Test(description = "loginToAccountTest", priority = 0)
    public void loginToAccountTest() {
        AccountPage accountPage = new LoginToGMailPage(driver).loginToGMail("test.da.10062017" ,"testtest01");
        boolean isAccountIconPresent = accountPage.isAccountIconPresent();
        Assert.assertTrue(isAccountIconPresent, "User isn't logged in");
    }

   @Test(description = "SaveToDraftTest", dependsOnMethods = "loginToAccountTest")
    public void saveToDraftTest() {
        DraftsFolderPage writeMail = new AccountPage(driver).clickWriteMailButton().writeMailAndSaveToDraft("dina_abdykasheva@mail.ru", "mentoring task", "body text");
        boolean isDraftMailSaved = new DraftsFolderPage(driver).isDraftMailDisplayed();
        Assert.assertTrue(isDraftMailSaved, "Mail isn't saved in drafts");
    }

    @Test(description = "VerifySavedDraftReceiverTest", dependsOnMethods = "saveToDraftTest")
    public void verifySavedDraftReceiverTest() {
        WriteMailPage openSavedDraft = new DraftsFolderPage(driver).openDraftMail();
        String receiver = openSavedDraft.getReceiver();
        Assert.assertEquals("dina_abdykasheva@mail.ru", receiver, "Receiver isn't valid");
    }

    @Test(description = "VerifySavedDraftSubjectTest", dependsOnMethods = "verifySavedDraftReceiverTest")
    public void verifySavedDraftSubjectTest() {
        String subject = new WriteMailPage(driver).getSubject();
        Assert.assertEquals("mentoring task", subject, "Subject isn't valid");
    }

    @Test(description = "VerifySavedDraftBodyTest", dependsOnMethods = "verifySavedDraftSubjectTest")
    public void verifySavedDraftBodyTest() {
        String body = new WriteMailPage(driver).getBody();
        Assert.assertEquals("body text", body, "Body isn't valid");
    }

    @Test(description = "isMailSent", dependsOnMethods = {"verifySavedDraftReceiverTest", "verifySavedDraftSubjectTest", "verifySavedDraftBodyTest"})
    public void isMailSent() {
        SentFolderPage sendMail = new WriteMailPage(driver).sendMail().openSentMail();
        boolean isMailSent = sendMail.isMailSent();
        Assert.assertTrue(isMailSent, "Mail wasn't sent");
    }

    @Test(description = "MailIsDeletedFromDraftsTest", dependsOnMethods = "isMailSent")
    public void mailIsDeletedFromDraftsTest() {
        DraftsFolderPage openDraftFolder = new AccountPage(driver).openDrafts();
        boolean isMailDeletedFromDrafts = new DraftsFolderPage(driver).isDraftMailDisplayed();
        Assert.assertFalse(isMailDeletedFromDrafts, "Mail isn't deleted from drafts");
    }

   @Test(description = "ExitGMailTest", dependsOnMethods = "isMailSent")
    public void exitGMailTest() {
        AccountPage exitGMail = new AccountPage(driver).exitGMail();
        boolean isUserLoggedOff = new LoginToGMailPage(driver).isUserLoggedOff();
        Assert.assertTrue(isUserLoggedOff, "User wasn't logged off");
    }

    @AfterClass(description = "closeDriver")
    public void closeDriver() {
        driver.close();
    }
}
