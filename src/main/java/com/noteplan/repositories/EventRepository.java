package com.noteplan.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteplan.entities.Event;

public interface EventRepository  extends JpaRepository<Event, Long>{
	
	Event findByDate(String date);
	
	Set<Event> findAllByUser_id(Long id);
	
}