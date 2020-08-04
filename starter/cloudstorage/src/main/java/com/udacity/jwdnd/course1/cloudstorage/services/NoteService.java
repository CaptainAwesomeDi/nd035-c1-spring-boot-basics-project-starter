package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> allUserNotes(User user){
        return this.noteMapper.findNotes(user.getUserid());
    }

    public Note findNote(Integer noteId) {
        return this.noteMapper.findNote(noteId);
    }

    public int createNote(Note note) {
        return this.noteMapper.insert(note);
    }

    public int update(Note note) {
        return this.noteMapper.update(note);
    }

    public void deleteNote(Integer noteId){
        this.noteMapper.deleteNote(noteId);
    }

}
