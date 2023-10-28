package com.noteplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noteplan.entities.Event;
import com.noteplan.entities.User;
import com.noteplan.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Event save(User user, Event event) {
		event.setUser(user);
		String date = event.getDate();
		date = date.substring(0, 10) + " " + date.substring(11);
		event.setDate(date);
		
		return eventRepo.save(event);
	}
}
