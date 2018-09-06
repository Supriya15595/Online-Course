package com.course.online.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.CourseDao;
import com.course.online.dao.CourseInstanceDao;
import com.course.online.model.Course;
import com.course.online.model.CourseInstance;
import com.course.online.util.CourseInstanceStatus;
import com.course.online.util.CourseStatus;

@Component
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private CourseInstanceService courseInstanceService;
	
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	
	@Override
	public Course addCourse(Course course) {
		Date currentDate = new Date();
		course.setCreatedOn(currentDate);
		course.setStatus(CourseStatus.New);
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

	@Override
	public Course deleteCourse(Integer id) {

		Course course = courseDao.findById(id).get();
		
		Iterable<CourseInstance> courseInstanceList = courseInstanceService.findCourseInstancesOfCourse(id);
		
		course.setStatus(CourseStatus.Terminated);
		
		for (CourseInstance courseInstance : courseInstanceList) {
			courseInstance.setStatus(CourseInstanceStatus.Terminated);
			courseInstanceDao.save(courseInstance);
		}
		
		courseDao.save(course);
		
		return course;
	}

	@Override
	public Course updateStatusToActive(Integer id) {
		
		Course course = courseDao.findById(id).get();
		
		course.setStatus(CourseStatus.Active);
		
		courseDao.save(course);
		
		return course;
	}
	
	
}
