package com.falcon.cms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Organizer entity.
 */
public class OrganizerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String affiliation;

    private String email;

    private String web;

    private Long userId;

    private Long conferenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizerDTO organizerDTO = (OrganizerDTO) o;
        if(organizerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganizerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", affiliation='" + getAffiliation() + "'" +
            ", email='" + getEmail() + "'" +
            ", web='" + getWeb() + "'" +
            "}";
    }
}
