package com.course.online.service;

import org.springframework.data.repository.CrudRepository;

import com.course.online.model.Enrollment;

public interface EnrollmentDao extends CrudRepository<Enrollment, Integer> {

}
