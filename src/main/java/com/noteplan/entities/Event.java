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
     * id of event
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * parent user
     */
    @ManyToOne
    private User user;

    /**
     * date of event
     */
    @Column(length = STRING_LENGTH)
    private String date;

    /**
     * title of event
     */
    @Column(length = STRING_LENGTH)
    private String title;

    /**
     * description of event
     */
    @Column(length = NOTE_LENGTH)
    private String description;

    /**
     * color of event
     */
    @Column(length = COLOR_LENGTH)
    private String color;

    /**
     * event parent user getter
     */
    public User getUser() {
        return user;
    }

    /**
     * event parent user setter
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * event id getter
     */
    public long getId() {
        return id;
    }

    /**
     * event id setter
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * event date getter
     */
    public String getDate() {
        return date;
    }

    /**
     * event date setter
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * event description getter
     */
    public String getDescription() {
        return description;
    }

    /**
     * event description setter
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * event color getter
     */
    public String getColor() {
        return color;
    }

    /**
     * event color setter
     */
    public void setColor(final String color) {
        this.color = color;
    }

    /**
     * event title getter
     */
    public String getTitle() {
        return title;
    }

    /**
     * event title setter
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", date=" + date + ", title=" + title + ", description=" + description + ", color="
                + color + "]";
    }
}
