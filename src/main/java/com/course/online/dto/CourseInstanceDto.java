package com.course.online.dto;

import java.util.Date;

import com.course.online.model.Course;
import com.course.online.util.CourseInstanceStatus;

public class CourseInstanceDto {

	private Integer id;
	private Date startdate;
	private Date endDate;
	private Course course;
	private Date createdOn;
	private CourseInstanceStatus status;
	private Integer courseId;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public CourseInstanceStatus getStatus() {
		return status;
	}

	public void setStatus(CourseInstanceStatus status) {
		this.status = status;
	}

}
