package com.noteplan.controllers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.noteplan.entities.Checkitem;
import com.noteplan.entities.Event;
import com.noteplan.entities.EventHelperClass;
import com.noteplan.entities.Note;
import com.noteplan.entities.User;
import com.noteplan.repositories.EventRepository;
import com.noteplan.repositories.NoteRepository;
import com.noteplan.service.EventService;
import com.noteplan.service.NoteService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes(names = { "selectedEvent", "selectedNote" })
public class DashboardController {

    /**
     * EventService instantiation.
     */
    @Autowired
    EventService eventService;

    /**
     * NoteService instantiation.
     */
    @Autowired
    NoteService noteService;

    /**
     * EventRepository instantiation.
     */
    @Autowired
    EventRepository eventRepo;

    /**
     * NoteRepository instantiation.
     */
    @Autowired
    NoteRepository noteRepo;

    /**
     * initializes the selectedEvent atribute with an new Event object.
     *
     * @return Event.
     */
    @ModelAttribute("selectedEvent")
    public Event initializeSelectedEvent() {
        return new Event();
    }

    /**
     * initializes the selectedNote atribute with an new Note object.
     *
     * @return Note.
     */
    @ModelAttribute("selectedNote")
    public Note initializeSelectedNote() {
        return new Note();
    }

