package com.clover.disasterrelief.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A BaseModel.
 */
@Document(collection = "base_model")
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("create")
    private Long create;

    @Field("modified")
    private Long modified;

    @Field("deleted")
    private Long deleted;

    @Field("updated")
    private Boolean updated;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public BaseModel id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreate() {
        return this.create;
    }

    public BaseModel create(Long create) {
        this.setCreate(create);
        return this;
    }

    public void setCreate(Long create) {
        this.create = create;
    }

    public Long getModified() {
        return this.modified;
    }

    public BaseModel modified(Long modified) {
        this.setModified(modified);
        return this;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public Long getDeleted() {
        return this.deleted;
    }

    public BaseModel deleted(Long deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public Boolean getUpdated() {
        return this.updated;
    }

    public BaseModel updated(Boolean updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseModel)) {
            return false;
        }
        return id != null && id.equals(((BaseModel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BaseModel{" +
            "id=" + getId() +
            ", create=" + getCreate() +
            ", modified=" + getModified() +
            ", deleted=" + getDeleted() +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
