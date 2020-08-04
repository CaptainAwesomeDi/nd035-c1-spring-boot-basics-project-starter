package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialsHomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private SignUpPage signUpPage;
    private LoginPage loginPage;



    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/signup");
        signUpPage = new SignUpPage(driver);
        signUpPage.submitForm("John","Doe","johndoe","password");


        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.submitForm("johndoe","password");
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void CRUDtest(){
        //create
        driver.get("http://localhost:" + this.port  + "/home");
        CredentialsHomePage credentialsHomePage = new CredentialsHomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

        credentialsHomePage.clickOnCredentialsTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-cred-button")));

        credentialsHomePage.clickOnAddNewCredential();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-cred-button")));

        credentialsHomePage.createOrEditCredential("test.com", "test", "test");
        Assertions.assertEquals("http://localhost:" + this.port  + "/cred", driver.getCurrentUrl());

        driver.get("http://localhost:" + this.port  + "/home");
        credentialsHomePage = new CredentialsHomePage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

        credentialsHomePage.clickOnCredentialsTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

        String firstCredUrl = credentialsHomePage.getCredentialUrl();
        String firstCredPassword = credentialsHomePage.getCredentialPassword();
        Assertions.assertEquals("test.com", firstCredUrl);
        Assertions.assertNotEquals("test", firstCredPassword);

        //edit
        credentialsHomePage.clickOnEditCredential();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));

        Assertions.assertEquals("test", credentialsHomePage.getEditCredentialModalPassword());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-cred-button")));

        credentialsHomePage.createOrEditCredential("example.com", "username", "password");

        driver.get("http://localhost:" + this.port  + "/home");
        credentialsHomePage = new CredentialsHomePage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

        credentialsHomePage.clickOnCredentialsTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

        String changedCredUrl = credentialsHomePage.getCredentialUrl();
        String changedCredPassword = credentialsHomePage.getCredentialPassword();
        Assertions.assertEquals("example.com", changedCredUrl);
        Assertions.assertNotEquals("password", changedCredPassword);

        //delete
        credentialsHomePage.clickOnDeleteCredential();
        driver.get("http://localhost:" + this.port  + "/home");
        credentialsHomePage = new CredentialsHomePage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

        credentialsHomePage.clickOnCredentialsTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

        Assertions.assertFalse(driver.getPageSource().contains("example.com"));
    }

}
