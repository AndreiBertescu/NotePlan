package com.noteplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noteplan.entities.Event;
import com.noteplan.entities.User;
import com.noteplan.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepo;

	public Event save(User user, Event event) {
		event.setUser(user);
		String date = event.getDate();
		date = date.substring(0, 10) + " " + date.substring(11);
		event.setDate(date);

		return eventRepo.save(event);
	}

	public Event getEventById(Long eventId) {
		return eventRepo.findById(eventId).orElse(new Event());
	}

	public void delete(Long eventId) {
		eventRepo.deleteById(eventId);
	}

	public void update(Event fullEvent, Event event) {
		fullEvent.setTitle(event.getTitle());
		fullEvent.setDate(event.getDate());
		fullEvent.setColor(event.getColor());
		fullEvent.setDescription(event.getDescription());

		eventRepo.save(fullEvent);
	}
}
