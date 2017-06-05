package com.falcon.cms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Organizer.
 */
@Entity
@Table(name = "organizer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organizer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "affiliation")
    private String affiliation;

    @Column(name = "email")
    private String email;

    @Column(name = "web")
    private String web;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private Conference conference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organizer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public Organizer affiliation(String affiliation) {
        this.affiliation = affiliation;
        return this;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getEmail() {
        return email;
    }

    public Organizer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public Organizer web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public User getUser() {
        return user;
    }

    public Organizer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conference getConference() {
        return conference;
    }

    public Organizer conference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organizer organizer = (Organizer) o;
        if (organizer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organizer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", affiliation='" + getAffiliation() + "'" +
            ", email='" + getEmail() + "'" +
            ", web='" + getWeb() + "'" +
            "}";
    }
}
