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
     * note id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * note parent user
     */
    @ManyToOne
    private User user;
    
    /**
     * note title
     */
    @Column(length = STRING_LENGTH)
    private String title;
    
    /**
     * note text
     */
    @Column(length = NOTE_LENGTH)
    private String text;
    
    /**
     * note isChecklist boolean
     */
    private boolean isChecklist;
    
    /**
     * note checklist
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "note", orphanRemoval = true)
    @OrderBy("id")
    private Set<Checkitem> checklist = new LinkedHashSet<>();

    /**
     * note list of checkitems - used if note is checklist
     */
    @Transient
    private List<Checkitem> checklistList = new ArrayList<>();

    
    /**
     * note blank constructor
     */
    public Note() {
        title = null;
        text = null;
        isChecklist = false;
        checklist = new LinkedHashSet<>();
    }

    
    /**
     * user checklist getter
     */
    public Set<Checkitem> getChecklist() {
        return checklist;
    }

    
    /**
     * note checklist setter
     */
    public void setChecklist(final LinkedHashSet<Checkitem> checklist) {
        this.checklist = checklist;
    }

    
    /**
     * note parent user getter
     */
    public User getUser() {
        return user;
    }

    
    /**
     * note parent user setter
     */
    public void setUser(final User user) {
        this.user = user;
    }

    
    /**
     * note id getter
     */
    public long getId() {
        return id;
    }

    
    /**
     * note id setter
     */
    public void setId(final long id) {
        this.id = id;
    }

    
    /**
     * note title getter
     */
    public String getTitle() {
        return title;
    }

    
    /**
     * note title setter
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    
    /**
     * note text getter
     */
    public String getText() {
        return text;
    }

    
    /**
     * note text setter
     */
    public void setText(final String text) {
        this.text = text;
    }

    
    /**
     * note isChecklist getter
     */
    public boolean isChecklist() {
        return isChecklist;
    }

    
    /**
     * note isChecklist setter
     */
    public void setChecklist(final boolean isChecklist) {
        this.isChecklist = isChecklist;
    }

    
    /**
     * note list of checklists getter
     */
    public List<Checkitem> getChecklistList() {
        return checklistList;
    }
    
    /**
     * note list of checklists setter
     */
    public void setChecklistList(final List<Checkitem> checklistList) {
        this.checklistList = checklistList;
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", user=" + user + ", title=" + title + ", text=" + text + ", checklist="
                + isChecklist + "]";
    }
}