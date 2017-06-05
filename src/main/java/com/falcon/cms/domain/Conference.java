package com.falcon.cms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Conference.
 */
@Entity
@Table(name = "conference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "date_start", nullable = false)
    private ZonedDateTime dateStart;

    @Column(name = "date_stop")
    private ZonedDateTime dateStop;

    @Column(name = "call_for_papers")
    private ZonedDateTime callForPapers;

    @NotNull
    @Column(name = "location", nullable = false)
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

    public Conference name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDateStart() {
        return dateStart;
    }

    public Conference dateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public void setDateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public ZonedDateTime getDateStop() {
        return dateStop;
    }

    public Conference dateStop(ZonedDateTime dateStop) {
        this.dateStop = dateStop;
        return this;
    }

    public void setDateStop(ZonedDateTime dateStop) {
        this.dateStop = dateStop;
    }

    public ZonedDateTime getCallForPapers() {
        return callForPapers;
    }

    public Conference callForPapers(ZonedDateTime callForPapers) {
        this.callForPapers = callForPapers;
        return this;
    }

    public void setCallForPapers(ZonedDateTime callForPapers) {
        this.callForPapers = callForPapers;
    }

    public String getLocation() {
        return location;
    }

    public Conference location(String location) {
        this.location = location;
        return this;
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
        Conference conference = (Conference) o;
        if (conference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conference{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateStart='" + getDateStart() + "'" +
            ", dateStop='" + getDateStop() + "'" +
            ", callForPapers='" + getCallForPapers() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
