package com.falcon.cms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.falcon.cms.domain.enumeration.Mark;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mark", nullable = false)
    private Mark mark;

    @OneToOne
    @JoinColumn(unique = true)
    private Organizer reviewer;

    @OneToOne
    @JoinColumn(unique = true)
    private Paper paper;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mark getMark() {
        return mark;
    }

    public Review mark(Mark mark) {
        this.mark = mark;
        return this;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Organizer getReviewer() {
        return reviewer;
    }

    public Review reviewer(Organizer organizer) {
        this.reviewer = organizer;
        return this;
    }

    public void setReviewer(Organizer organizer) {
        this.reviewer = organizer;
    }

    public Paper getPaper() {
        return paper;
    }

    public Review paper(Paper paper) {
        this.paper = paper;
        return this;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        if (review.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", mark='" + getMark() + "'" +
            "}";
    }
}
