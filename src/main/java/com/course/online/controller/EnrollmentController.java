package com.course.online.controller;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.online.AppContext;
import com.course.online.dto.EnrollmentDto;
import com.course.online.exception.CourseInstanceNotActiveException;
import com.course.online.model.CourseInstance;
import com.course.online.model.Enrollment;
import com.course.online.model.Member;
import com.course.online.service.CourseInstanceService;
import com.course.online.service.EnrollmentService;
import com.course.online.service.MemberService;
import com.course.online.util.EnrollmentBuilder;

@Controller
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CourseInstanceService courseInstanceService;

	@PostMapping("/enrollment")
	public @ResponseBody EnrollmentDto enrollMember(@RequestBody EnrollmentDto enrollmentDto) {
	
		Member member = AppContext.getMember();

		// Finding CourseInstance object mapped to be with Enrollment
		Integer courseInstanceId = enrollmentDto.getCourseInstanceId();
		CourseInstance courseInstance = courseInstanceService.findCourseInstanceById(courseInstanceId);

		if (courseInstance.getStatus().toString().equals("Active")) {
			// Enrolling Member and CourseInstance
			enrollmentDto.setCourseInstance(courseInstance);
			enrollmentDto.setMember(member);

			// Convert the EnrollmentDto to Enrollment entity
			Enrollment enrollment = EnrollmentBuilder.convert(enrollmentDto);

			// save the object
			enrollment = enrollmentService.enrollMember(enrollment);

			// Convert the Enrollment to EnrollmentDto entity
			enrollmentDto = EnrollmentBuilder.convert(enrollment);
		} else {
			throw new CourseInstanceNotActiveException("This Course Instance is Terminated");
		}

		return enrollmentDto;
	}

	@GetMapping("/enrollment/{id}")
	public @ResponseBody EnrollmentDto findEnrolledMemberById(@PathVariable Integer id) {
		Enrollment enrollment = enrollmentService.findEnrolledMember(id);

		// Convert Enrollment entity to Dto
		EnrollmentDto enrollmentDto = EnrollmentBuilder.convert(enrollment);

		return enrollmentDto;
	}

	@GetMapping("/enrollment/list")
	public @ResponseBody Iterable<EnrollmentDto> listOfEnrolledMembers() {
		Iterable<Enrollment> enrollmentList = enrollmentService.listOfEnrolledMembers();

		Iterable<EnrollmentDto> enrolledDtoList = EnrollmentBuilder.convert(enrollmentList);

		return enrolledDtoList;
	}

	@GetMapping("/enrollment/member/{memberId}")
	public @ResponseBody Iterable<EnrollmentDto> findEnrolledMemberByMemberId(@PathVariable Integer memberId) {
		Iterable<Enrollment> enrollmentList = enrollmentService.findEnrolledMemberByMemberId(memberId);

		return EnrollmentBuilder.convert(enrollmentList);
	}
}
