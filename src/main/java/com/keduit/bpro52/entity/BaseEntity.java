package com.keduit.bpro52.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass // 상속한 엔티티들을 아래 필드 컬럼을 인식하게끔
@EntityListeners(value = {AuditingEntityListener.class}) // 자동으로 값 매핑기능 추가
abstract class BaseEntity {

	// 날짜를 따로 추가 Bpro51Application에 에노테이션 추가 + webserver에 extends함
	
	
	@CreatedDate
	@Column(name="regdate", updatable = false)
	private LocalDateTime regDate;
	
	@LastModifiedDate
	@Column(name="updatedate")
	private LocalDateTime updateDate;
	

}
