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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.noteplan.entities.Event;
import com.noteplan.entities.User;
import com.noteplan.repositories.EventRepository;
import com.noteplan.service.EventService;

@Controller
public class DashboardController {

	@Autowired
	EventService eventService;
	@Autowired
	EventRepository eventRepo;

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
	public String dashBoardView(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("event", new Event());

		Set<Event> loadedEvents = eventRepo.findAllByUser_id(user.getId());
		List<Event> events = new ArrayList<>();
		events.addAll(loadedEvents);

		if (events.size() > 0) {
			Collections.sort(events, new Comparator<Event>() {
				@Override
				public int compare(final Event object1, final Event object2) {
					return object1.getDate().compareTo(object2.getDate());
				}
			});
		}

		model.put("events", events);

		return "dashboard";
	}

	@PostMapping("/dashboard")
	public String newEventForm(@AuthenticationPrincipal User user, Event event) {
		eventService.save(user, event);
		return "redirect:/dashboard";
	}
}
