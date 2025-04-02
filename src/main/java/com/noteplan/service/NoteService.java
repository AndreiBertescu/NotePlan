package com.noteplan.service;

import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noteplan.entities.Checkitem;
import com.noteplan.entities.Note;
import com.noteplan.entities.User;
import com.noteplan.repositories.NoteRepository;

@Service
public class NoteService {

    /**
     * NoteRepository interface
     */
    @Autowired
    NoteRepository noteRepo;

    /**
     * saves the new note to the db
     */
    public Note save(final User user, final Note note, final LinkedHashSet<Checkitem> checklist) {
        note.setUser(user);

        for (Checkitem checkitem : checklist) {
            checkitem.setNote(note);
        }
        note.setChecklist(checklist);

        return noteRepo.save(note);
    }

    /**
     * updates an existing note from the db
     */
    public Note update(final Note fullNote, final Note note, final LinkedHashSet<Checkitem> checklist) {
        fullNote.setTitle(note.getTitle());
        fullNote.setText(note.getText());

        fullNote.setChecklist(new LinkedHashSet<>());
        for (Checkitem checkitem : checklist) {
            checkitem.setNote(fullNote);
        }
        fullNote.setChecklist(checklist);

        return noteRepo.save(fullNote);
    }

    /**
     * finds a note by its id
     */
    public Note getNoteById(final Long noteId) {
        return noteRepo.findById(noteId).orElse(new Note());
    }

    /**
     * deletes a note by its id
     */
    public void delete(final Long noteId) {
        noteRepo.deleteById(noteId);
    }
}
