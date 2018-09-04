package com.course.online.dao;

import org.springframework.data.repository.CrudRepository;

import com.course.online.model.CourseItem;

public interface CourseItemDao extends CrudRepository<CourseItem, Integer> {

}
