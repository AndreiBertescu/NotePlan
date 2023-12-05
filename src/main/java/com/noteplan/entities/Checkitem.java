package com.noteplan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Checkitem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Note note;

	@Column(length = 500)
	private String text;
	private boolean isChecked;

	public Checkitem() {
		this.text = "";
		this.isChecked = false;
	}

	public Checkitem(String text, boolean isChecked) {
		this.text = text;
		this.isChecked = isChecked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Checkitem [text=" + text + ", isChecked=" + isChecked + "]";
	}
}
