package com.course.online.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.CourseInstanceDao;
import com.course.online.model.CourseInstance;
import com.course.online.util.CourseInstanceStatus;

@Component
public class CourseInstanceServiceImpl implements CourseInstanceService {
	
	@Autowired
	private CourseInstanceDao courseInstanceDao;

	@Override
	public CourseInstance addCourseInstance(CourseInstance courseInstance) {
		Date currentDate = new Date();
		courseInstance.setCreatedOn(currentDate);
		courseInstance.setStatus(CourseInstanceStatus.Active);
		courseInstance = courseInstanceDao.save(courseInstance);
		return courseInstance;
	}

	@Override
	public Iterable<CourseInstance> listOfCourseInstances() {
		Iterable<CourseInstance> courseInstanceList = courseInstanceDao.findAll();
		return courseInstanceList;
	}

	@Override
	public CourseInstance findCourseInstanceById(int id) {
		CourseInstance courseInstance = courseInstanceDao.findById(id).get();
		return courseInstance;
	}

}
