package com.course.online.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.CourseItemDao;
import com.course.online.model.CourseItem;

@Component
public class CourseItemServiceImpl implements CourseItemService {

	@Autowired
	CourseItemDao courseItemDao;

	@Override
	public CourseItem addCourseItem(CourseItem courseItem) {
		Date currentDate = new Date();
		courseItem.setCreatedOn(currentDate);
		courseItem = courseItemDao.save(courseItem);
		return courseItem;
	}

	@Override
	public CourseItem findCourseItem(Integer id) {

		CourseItem courseItem = courseItemDao.findById(id).get();
		return courseItem;
	}

	@Override
	public Iterable<CourseItem> findAllCourseItems() {

		Iterable<CourseItem> courseItemsList = courseItemDao.findAll();
		return courseItemsList;
	}

	@Override
	public Iterable<CourseItem> listCourseItemsOfCourse(Integer courseId) {
		
		Iterable<CourseItem> courseItemList = courseItemDao.findByCourseId(courseId);
		return courseItemList;
	}

}
