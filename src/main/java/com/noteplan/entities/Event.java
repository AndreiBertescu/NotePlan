package com.noteplan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Event {

    private static final int COLOR_LENGTH = 7;
    private static final int STRING_LENGTH = 100;
    private static final int NOTE_LENGTH = 1000;

    /**
     * id of event.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * parent user.
     */
    @ManyToOne
    private User user;

    /**
     * date of event.
     */
    @Column(length = STRING_LENGTH)
    private String date;

    /**
     * title of event.
     */
    @Column(length = STRING_LENGTH)
    private String title;

    /**
     * description of event.
     */
    @Column(length = NOTE_LENGTH)
    private String description;

    /**
     * color of event.
     */
    @Column(length = COLOR_LENGTH)
    private String color;

    /**
     * event parent user getter.
     * 
     * @return User.
     */
    public User getUser() {
        return user;
    }

    /**
     * event parent user setter.
     * 
     * @param user.
     */
    public void setUser(final User newUser) {
        this.user = newUser;
    }

    /**
     * event id getter.
     * 
     * @return long.
     */
    public long getId() {
        return id;
    }

    /**
     * event id setter.
     * 
     * @param id.
     */
    public void setId(final long newId) {
        this.id = newId;
    }

    /**
     * event date getter.
     * 
     * @return String.
     */
    public String getDate() {
        return date;
    }

    /**
     * event date setter.
     * 
     * @param date.
     */
    public void setDate(final String newDate) {
        this.date = newDate;
    }

    /**
     * event description getter.
     * 
     * @return String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * event description setter.
     * 
     * @param description.
     */
    public void setDescription(final String newDescription) {
        this.description = newDescription;
    }

    /**
     * event color getter.
     * 
     * @return String.
     */
    public String getColor() {
        return color;
    }

    /**
     * event color setter.
     * 
     * @param color.
     */
    public void setColor(final String newColor) {
        this.color = newColor;
    }

    /**
     * event title getter.
     * 
     * @return String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * event title setter.
     * 
     * @param title.
     */
    public void setTitle(final String newTitle) {
        this.title = newTitle;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", date=" + date + ", title=" + title + ", description=" + description + ", color="
                + color + "]";
    }
}
