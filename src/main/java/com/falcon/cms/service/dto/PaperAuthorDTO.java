package com.falcon.cms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PaperAuthor entity.
 */
public class PaperAuthorDTO implements Serializable {

    private Long id;

    private Long paperId;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaperAuthorDTO paperAuthorDTO = (PaperAuthorDTO) o;
        if(paperAuthorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paperAuthorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaperAuthorDTO{" +
            "id=" + getId() +
            "}";
    }
}
