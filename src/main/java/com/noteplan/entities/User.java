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
     * id of user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * password of user
     */
    @Column(length = STRING_LENGTH)
    private String password;
    
    /**
     * email of user
     */
    @Column(length = STRING_LENGTH)
    private String username;
    
    /**
     * name of user
     */
    @Column(length = STRING_LENGTH)
    private String name;
    
    /**
     * 12hr or 24hr format
     */
    private boolean timeFormat = false;
    
    /**
     * dark or light theme
     */
    private boolean theme = false;

    /**
     * stores all the authorities of a given user
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();
    
    /**
     * stores all the events of a given user
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Event> events = new HashSet<>();
    
    /**
     * stores all the notes of a given user
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Note> notes = new HashSet<>();

    /**
     * user blank constructor
     */
    public User() {
    }

    /**
     * user copy constructor
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
     * events getter
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * events setter
     */
    public void setEvents(final Set<Event> events) {
        this.events = events;
    }

    /**
     * notes getter
     */
    public Set<Note> getNotes() {
        return notes;
    }

    /**
     * notes setter
     */
    public void setNotes(final Set<Note> notes) {
        this.notes = notes;
    }

    /**
     * id getter
     */
    public long getId() {
        return id;
    }

    /**
     * id setter
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * timeFormat getter
     */
    public boolean getTimeFormat() {
        return timeFormat;
    }

    /**
     * timeFormat setter
     */
    public void setTimeFormat(final boolean timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * theme getter
     */
    public boolean getTheme() {
        return theme;
    }

    /**
     * theme setter
     */
    public void setTheme(final boolean theme) {
        this.theme = theme;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * password setter
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * username setter
     */
    public void setUsername(final String name) {
        this.username = name;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     * authorities setter
     */
    public void setAuthorities(final Set<Authority> authorities) {
        this.authorities = authorities;
    }

    /**
     * name getter
     */
    public String getName() {
        return name;
    }

    /**
     * name setter
     */
    public void setName(final String name) {
        this.name = name;
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
