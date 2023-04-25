package com.app.productservice.core;

import com.app.productservice.core.security.SecurityUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public class BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, length = 36)
    private String id;


    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "created_user", nullable = false)
    private String createdUser;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_user")
    private String updatedUser;

    @PrePersist
    public void prePersist() {
        this.setCreatedDate(Instant.now());
        this.setCreatedUser(SecurityUtil.getCurrentUser());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedDate(Instant.now());
        this.setUpdatedUser(SecurityUtil.getCurrentUser());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    @Override
    public String toString() {
        return "BaseDomain{" +
            "id='" + id + '\'' +
            ", createdDate=" + createdDate +
            ", createdUser='" + createdUser + '\'' +
            ", updatedDate=" + updatedDate +
            ", updatedUser='" + updatedUser + '\'' +
            '}';
    }
}

