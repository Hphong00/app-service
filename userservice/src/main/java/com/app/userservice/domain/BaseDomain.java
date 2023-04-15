package com.app.userservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author
 */
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

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

//    @PrePersist
//    public void prePersist() {
//        this.setCreatedDate(Instant.now());
//        this.setCreatedUser(StringUtils.isNotEmpty(SecurityUtil.getCurrentUserLogin().get()) ? SecurityUtil.getCurrentUserLogin().get() : "SYSTEM");
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.setUpdatedDate(Instant.now());
//        this.setCreatedUser(StringUtils.isNotEmpty(SecurityUtil.getCurrentUserLogin().get()) ? SecurityUtil.getCurrentUserLogin().get() : "SYSTEM");
//    }
}

