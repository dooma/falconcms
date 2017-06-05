package com.falcon.cms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PaperAuthor.
 */
@Entity
@Table(name = "paper_author")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaperAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Paper paper;

    @ManyToOne
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paper getPaper() {
        return paper;
    }

    public PaperAuthor paper(Paper paper) {
        this.paper = paper;
        return this;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Author getAuthor() {
        return author;
    }

    public PaperAuthor author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaperAuthor paperAuthor = (PaperAuthor) o;
        if (paperAuthor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paperAuthor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaperAuthor{" +
            "id=" + getId() +
            "}";
    }
}
