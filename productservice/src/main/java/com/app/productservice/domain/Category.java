package com.app.productservice.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Category id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Category createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return this.createdUser;
    }

    public Category createdUser(String createdUser) {
        this.setCreatedUser(createdUser);
        return this;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Category updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return this.updatedUser;
    }

    public Category updatedUser(String updatedUser) {
        this.setUpdatedUser(updatedUser);
        return this;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Category imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUser='" + getCreatedUser() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedUser='" + getUpdatedUser() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
