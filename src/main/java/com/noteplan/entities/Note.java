package com.noteplan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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
	private boolean checklist;

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
		return checklist;
	}

	public void setChecklist(boolean checklist) {
		this.checklist = checklist;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", user=" + user + ", title=" + title + ", text=" + text + ", checklist=" + checklist
				+ "]";
	}
}