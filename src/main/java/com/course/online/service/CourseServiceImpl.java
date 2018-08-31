package com.course.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.CourseDao;
import com.course.online.model.Course;

@Component
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseDao courseDao;
	
	@Override
	public Course addCourse(Course course) {
		course = courseDao.save(course);
		return course;
	}

	@Override
	public Course findCourseById(int id) {
		Course course = courseDao.findById(id).get();
		return course;
	}

	@Override
	public Iterable<Course> findAllCourses() {
		
		Iterable<Course> listOfCourse = courseDao.findAll();
		return listOfCourse;
	}
	
	
}
