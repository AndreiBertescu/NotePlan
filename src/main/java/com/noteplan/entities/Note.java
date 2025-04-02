package com.noteplan.entities;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Transient;

@Entity
public class Note {

    private static final int STRING_LENGTH = 100;
    private static final int NOTE_LENGTH = 1000;
    
    /**
     * note id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * note parent user.
     */
    @ManyToOne
    private User user;
    
    /**
     * note title.
     */
    @Column(length = STRING_LENGTH)
    private String title;
    
    /**
     * note text.
     */
    @Column(length = NOTE_LENGTH)
    private String text;
    
    /**
     * note isChecklist boolean.
     */
    private boolean isChecklist;
    
    /**
     * note checklist.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "note", orphanRemoval = true)
    @OrderBy("id")
    private Set<Checkitem> checklist = new LinkedHashSet<>();

    /**
     * note list of checkitems - used if note is checklist.
     */
    @Transient
    private List<Checkitem> checklistList = new ArrayList<>();

    
    /**
     * note blank constructor.
     */
    public Note() {
        title = null;
        text = null;
        isChecklist = false;
        checklist = new LinkedHashSet<>();
    }

    
    /**
     * user checklist getter.
     * 
     * @return Set.
     */
    public Set<Checkitem> getChecklist() {
        return checklist;
    }

    
    /**
     * note checklist setter.
     * 
     * @param checklist.
     */
    public void setChecklist(final LinkedHashSet<Checkitem> newChecklist) {
        this.checklist = newChecklist;
    }

    
    /**
     * note parent user getter.
     * 
     * @return User.
     */
    public User getUser() {
        return user;
    }

    
    /**
     * note parent user setter.
     * 
     * @param user.
     */
    public void setUser(final User newUser) {
        this.user = newUser;
    }

    
    /**
     * note id getter.
     * 
     * @return long.
     */
    public long getId() {
        return id;
    }

    
    /**
     * note id setter.
     * 
     * @param id.
     */
    public void setId(final long newId) {
        this.id = newId;
    }

    
    /**
     * note title getter.
     * 
     * @return String.
     */
    public String getTitle() {
        return title;
    }

    
    /**
     * note title setter.
     * 
     * @param title.
     */
    public void setTitle(final String newTitle) {
        this.title = newTitle;
    }

    
    /**
     * note text getter.
     * 
     * @return String.
     */
    public String getText() {
        return text;
    }

    
    /**
     * note text setter.
     * 
     * @param text.
     */
    public void setText(final String newText) {
        this.text = newText;
    }

    
    /**
     * note isChecklist getter.
     * 
     * @return boolean.
     */
    public boolean isChecklist() {
        return isChecklist;
    }

    
    /**
     * note isChecklist setter.
     * 
     * @param isChecklist.
     */
    public void setChecklist(final boolean newIsChecklist) {
        this.isChecklist = newIsChecklist;
    }

    
    /**
     * note list of checklists getter.
     * 
     * @return List.
     */
    public List<Checkitem> getChecklistList() {
        return checklistList;
    }
    
    /**
     * note list of checklists setter.
     * 
     * @param checklistList.
     */
    public void setChecklistList(final List<Checkitem> newChecklistList) {
        this.checklistList = newChecklistList;
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", user=" + user + ", title=" + title + ", text=" + text + ", checklist="
                + isChecklist + "]";
    }
}
