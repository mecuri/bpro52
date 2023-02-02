package com.keduit.bpro52.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="board")
@ToString(exclude = "writer")
public class Pr_table extends BaseEntity{
	
	// 테이블 생성
	@Id // primary key 생성
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length=50, nullable = false)
	private String title;
	
	@Column(length=200, nullable=false, updatable=false)
	private String content;
	
//	@Column(length = 20, nullable=false)
	@ManyToOne(fetch = FetchType.LAZY) //lazy 로딩으로 지정
	private Member writer;
	
	// ?
	public void change(String title, String content) {
		this.title = title;
		this.content = content;
	} 
	
	
}
