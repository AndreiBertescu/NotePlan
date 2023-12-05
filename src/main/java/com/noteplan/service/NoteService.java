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

	@Autowired
	NoteRepository noteRepo;

	public Note save(User user, Note note, LinkedHashSet<Checkitem> checklist) {
		note.setUser(user);

		for (Checkitem checkitem : checklist)
			checkitem.setNote(note);
		note.setChecklist(checklist);

		return noteRepo.save(note);
	}

	public Note update(Note fullNote, Note note, LinkedHashSet<Checkitem> checklist) {
		fullNote.setTitle(note.getTitle());
		fullNote.setText(note.getText());

		fullNote.setChecklist(new LinkedHashSet<>());
		for (Checkitem checkitem : checklist)
			checkitem.setNote(fullNote);
		fullNote.setChecklist(checklist);

		return noteRepo.save(fullNote);
	}

	public Note getNoteById(Long noteId) {
		return noteRepo.findById(noteId).orElse(new Note());
	}

	public void delete(Long noteId) {
		noteRepo.deleteById(noteId);
	}
}
