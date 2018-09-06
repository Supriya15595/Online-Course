package com.course.online.service;

import org.springframework.stereotype.Component;

import com.course.online.model.CourseInstance;

@Component
public interface CourseInstanceService {

	public CourseInstance addCourseInstance(CourseInstance courseInstance);

	public Iterable<CourseInstance> listOfCourseInstances();

	public CourseInstance findCourseInstanceById(int id);

	public CourseInstance deleteCourseInstance(int id);
	
	public Iterable<CourseInstance> findCourseInstancesOfCourse(int courseId);

}
