package com.noteplan.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteplan.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	Event findByDate(String date);

	@Override
	Optional<Event> findById(Long id);

	Set<Event> findAllByUser_id(Long id);

	@Override
	void deleteById(Long eventId);
}