package com.mbti.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity{
	//Entity 클래스는 테이블이 생성된다
	// @Mappedsuperclass : entity 클래스의 부모이므로 테이블을 생성하지 않는다.
	// 누가 생성했는지 (Id email)
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    
	// 누가 생성했는지 (Id email)
    @LastModifiedBy
    private String modifiedBy;

	public void setCreatedBy(String email) {
		this.createdBy = email;
	}

}
