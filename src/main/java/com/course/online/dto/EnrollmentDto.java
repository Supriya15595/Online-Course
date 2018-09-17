package com.course.online.dto;

import java.util.Date;

import com.course.online.model.CourseInstance;
import com.course.online.model.Member;

public class EnrollmentDto {

	private Integer id;
	private Member member;
	private CourseInstance courseInstance;
	private Date createdOn;
	
	
	private Integer courseInstanceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public CourseInstance getCourseInstance() {
		return courseInstance;
	}

	public void setCourseInstance(CourseInstance courseInstance) {
		this.courseInstance = courseInstance;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getCourseInstanceId() {
		return courseInstanceId;
	}

	public void setCourseInstanceId(Integer courseInstanceId) {
		this.courseInstanceId = courseInstanceId;
	}
}
