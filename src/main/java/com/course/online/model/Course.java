package com.course.online.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.course.online.util.CourseStatus;


@Entity
@Table
public class Course {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="NAME")
	private String name;

	@Column(name="language")
	private String language;

	@Column(name="ratings")
	private int ratings;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = " MEMBERID")
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private CourseStatus status;

	@Column(name="createdon")
	private Date createdOn;
	

	public CourseStatus getStatus() {
		return status;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member user) {
		this.member = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

}
