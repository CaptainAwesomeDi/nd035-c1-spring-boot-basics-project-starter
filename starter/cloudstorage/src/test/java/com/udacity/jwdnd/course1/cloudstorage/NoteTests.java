package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesHomePage;
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
public class NoteTests {
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
        // Create
        driver.get("http://localhost:" + this.port + "/home");
        NotesHomePage notesHomePage = new NotesHomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

        notesHomePage.clickOnNotesTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button")));

        notesHomePage.clickOnAddNewNote();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-save-button")));

        notesHomePage.createOrEditNote("New Note", "This is a note");
        Assertions.assertEquals("http://localhost:" + this.port  + "/note", driver.getCurrentUrl());


        driver.get("http://localhost:" + this.port  + "/home");
        notesHomePage = new NotesHomePage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

        notesHomePage.clickOnNotesTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));

        String firstNoteTitle = notesHomePage.getNoteTitle();
        Assertions.assertEquals("New Note", firstNoteTitle);

        //edit
        notesHomePage.clickOnEditNote();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-save-button")));

        notesHomePage.createOrEditNote("Changed", "This is a changed note");


        driver.get("http://localhost:" + this.port  + "/home");
        notesHomePage = new NotesHomePage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

        notesHomePage.clickOnNotesTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));

        String changedNoteTitle = notesHomePage.getNoteTitle();
        Assertions.assertEquals("Changed", changedNoteTitle);

        //delete
        notesHomePage.clickOnDeleteNote();
        driver.get("http://localhost:" + this.port  + "/home");
        notesHomePage = new NotesHomePage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

        notesHomePage.clickOnNotesTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));

        Assertions.assertFalse(driver.getPageSource().contains("Changed"));
    }

}
