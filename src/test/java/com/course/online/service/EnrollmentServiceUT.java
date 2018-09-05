package com.course.online.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.course.online.model.Enrollment;
import com.course.online.model.Member;

@RunWith(SpringRunner.class)
public class EnrollmentServiceUT {
	
	@TestConfiguration
	static class DefaultEnrollmentServiceTestContextConfiguration {

		@Bean
		public EnrollmentService enrollmentService() {
			return new EnrollmentServiceImpl();
		}
	}

	@Autowired
	private EnrollmentService enrollmentService;
	
	@MockBean
	private EnrollmentDao enrollmentDao;
	
	@Test
	public void testAddEnrollmentMethodWillReturnAddedEnrollmentObject() {
		when(enrollmentDao.save(Mockito.any())).thenReturn(getEnrollmentObject());
		
		Enrollment enrolledMember = enrollmentService.enrollMember(getEnrollmentObject());
		
		assertNotNull(enrolledMember.getId());
	}
	
	@Test
	public void testFindEnrolledMemberMethodWillReturnEnrollmentObject()
	{
		when(enrollmentDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getEnrollmentObject()));
		
		Enrollment enrollment = enrollmentService.findEnrolledMember(Mockito.anyInt());
		
		assertNotNull(enrollment.getId());
	}
	
	@Test
	public void testListOfEnrolledMembersMethodWillReturnEnrolledMembersList()
	{
		when(enrollmentDao.findAll()).thenReturn(getListOfEnrolledMembers());
		
		Iterable<Enrollment> enrollmentList = enrollmentService.listOfEnrolledMembers();
		
		for (Enrollment enrollment : enrollmentList) {
			assertNotNull(enrollment.getId());
		}
	}

	private Iterable<Enrollment> getListOfEnrolledMembers() {

		List<Enrollment> enrollmentList = new ArrayList<Enrollment>();
		
		Enrollment enrollment1 = new Enrollment();
		enrollment1.setId(1);
		enrollment1.setMember(getMemberObject());
		
		Enrollment enrollment2 = new Enrollment();
		enrollment2.setId(1);
		enrollment2.setMember(getMemberObject());
		
		enrollmentList.add(enrollment1);
		enrollmentList.add(enrollment2);
		
		return enrollmentList;
	}

	private Member getMemberObject() {
		
		Member member = new Member();
		member.setUserName("Ganga");
		member.setPassword("1234");
		member.setType("student");
		
		return member;
	}

	private Enrollment getEnrollmentObject() {
		
		Enrollment enrollment = new Enrollment();
		
		enrollment.setId(1);
		
		enrollment.setMember(getMemberObject());
		
		return enrollment;
	}

}
