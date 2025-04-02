package com.noteplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteplan.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    /**
     * retrieves a confirmation token from a database
     */
    ConfirmationToken findByConfirmationToken(String confirmationToken);

}
