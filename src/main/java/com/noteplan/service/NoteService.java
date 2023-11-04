package com.noteplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noteplan.entities.Note;
import com.noteplan.entities.User;
import com.noteplan.repositories.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository noteRepo;

	public Note save(User user, Note note) {
		note.setUser(user);
		note.setChecklist(false);

		return noteRepo.save(note);
	}

	public Note update(Note fullNote, Note note) {
		fullNote.setTitle(note.getTitle());
		fullNote.setText(note.getText());

		return noteRepo.save(fullNote);
	}

	public Note getNoteById(Long noteId) {
		return noteRepo.findById(noteId).orElse(new Note());
	}

	public void delete(Long noteId) {
		noteRepo.deleteById(noteId);
	}
}
