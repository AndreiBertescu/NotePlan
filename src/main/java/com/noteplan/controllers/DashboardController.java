package com.noteplan.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.noteplan.entities.Event;
import com.noteplan.entities.EventHelperClass;
import com.noteplan.entities.Note;
import com.noteplan.entities.User;
import com.noteplan.repositories.EventRepository;
import com.noteplan.repositories.NoteRepository;
import com.noteplan.service.EventService;
import com.noteplan.service.NoteService;

@Controller
@SessionAttributes(names = { "selectedEvent", "selectedNote" })
public class DashboardController {

	@Autowired
	EventService eventService;

	@Autowired
	NoteService noteService;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	NoteRepository noteRepo;

	@ModelAttribute("selectedEvent")
	public Event initializeSelectedEvent() {
		return new Event();
	}

	@ModelAttribute("selectedNote")
	public Note initializeSelectedNote() {
		return new Note();
	}

	@GetMapping("/")
	public String rootView(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated())
			return "redirect:/dashboard";
		else
			return "index";
	}

	@GetMapping("/index")
	public String rootView2(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated())
			return "redirect:/dashboard";
		else
			return "index";
	}

	@GetMapping("/dashboard")
	public String dashBoardView(@AuthenticationPrincipal User user, ModelMap model, Model smodel) {
		Event event = new Event();
		event.setColor("#000000");
		model.put("event", event);

		Note note = new Note();
		note.setTitle("New note");
		model.put("note", note);

		// load all events and sort by date
		Set<Event> loadedEvents = eventRepo.findAllByUser_id(user.getId());
		List<Event> sortedEvents = new ArrayList<>();
		sortedEvents.addAll(loadedEvents);

		if (sortedEvents.size() > 0) {
			Collections.sort(sortedEvents, new Comparator<Event>() {
				@Override
				public int compare(final Event object1, final Event object2) {
					return object1.getDate().compareTo(object2.getDate());
				}
			});
		}

		String lastDate = "";
		List<EventHelperClass> events = new ArrayList<>();
		EventHelperClass lastEvent = null;
		for (Event ev : sortedEvents) {
			if (!lastDate.equals(ev.getDate().substring(0, 10))) {
				if (lastEvent != null)
					events.add(lastEvent);

				lastDate = ev.getDate().substring(0, 10);
				lastEvent = new EventHelperClass(lastDate);
			}
			ev.setDate(ev.getDate().substring(11));
			lastEvent.addEvent(ev);
		}
		if (lastEvent != null)
			events.add(lastEvent);

		model.put("events", events);

		// load all notes
		Set<Note> notes = noteRepo.findAllByUser_id(user.getId());
		model.put("notes", notes);

		return "dashboard";
	}

	@PostMapping("/dashboard/saveEvent")
	public String newEventForm(@AuthenticationPrincipal User user, Event event) {
		eventService.save(user, event);
		return "redirect:/dashboard";
	}

	@PostMapping("/getEventDetails/{eventId}")
	public String getEventDetails(@AuthenticationPrincipal User user, @PathVariable Long eventId, Model smodel) {
		Event event = eventService.getEventById(eventId);

		smodel.addAttribute("selectedEvent", event);
		smodel.addAttribute("selectedNote", new Note());

		return "redirect:/dashboard";
	}

	@PostMapping("/dashboard/updateEvent")
	public String updateEventForm(@AuthenticationPrincipal User user, Event event, Model smodel) {
		eventService.update((Event) smodel.getAttribute("selectedEvent"), event);

		smodel.addAttribute("selectedEvent", new Event());

		return "redirect:/dashboard";
	}

	@PostMapping("/dashboard/deleteEvent")
	public String deleteEventForm(@AuthenticationPrincipal User user, Model smodel) {
		eventService.delete(((Event) smodel.getAttribute("selectedEvent")).getId());

		smodel.addAttribute("selectedEvent", new Event());

		return "redirect:/dashboard";
	}

	@PostMapping("/dashboard/saveNote")
	public String newNoteForm(@AuthenticationPrincipal User user, Note note) {
		noteService.save(user, note);

		return "redirect:/dashboard";
	}

	@PostMapping("/getNoteDetails/{eventId}")
	public String getNoteDetails(@AuthenticationPrincipal User user, @PathVariable Long eventId, Model smodel) {
		Note note = noteService.getNoteById(eventId);

		smodel.addAttribute("selectedEvent", new Event());
		smodel.addAttribute("selectedNote", note);

		return "redirect:/dashboard";
	}

	@PostMapping("/dashboard/updateNote")
	public String updateNoteForm(@AuthenticationPrincipal User user, Note note, Model smodel) {
		noteService.update((Note) smodel.getAttribute("selectedNote"), note);

		smodel.addAttribute("selectedNote", new Note());

		return "redirect:/dashboard";
	}

	@PostMapping("/dashboard/deleteNote")
	public String deleteNoteForm(Model smodel) {
		noteService.delete(((Note) smodel.getAttribute("selectedNote")).getId());

		smodel.addAttribute("selectedNote", new Note());

		return "redirect:/dashboard";
	}
}
