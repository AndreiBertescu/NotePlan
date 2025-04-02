package com.noteplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noteplan.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * finds a user in db by its username
     */
    User findByUsername(String username);

    @Override
    void deleteById(Long eventId);
}
