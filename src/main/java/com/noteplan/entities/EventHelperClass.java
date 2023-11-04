package com.noteplan.entities;

import java.util.ArrayList;
import java.util.List;

public class EventHelperClass {
	private String date;
	private List<Event> events;

	public EventHelperClass() {
		date = "";
		events = null;
	}

	public EventHelperClass(String date) {
		this.date = date;
		events = new ArrayList<>();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	@Override
	public String toString() {
		return "EventHelperClass [date=" + date + ", events=" + events + "]";
	}
}
