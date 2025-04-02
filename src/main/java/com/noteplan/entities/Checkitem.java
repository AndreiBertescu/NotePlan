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

    /**
     * checkitem id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * checkitem note.
     */
    @ManyToOne
    private Note note;

    /**
     * checkitem text.
     */
    @Column(length = STRING_LENGTH)
    private String text;
    
    /**
     * checkitem isChecked.
     */
    private boolean isChecked;

    /**
     * checkitem blank constructor.
     */
    public Checkitem() {
        this.text = "";
        this.isChecked = false;
    }

    /**
     * checkitem constructor from text and isChecked boolean.
     */
    public Checkitem(final String newText, boolean newIsChecked) {
        this.text = newText;
        this.isChecked = newIsChecked;
    }

    /**
     * checkitem text getter.
     * 
     * @return String.
     */
    public String getText() {
        return text;
    }

    /**
     * checkitem text setter.
     * 
     * @param text.
     */
    public void setText(final String newText) {
        this.text = newText;
    }

    /**
     * checkitem isChecked getter.
     * 
     * @return boolean.
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * checkitem isChecked setter.
     * 
     * @param isChecked.
     */
    public void setChecked(final boolean newIsChecked) {
        this.isChecked = newIsChecked;
    }

    /**
     * checkitem note getter.
     * 
     * @return Note.
     */
    public Note getNote() {
        return note;
    }

    /**
     * checkitem note setter.
     * 
     * @param note.
     */
    public void setNote(final Note newNote) {
        this.note = newNote;
    }

    @Override
    public String toString() {
        return "Checkitem [text=" + text + ", isChecked=" + isChecked + "]";
    }
}
