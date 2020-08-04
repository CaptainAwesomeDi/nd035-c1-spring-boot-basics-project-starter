package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesHomePage {
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-save-button")
    private WebElement noteSaveChangesButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr")
    private WebElement firstNote;

    public NotesHomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void clickOnNotesTab() {
        this.notesTab.click();
    }

    public void clickOnAddNewNote() {
        this.addNoteButton.click();
    }

    public void clickOnEditNote() {
        this.editNoteButton.click();
    }

    public void clickOnDeleteNote() {
        this.deleteNoteButton.click();
    }

    public void createOrEditNote(String title, String description) {
        this.noteTitle.clear();
        this.noteTitle.sendKeys(title);

        this.noteDescription.clear();
        this.noteDescription.sendKeys(description);

        this.noteSaveChangesButton.click();
    }

    public String getNoteTitle() {
        return this.firstNote.findElement(By.id("displayed-note-title")).getText();
    }


}
