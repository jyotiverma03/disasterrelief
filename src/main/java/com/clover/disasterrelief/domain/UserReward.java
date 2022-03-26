package com.clover.disasterrelief.domain;

import com.clover.disasterrelief.domain.enumeration.BadgeLevel;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A UserReward.
 */
@Document(collection = "user_reward")
public class UserReward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("rating")
    private Integer rating;

    @Field("user_id")
    private String userId;

    @Field("badge_level")
    private BadgeLevel badgeLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public UserReward id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRating() {
        return this.rating;
    }

    public UserReward rating(Integer rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return this.userId;
    }

    public UserReward userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BadgeLevel getBadgeLevel() {
        return this.badgeLevel;
    }

    public UserReward badgeLevel(BadgeLevel badgeLevel) {
        this.setBadgeLevel(badgeLevel);
        return this;
    }

    public void setBadgeLevel(BadgeLevel badgeLevel) {
        this.badgeLevel = badgeLevel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserReward)) {
            return false;
        }
        return id != null && id.equals(((UserReward) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserReward{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", userId='" + getUserId() + "'" +
            ", badgeLevel='" + getBadgeLevel() + "'" +
            "}";
    }
}
