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
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setAuthorities(user.getAuthorities());
		this.setName(user.getName());
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

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
