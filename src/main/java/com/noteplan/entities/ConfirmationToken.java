package com.noteplan.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class ConfirmationToken {

    /**
     * ConfirmationToken id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * confirmationToken value
     */
    private String confirmationToken;

    /**
     * date when the confirmationToken was created
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * user who owns/created the confirmationToken
     */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    /**
     * ConfirmationToken blank constructor - it is used to give authority
     */
    public ConfirmationToken() {
    }

    /**
     * ConfirmationToken constructor from user
     */
    public ConfirmationToken(final User user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    /**
     * confirmationToken getter
     */
    public String getConfirmationToken() {
        return confirmationToken;
    }

    /**
     * confirmationToken setter
     */
    public void setConfirmationToken(final String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    /**
     * createdDate getter
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * createdDate setter
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * user getter
     */
    public User getUser() {
        return user;
    }

    /**
     * user setter
     */
    public void setUser(final User user) {
        this.user = user;
    }
}
