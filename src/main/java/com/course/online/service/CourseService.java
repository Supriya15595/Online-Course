package com.course.online.service;

import org.springframework.stereotype.Component;

import com.course.online.model.Course;

@Component
public interface CourseService {

	public Course addCourse(Course course);
	
	public Course findCourseById(int id);
	
	public Iterable<Course> findAllCourses();
	
	public Course deleteCourse(Integer id);
	
	public Course updateStatusToActive(Integer id);
}
