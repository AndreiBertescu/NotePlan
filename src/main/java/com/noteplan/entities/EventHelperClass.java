package com.noteplan.entities;

import java.util.ArrayList;
import java.util.List;

public class EventHelperClass {
    /**
     * stores the date when the events take place
     */
    private String date;
    
    /**
     * stores all the events that take place
     */
    private List<Event> events;

    /**
     * EventHelperClass blank constructor - it stores all the events that take place on a single date
     */
    public EventHelperClass() {
        date = "";
        events = null;
    }

    /**
     * EventHelperClass constructor from a date
     */
    public EventHelperClass(final String date) {
        this.date = date;
        events = new ArrayList<>();
    }

    /**
     * date getter
     */
    public String getDate() {
        return date;
    }

    /**
     * date setter
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * events getter
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * events setter
     */
    public void setEvents(final List<Event> events) {
        this.events = events;
    }

    /**
     * add event to events
     */
    public void addEvent(final Event event) {
        events.add(event);
    }

    @Override
    public String toString() {
        return "EventHelperClass [date=" + date + ", events=" + events + "]";
    }
}
