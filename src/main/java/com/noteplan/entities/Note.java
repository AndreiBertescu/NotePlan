package com.noteplan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Note {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String text;
	private int[] color;
	private boolean checklist;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int[] getColor() {
		return color;
	}
	public void setColor(int[] color) {
		this.color = color;
	}
	public boolean isChecklist() {
		return checklist;
	}
	public void setChecklist(boolean checklist) {
		this.checklist = checklist;
	}
}