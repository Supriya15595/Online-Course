package com.course.online.service;

import com.course.online.model.CourseInstance;

public interface CourseInstanceService {

	public CourseInstance addCourseInstance(CourseInstance courseInstance);

	public Iterable<CourseInstance> listOfCourseInstances();

	public CourseInstance findCourseInstanceById(int id);

}
