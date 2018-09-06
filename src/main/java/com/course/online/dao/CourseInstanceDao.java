package com.course.online.dao;

import org.springframework.data.repository.CrudRepository;

import com.course.online.model.CourseInstance;

public interface CourseInstanceDao extends CrudRepository<CourseInstance, Integer> {
	
	public Iterable<CourseInstance> findByCourseId(Integer courseId);

}
