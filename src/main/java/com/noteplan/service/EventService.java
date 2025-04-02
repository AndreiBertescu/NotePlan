package com.noteplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noteplan.entities.Event;
import com.noteplan.entities.User;
import com.noteplan.repositories.EventRepository;

@Service
public class EventService {

    /**
     * EventRepository interfacce
     */
    @Autowired
    EventRepository eventRepo;

    /**
     * saves an event in the db
     */
    public Event save(final User user, final Event event) {
        event.setUser(user);
        String date = event.getDate();
        date = date.substring(0, 10) + " " + date.substring(11);
        event.setDate(date);
        return eventRepo.save(event);
    }

    /**
     * finds an event by its id
     */
    public Event getEventById(final Long eventId) {
        return eventRepo.findById(eventId).orElse(new Event());
    }

    /**
     * deletes an event
     */
    public void delete(final Long eventId) {
        eventRepo.deleteById(eventId);
    }

    /**
     * updates an event
     */
    public void update(final Event fullEvent, final Event event) {
        fullEvent.setTitle(event.getTitle());
        fullEvent.setDate(event.getDate());
        fullEvent.setColor(event.getColor());
        fullEvent.setDescription(event.getDescription());

        eventRepo.save(fullEvent);
    }
}