    /**
     * redirects to dashboard form the root web page if the user is authenticated.
     *
     * @param authentication.
     *
     * @return String.
     */
    @GetMapping("/")
    public String rootView(final Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    /**
     * redirects to dashboard form index web page if the user is authenticated.
     *
     * @param authentication.
     *
     * @return String.
     */
    @GetMapping("/index")
    public String rootView2(final Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    /**
     * loads the dashboard web page, all the notes and event from the specific user, and initializes/formats them.
     *
     * @param user.
     * @param model.
     *
     * @return String.
     */
    @GetMapping("/dashboard")
    public String dashBoardView(@AuthenticationPrincipal final User user, final ModelMap model) {
        model.put("username", user.getName());
        model.put("initials", getInitials(user.getName()));
        model.put("theme", !user.getTheme() ? "Light" : "Dark");
        model.put("isChecklist", "false");

        Event event = new Event();
        event.setColor("#000000");
        model.put("event", event);

        Note note = new Note();
        note.setTitle("New note");
        note.setChecklist(true);
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

        // reformat the events description
        for (Event ev : sortedEvents) {
            if (ev.getDescription() != "" && ev.getDescription() != null) {
                String s = ev.getDescription();

                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '\n') {
                        s = s.substring(0, i) + "<br>" + s.substring(i, s.length());
                        i += "<br>".length();
                    }
                }

                ev.setDescription(s);
            }
        }

        // reformat the events
        String lastDate = "";
        int dateLength = 10;
        List<EventHelperClass> events = new ArrayList<>();
        EventHelperClass lastEvent = null;
        for (Event ev : sortedEvents) {
            if (!lastDate.equals(ev.getDate().substring(0, dateLength))) {
                if (lastEvent != null) {
                    events.add(lastEvent);
                }

                lastDate = ev.getDate().substring(0, dateLength);
                lastEvent = new EventHelperClass(lastDate);
            }

            // set time format
            if (!user.getTimeFormat()) {
                ev.setDate(ev.getDate().substring(dateLength + 1));
            } else {
                ev.setDate(formatTime(ev.getDate().substring(dateLength + 1)));
            }

            lastEvent.addEvent(ev);
        }
        if (lastEvent != null) {
            events.add(lastEvent);
        }

        model.put("events", events);

        // load all notes
        Set<Note> notes = noteRepo.findAllByUser_id(user.getId());

        // reformat the notes description
        int noteLength = 8;
        for (Note nt : notes) {
            List<Checkitem> ls = new ArrayList<>(nt.getChecklist());
            if (ls.size() >= noteLength) {
                ls = ls.subList(0, noteLength);
            }
            nt.setChecklistList(ls);

            if (nt.getText() != "" && nt.getText() != null) {
                String s = nt.getText();

                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '\n') {
                        s = s.substring(0, i) + "<br>" + s.substring(i, s.length());
                        i += "<br>".length();
                    }
                }

                nt.setText(s);
            }
        }

        model.put("notes", notes);

        return "dashboard";
    }

    /**
     * saves active event.
     *
     * @param user.
     * @param event.
     *
     * @return String.
     */
    @PostMapping("/dashboard/saveEvent")
    public String newEventForm(@AuthenticationPrincipal final User user, final Event event) {
        eventService.save(user, event);

        return "redirect:/dashboard";
    }

    /**
     * selects new active event.
     *
     * @param user.
     * @param eventId.
     * @param smodel.
     *
     * @return String.
     */
    @PostMapping("/dashboard/getEventDetails/{eventId}")
    public String getEventDetails(@AuthenticationPrincipal final User user, @PathVariable final Long eventId, final Model smodel) {
        Event event = eventService.getEventById(eventId);

        smodel.addAttribute("selectedEvent", event);
        smodel.addAttribute("selectedNote", new Note());

        return "redirect:/dashboard";
    }

    /**
     * updates active event.
     *
     * @param user.
     * @param event.
     * @param smodel.
     *
     * @return String.
     */
    @PostMapping("/dashboard/updateEvent")
    public String updateEventForm(@AuthenticationPrincipal final User user, final Event event, final Model smodel) {
        eventService.update((Event) smodel.getAttribute("selectedEvent"), event);

        smodel.addAttribute("selectedEvent", new Event());

        return "redirect:/dashboard";
    }

    /**
     * deletes active event.
     *
     * @param user.
     * @param smodel.
     *
     * @return String.
     */
    @PostMapping("/dashboard/deleteEvent")
    public String deleteEventForm(@AuthenticationPrincipal final User user, final Model smodel) {
        eventService.delete(((Event) smodel.getAttribute("selectedEvent")).getId());

        smodel.addAttribute("selectedEvent", new Event());

        return "redirect:/dashboard";
    }

    /**
     * saves active note.
     *
     * @param user.
     * @param params.
     * @param isChecklist.
     * @param note.
     *
     * @return String.
     */
    @PostMapping("/dashboard/saveNote")
    public String newNoteForm(@AuthenticationPrincipal final User user, @RequestParam final Map<String, String> params, final String isChecklist, final Note note) {
        note.setChecklist(isChecklist != null && isChecklist.equals("isChecklist"));
        LinkedHashSet<Checkitem> checklist = new LinkedHashSet<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().startsWith("checkitem")) {
                String itemName = entry.getValue();
                String checkboxKey = "checkbox" + entry.getKey().substring(9);
                boolean isChecked = "on".equals(params.getOrDefault(checkboxKey, "off"));

                checklist.add(new Checkitem(itemName, isChecked));
            }
        }

        noteService.save(user, note, checklist);

        return "redirect:/dashboard";
    }

    /**
     * selects new active note.
     *
     * @param user.
     * @param eventId.
     * @param smodel.
     *
     * @return String.
     */
    @PostMapping("/dashboard/getNoteDetails/{eventId}")
    public String getNoteDetails(@AuthenticationPrincipal final User user, @PathVariable final Long eventId, final Model smodel) {
        Note note = noteService.getNoteById(eventId);

        smodel.addAttribute("selectedEvent", new Event());
        smodel.addAttribute("selectedNote", note);

        return "redirect:/dashboard";
    }

    /**
     * updates active note.
     *
     * @param user.
     * @param note.
     * @param params.
     * @param smodel.
     *
     * @return String.
     */
    @PostMapping("/dashboard/updateNote")
    public String updateNoteForm(@AuthenticationPrincipal final User user, final Note note, @RequestParam final Map<String, String> params, final Model smodel) {
        LinkedHashSet<Checkitem> checklist = new LinkedHashSet<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().startsWith("checkitem")) {
                String itemName = entry.getValue();
                String checkboxKey = "checkbox" + entry.getKey().substring(9);
                boolean isChecked = "on".equals(params.getOrDefault(checkboxKey, "off"));

                checklist.add(new Checkitem(itemName, isChecked));
            }
        }

        noteService.update((Note) smodel.getAttribute("selectedNote"), note, checklist);

        smodel.addAttribute("selectedNote", new Note());

        return "redirect:/dashboard";
    }

    /**
     * deletes active note.
     *
     * @param smodel.
     *
     * @return String.
     */
    @PostMapping("/dashboard/deleteNote")
    public String deleteNoteForm(final Model smodel) {
        noteService.delete(((Note) smodel.getAttribute("selectedNote")).getId());

        smodel.addAttribute("selectedNote", new Note());

        return "redirect:/dashboard";
    }

    /**
     * assigns new values to smodel - event and note.
     *
     * @param smodel.
     * @param response.
     */
    @PostMapping("/dashboard/deleteSmodel")
    public void deleteSmodel(final Model smodel, final HttpServletResponse response) {
        smodel.addAttribute("selectedNote", new Note());
        smodel.addAttribute("selectedEvent", new Event());

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * reformats the time string.
     *
     * @param militaryTime.
     *
     * @return String.
     */
    private String formatTime(final String militaryTime) {
        LocalTime time = LocalTime.parse(militaryTime, DateTimeFormatter.ofPattern("HH:mm"));
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    /**
     * returns the username initials.
     *
     * @param name.
     *
     * @return String.
     */
    public static String getInitials(final String name) {
        if (name.split(" ").length >= 2) {
            return name.split(" ")[0].charAt(0) + "" + name.split(" ")[1].charAt(0);
        }
        return name.substring(0, 2);
    }
}
