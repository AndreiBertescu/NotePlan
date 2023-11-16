package com.noteplan.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noteplan.entities.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	@Override
	Optional<Note> findById(Long id);

	Set<Note> findAllByUser_id(Long id);

	@Override
	void deleteById(Long eventId);
}
