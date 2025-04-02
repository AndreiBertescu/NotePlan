package com.noteplan.entities;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 2012020929299864445L;

    /**
     * id of authority
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * authority - gives access to certain parts of the domain
     */
    private String authority;

    /**
     * parent user of authority
     */
    @ManyToOne
    private User user;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    /**
     * authority setter
     */
    public void setAuthority(final String authority) {
        this.authority = authority;
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
