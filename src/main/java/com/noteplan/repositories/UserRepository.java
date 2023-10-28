package com.noteplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteplan.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
}
