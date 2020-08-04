package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {
    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    String getHome(Authentication auth, Model model, @ModelAttribute("newNote")Note note, @ModelAttribute("newCred")Credential cred) {
        User user  = this.userService.getUser(auth.getName());
        List<File>  files = this.fileService.allUserFiles(user);
        List<Note> notes = this.noteService.allUserNotes(user);
        List<Credential> creds = this.credentialService.getUserCredentials(user);
        HashMap<Integer,String> hashMap = new HashMap<>();
        for (Credential credit: creds) {
            hashMap.put(credit.getCredentialid(),this.credentialService.showOne(credit.getCredentialid()));
        }
        model.addAttribute("filelist", files);
        model.addAttribute("notelist", notes);
        model.addAttribute("credlist", creds);
        model.addAttribute("unencrypted", hashMap);
        return "home";
    }


}
