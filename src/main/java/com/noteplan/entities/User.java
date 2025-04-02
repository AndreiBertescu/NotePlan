package com.noteplan.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    private static final long serialVersionUID = 3578466250205857028L;
    private static final int STRING_LENGTH = 100;

    /**
     * id of user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * password of user.
     */
    @Column(length = STRING_LENGTH)
    private String password;
    
    /**
     * email of user.
     */
    @Column(length = STRING_LENGTH)
    private String username;
    
    /**
     * name of user.
     */
    @Column(length = STRING_LENGTH)
    private String name;
    
    /**
     * 12hr or 24hr format.
     */
    private boolean timeFormat = false;
    
    /**
     * dark or light theme.
     */
    private boolean theme = false;

    /**
     * stores all the authorities of a given user.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();
    
    /**
     * stores all the events of a given user.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Event> events = new HashSet<>();
    
    /**
     * stores all the notes of a given user.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Note> notes = new HashSet<>();

    /**
     * user blank constructor.
     */
    public User() {
    }

    /**
     * user copy constructor.
     */
    public User(final User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setAuthorities(user.getAuthorities());
        this.setTimeFormat(user.getTimeFormat());
        this.setTheme(user.getTheme());
    }

    /**
     * events getter.
     *
     * @return Set.
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * events setter.
     *
     * @param events.
     */
    public void setEvents(final Set<Event> newEvents) {
        this.events = newEvents;
    }

    /**
     * notes getter.
     *
     * @return Set.
     */
    public Set<Note> getNotes() {
        return notes;
    }

    /**
     * notes setter.
     *
     * @param notes.
     */
    public void setNotes(final Set<Note> newNotes) {
        this.notes = newNotes;
    }

    /**
     * id getter.
     *
     * @return long.
     */
    public long getId() {
        return id;
    }

    /**
     * id setter.
     *
     * @param id.
     */
    public void setId(final long newId) {
        this.id = newId;
    }

    /**
     * timeFormat getter.
     *
     * @return boolean.
     */
    public boolean getTimeFormat() {
        return timeFormat;
    }

    /**
     * timeFormat setter.
     *
     * @param timeFormat.
     */
    public void setTimeFormat(final boolean newTimeFormat) {
        this.timeFormat = newTimeFormat;
    }

    /**
     * theme getter.
     *
     * @return boolean.
     */
    public boolean getTheme() {
        return theme;
    }

    /**
     * theme setter.
     *
     * @param theme.
     */
    public void setTheme(final boolean newTheme) {
        this.theme = newTheme;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * password setter.
     *
     * @param password.
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * username setter.
     *
     * @param name.
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     * authorities setter.
     *
     * @param authorities.
     */
    public void setAuthorities(final Set<Authority> newAuthorities) {
        this.authorities = newAuthorities;
    }

    /**
     * name getter.
     *
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * name setter.
     *
     * @param name.
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", password=" + password + ", username=" + username + ", name=" + name
                + ", authorities=" + authorities + "]";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
