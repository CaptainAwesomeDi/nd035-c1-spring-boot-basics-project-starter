package com.udacity.jwdnd.course1.cloudstorage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginRegisterTests {
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
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void unableToAccessHomePageWithoutLogin(){
        driver.get("http://localhost:" + this.port + "/home");
       Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void RegisterLoginLogoutFlow(){
        driver.get("http://localhost:" + this.port + "/signup");
        signUpPage = new SignUpPage(driver);
        signUpPage.submitForm("John","Doe","johndoe","password");


        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.submitForm("johndoe","password");

        Assertions.assertEquals("Home", driver.getTitle());

    }

}
