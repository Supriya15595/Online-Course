package com.course.online.dao;

import org.springframework.data.repository.CrudRepository;

import com.course.online.dto.EnrollmentDto;
import com.course.online.model.Enrollment;

public interface EnrollmentDao extends CrudRepository<Enrollment, Integer> {
	
	public Iterable<Enrollment> findByMemberId(Integer memberId);
}
