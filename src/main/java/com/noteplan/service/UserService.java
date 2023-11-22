package com.noteplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noteplan.entities.User;
import com.noteplan.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	public void checkValidity(User user) {
		if (findByUsername(user.getUsername()) != null)
			throw new DataIntegrityViolationException("*Email address is already in use.");
	}

	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user.setTimeFormat(false);
		user.setTheme(false);

		return userRepo.save(user);
	}

	public User saveWithEncodedPassword(User user) {
		user.setTimeFormat(false);
		user.setTheme(false);

		return userRepo.save(user);
	}

	public void delete(Long noteId) {
		userRepo.deleteById(noteId);
	}

	public User updatePreferences(User currentUser, String timeFormat, String theme) {
		User user = new User(currentUser);

		user.setTimeFormat(timeFormat.equals("24hr") ? false : true);
		currentUser.setTimeFormat(timeFormat.equals("24hr") ? false : true);

		user.setTheme(theme.equals("Light") ? false : true);
		currentUser.setTheme(theme.equals("Light") ? false : true);

		return userRepo.save(user);
	}
}
