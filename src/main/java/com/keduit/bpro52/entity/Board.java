package com.keduit.bpro52.entity;

//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="board")
@ToString(exclude = "writer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length=100, nullable = false)
	private String title;
	
	@Column(length=500, nullable = false)
	private String content;
	
//	@Column(length=50, nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)      // Lazy로딩으로 지정(더 빠르게 검색)
	private Member writer;
	

	public void change(String title, String content) {
		
		this.title = title;
		this.content = content;
	}
}
