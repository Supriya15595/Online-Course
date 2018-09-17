package com.course.online.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.EnrollmentDao;
import com.course.online.model.Enrollment;

@Component
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	private EnrollmentDao enrollmentDao;

	@Override
	public Enrollment enrollMember(Enrollment enrollment) {
		Date currentDate = new Date();
		enrollment.setCreatedOn(currentDate);

		enrollment = enrollmentDao.save(enrollment);
		return enrollment;
	}

	@Override
	public Enrollment findEnrolledMember(Integer id) {

		Enrollment enrollment = enrollmentDao.findById(id).get();
		return enrollment;
	}

	@Override
	public Iterable<Enrollment> listOfEnrolledMembers() {
		Iterable<Enrollment> enrollmentList = enrollmentDao.findAll();
		return enrollmentList;
	}

	@Override
	public Iterable<Enrollment> findEnrolledMemberByMemberId(Integer memberId) {
		Iterable<Enrollment> enrollmentList = enrollmentDao.findByMemberId(memberId);
		return enrollmentList;
	}

}
