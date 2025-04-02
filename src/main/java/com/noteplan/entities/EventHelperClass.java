package com.noteplan.entities;

import java.util.ArrayList;
import java.util.List;

public class EventHelperClass {
    /**
     * stores the date when the events take place.
     */
    private String date;
    
    /**
     * stores all the events that take place.
     */
    private List<Event> events;

    /**
     * EventHelperClass blank constructor - it stores all the events that take place on a single date.
     */
    public EventHelperClass() {
        date = "";
        events = null;
    }

    /**
     * EventHelperClass constructor from a date.
     */
    public EventHelperClass(final String newDate) {
        this.date = newDate;
        events = new ArrayList<>();
    }

    /**
     * date getter.
     * 
     * @return String.
     */
    public String getDate() {
        return date;
    }

    /**
     * date setter.
     * 
     * @param date.
     */
    public void setDate(final String newDate) {
        this.date = newDate;
    }

    /**
     * events getter.
     * 
     * @return List.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * events setter.
     * 
     * @param events.
     */
    public void setEvents(final List<Event> newEvents) {
        this.events = newEvents;
    }

    /**
     * add event to events.
     * 
     * @param event.
     */
    public void addEvent(final Event newEvent) {
        events.add(newEvent);
    }

    @Override
    public String toString() {
        return "EventHelperClass [date=" + date + ", events=" + events + "]";
    }
}
