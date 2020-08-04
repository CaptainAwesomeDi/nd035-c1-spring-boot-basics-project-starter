package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/cred")
    String create(Model model, @ModelAttribute("newCred")Credential credential, Authentication auth) {
        credential.setUserid(this.userService.getUser(auth.getName()).getUserid());
        int result = this.credentialService.createOrUpdate(credential);
        if (result > 0) model.addAttribute("saved", true);
        else model.addAttribute("saved", false);
        return "result";
    }

    @GetMapping("/deletecred/{id}")
    String delete(Model model, @PathVariable("id") Integer credentialid) {
        this.credentialService.deleteCredential(credentialid);
        model.addAttribute("saved", true);
        return "result";
    }
}
