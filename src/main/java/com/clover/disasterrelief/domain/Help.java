package com.clover.disasterrelief.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Help.
 */
@Document(collection = "help")
public class Help implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("first_name")
    private String firstName;

    @NotNull
    @Field("last_name")
    private String lastName;

    @Field("mobile_no")
    private String mobileNo;

    @DBRef
    @Field("helps")
    @DBRef
    @Field("helps")
    private User helps;

    @DBRef
    @Field("helps")
    @DBRef
    @Field("helps")
    @JsonIgnoreProperties(value = { "assistance" }, allowSetters = true)
    private Assistance helps;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Help id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Help firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Help lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Help mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public User getHelps() {
        return this.helps;
    }

    public void setHelps(User user) {
        this.helps = user;
    }

    public Help helps(User user) {
        this.setHelps(user);
        return this;
    }

    public Assistance getHelps() {
        return this.helps;
    }

    public void setHelps(Assistance assistance) {
        this.helps = assistance;
    }

    public Help helps(Assistance assistance) {
        this.setHelps(assistance);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Help)) {
            return false;
        }
        return id != null && id.equals(((Help) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Help{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            "}";
    }
}
