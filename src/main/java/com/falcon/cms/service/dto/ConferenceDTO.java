package com.falcon.cms.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Conference entity.
 */
public class ConferenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ZonedDateTime dateStart;

    private ZonedDateTime dateStop;

    private ZonedDateTime callForPapers;

    @NotNull
    private String location;

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

    public ZonedDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public ZonedDateTime getDateStop() {
        return dateStop;
    }

    public void setDateStop(ZonedDateTime dateStop) {
        this.dateStop = dateStop;
    }

    public ZonedDateTime getCallForPapers() {
        return callForPapers;
    }

    public void setCallForPapers(ZonedDateTime callForPapers) {
        this.callForPapers = callForPapers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConferenceDTO conferenceDTO = (ConferenceDTO) o;
        if(conferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConferenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateStart='" + getDateStart() + "'" +
            ", dateStop='" + getDateStop() + "'" +
            ", callForPapers='" + getCallForPapers() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
