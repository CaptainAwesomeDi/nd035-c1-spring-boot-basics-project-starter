package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    String postFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication auth) throws IOException {
        User user  = this.userService.getUser(auth.getName());
        String filesize = Long.toString(file.getSize());
        if (!file.isEmpty()) {
            File currentFile = new File(null, file.getOriginalFilename(), file.getContentType(), filesize, user.getUserid(), file.getBytes());
            int result = this.fileService.createFile(currentFile);
            model.addAttribute("saved", true);
        } else {
            model.addAttribute("saved", false);
        }
        return "result";
    }

    @GetMapping("deleteFile/{id}")
    String getDeleteFile(@PathVariable("id") Integer fileId, Model model) {
        this.fileService.deleteFile(fileId);
        model.addAttribute("saved", true);
        return "result";
    }

    @GetMapping("downloadFile/{id}")
    ResponseEntity<byte[]> download(@PathVariable("id") Integer fileId) {
        File file = this.fileService.findFile(fileId);
        HttpHeaders responseheader = new HttpHeaders();
        responseheader.set("charset","utf-8");
        responseheader.setContentType(MediaType.valueOf(file.getContenttype()));
        byte[] output = file.getFiledata();
        responseheader.setContentLength(output.length);
        ContentDisposition disposition = ContentDisposition
                .builder("attachment")
                .filename(file.getFilename())
                .build();
        responseheader.setContentDisposition(disposition);

        return new ResponseEntity<byte[]>(output,responseheader, HttpStatus.OK);

    }


    @ExceptionHandler
    String handleException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
