package com.firstversion.musicmanager.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {
//    @Column
//    @CreatedBy
//    private String createBy;
    @Column
    @CreatedDate
    private Date createDate;
//    @Column
//    @LastModifiedBy
//    private String modifiedBy;
    @Column
    @LastModifiedDate
    private Date modifiedDate;
}
