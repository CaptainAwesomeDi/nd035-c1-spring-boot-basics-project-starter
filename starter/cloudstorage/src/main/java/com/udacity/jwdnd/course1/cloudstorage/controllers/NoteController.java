package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    String postNote(@ModelAttribute Note note, Model model, Authentication auth){

        note.setUserid(this.userService.getUser(auth.getName()).getUserid());

        int result;
        if (note.getNoteid() != null){ result = this.noteService.update(note); }
        else { result = this.noteService.createNote(note); }

        if (result > 0) { model.addAttribute("saved", true); }
        else { model.addAttribute("saved", false); }

        return "result";
    }


    @GetMapping("/deleteNote/{id}")
    String deleteNote(@PathVariable("id") Integer noteId, Model model){
        this.noteService.deleteNote(noteId);
        model.addAttribute("saved", true);
        return "result";
    }

}
