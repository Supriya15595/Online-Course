package com.course.online.service;

import org.springframework.data.repository.CrudRepository;

import com.course.online.model.Course;

public interface CourseDao extends CrudRepository<Course,Integer> {

}
