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
     * ConfirmationToken id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * confirmationToken value.
     */
    private String confirmationToken;

    /**
     * date when the confirmationToken was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * user who owns/created the confirmationToken.
     */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    /**
     * ConfirmationToken blank constructor - it is used to give authority.
     */
    public ConfirmationToken() {
    }

    /**
     * ConfirmationToken constructor from user.
     */
    public ConfirmationToken(final User newUser) {
        this.user = newUser;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    /**
     * confirmationToken getter.
     * 
     * @return String.
     */
    public String getConfirmationToken() {
        return confirmationToken;
    }

    /**
     * confirmationToken setter.
     * 
     * @param confirmationToken.
     */
    public void setConfirmationToken(final String newConfirmationToken) {
        this.confirmationToken = newConfirmationToken;
    }

    /**
     * createdDate getter.
     * 
     * @return Date.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * createdDate setter.
     * 
     * @param createdDate.
     */
    public void setCreatedDate(final Date newCreatedDate) {
        this.createdDate = newCreatedDate;
    }

    /**
     * user getter.
     * 
     * @return User.
     */
    public User getUser() {
        return user;
    }

    /**
     * user setter.
     * 
     * @param user.
     */
    public void setUser(final User newUser) {
        this.user = newUser;
    }
}
