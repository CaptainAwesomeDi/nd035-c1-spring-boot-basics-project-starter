package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id="inputUsername")
    private WebElement userNameInput;

    @FindBy(id="inputPassword")
    private  WebElement passwordInput;

    @FindBy(tagName = "button")
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void submitForm(String username, String password){
        userNameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }
}
