package com.noteplan.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteplan.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
	@Override
	Optional<Note> findById(Long id);

	Set<Note> findAllByUser_id(Long id);

	@Override
	void deleteById(Long eventId);
}
