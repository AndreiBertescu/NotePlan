package com.noteplan.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.noteplan.entities.Authority;
import com.noteplan.entities.User;

public class CustomSecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 1364622556423260049L;
	Set<Authority> authorities = new HashSet<>();

	public CustomSecurityUser() {
	}

	public CustomSecurityUser(User user) {
		super();
		this.setId(user.getId());
		this.setName(user.getName());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setAuthorities(user.getAuthorities());
		this.setTimeFormat(user.getTimeFormat());
		this.setTheme(user.getTheme());
	}

	@Override
	public Set<Authority> getAuthorities() {
		return super.getAuthorities();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}
}
