package com.course.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.CourseInstanceDao;
import com.course.online.model.CourseInstance;

@Component
public class CourseInstanceServiceImpl implements CourseInstanceService {
	
	@Autowired
	private CourseInstanceDao courseInstanceDao;

	@Override
	public CourseInstance addCourseInstance(CourseInstance courseInstance) {
		
		courseInstance = courseInstanceDao.save(courseInstance);
		return courseInstance;
	}

	@Override
	public Iterable<CourseInstance> listOfCourseInstances() {
		Iterable<CourseInstance> courseInstanceList = courseInstanceDao.findAll();
		return courseInstanceList;
	}

}
