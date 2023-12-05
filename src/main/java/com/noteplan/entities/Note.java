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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@Column(length = 100)
	private String title;
	@Column(length = 5000)
	private String text;
	private boolean isChecklist;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "note", orphanRemoval = true)
	@OrderBy("id")
	private Set<Checkitem> checklist = new LinkedHashSet<>();

	@Transient
	private List<Checkitem> checklistList = new ArrayList<>();

	public Note() {
		title = null;
		text = null;
		isChecklist = false;
		checklist = new LinkedHashSet<>();
	}

	public Set<Checkitem> getChecklist() {
		return checklist;
	}

	public void setChecklist(LinkedHashSet<Checkitem> checklist) {
		this.checklist = checklist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecklist() {
		return isChecklist;
	}

	public void setChecklist(boolean isChecklist) {
		this.isChecklist = isChecklist;
	}

	public List<Checkitem> getChecklistList() {
		return checklistList;
	}

	public void setChecklistList(List<Checkitem> checklistList) {
		this.checklistList = checklistList;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", user=" + user + ", title=" + title + ", text=" + text + ", checklist="
				+ isChecklist + "]";
	}
}