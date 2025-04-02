package com.noteplan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Checkitem {
	
    private static final int STRING_LENGTH = 100;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Note note;

    @Column(length = STRING_LENGTH)
    private String text;
    private boolean isChecked;

    /**
     * Checkitem blank constructor
     */
    public Checkitem() {
        this.text = "";
        this.isChecked = false;
    }

    /**
     * Checkitem constructor from text and isChecked boolean
     */
    public Checkitem(final String text, boolean isChecked) {
        this.text = text;
        this.isChecked = isChecked;
    }

    /**
     * checkitem text getter
     */
    public String getText() {
        return text;
    }

    /**
     * checkitem text setter
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * checkitem isChecked getter
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * checkitem isChecked setter
     */
    public void setChecked(final boolean isChecked) {
        this.isChecked = isChecked;
    }

    /**
     * checkitem note getter
     */
    public Note getNote() {
        return note;
    }

    /**
     * checkitem note setter
     */
    public void setNote(final Note note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Checkitem [text=" + text + ", isChecked=" + isChecked + "]";
    }
}
