package com.course.online.service;

import org.springframework.stereotype.Component;

import com.course.online.model.Enrollment;

@Component
public interface EnrollmentService {
	
	public Enrollment enrollMember(Enrollment enrollment);
	
	public Enrollment findEnrolledMember(Integer id);

	public Iterable<Enrollment> listOfEnrolledMembers();

	public Iterable<Enrollment> findEnrolledMemberByMemberId(Integer memberId);
}
