package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> allUserFiles(User user) {
        return this.fileMapper.findfiles(user.getUserid());
    }

    public File findFile(Integer fileId){
        return this.fileMapper.findfile(fileId);
    }

    public int createFile(File file){
        return this.fileMapper.insert(file);
    }

    public void deleteFile(Integer fileId) {
        this.fileMapper.deletefile(fileId);
    }

    public boolean isfileNameAvailable(String filename){
        return this.fileMapper.findFileByName(filename) == null;
    }

}
