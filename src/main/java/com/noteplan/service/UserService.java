package com.noteplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noteplan.entities.User;
import com.noteplan.repositories.UserRepository;

@Service
public class UserService {

    /**
     * UserRepository interface.
     */
    @Autowired
    private UserRepository userRepo;

    /**
     * PasswordEncoder interface.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * checks if email is already in use.
     *
     * @param user.
     */
    public void checkValidity(final User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new DataIntegrityViolationException(
            		"*Email address is already in use."
            );
        }
    }

    /**
     * saves finds user by username.
     *
     * @param username.
     *
     * @return User.
     */
    public User findByUsername(final String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * saves the user in the db.
     *
     * @param user.
     *
     * @return User.
     */
    public User save(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setTimeFormat(false);
        user.setTheme(false);

        return userRepo.save(user);
    }

    /**
     * saves the user with the already encoded password.
     *
     * @param user.
     *
     * @return User.
     */
    public User saveWithEncodedPassword(final User user) {
        user.setTimeFormat(false);
        user.setTheme(false);

        return userRepo.save(user);
    }

    /**
     * deletes note from db.
     *
     * @param noteId.
     */
    public void delete(final Long noteId) {
        userRepo.deleteById(noteId);
    }

    /**
     * updates preferences in db.
     *
     * @param currentUser.
     * @param timeFormat.
     * @param theme.
     *
     * @return User.
     */
    public User updatePreferences(final User currentUser, final String timeFormat, final String theme) {
        User user = new User(currentUser);

        user.setTimeFormat(!timeFormat.equals("24hr"));
        currentUser.setTimeFormat(!timeFormat.equals("24hr"));

        user.setTheme(!theme.equals("Light"));
        currentUser.setTheme(!theme.equals("Light"));

        return userRepo.save(user);
    }
}
