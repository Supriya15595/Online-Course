package com.course.online.service;

import org.springframework.stereotype.Component;

import com.course.online.model.CourseItem;

@Component
public interface CourseItemService {
	
	public CourseItem addCourseItem(CourseItem courseItem);

	public CourseItem findCourseItem(Integer id);

	public Iterable<CourseItem> findAllCourseItems();
	
	public Iterable<CourseItem> listCourseItemsOfCourse(Integer courseId);
}
