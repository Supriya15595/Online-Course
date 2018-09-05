package com.course.online.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.course.online.model.CourseInstance;
import com.course.online.model.Enrollment;
import com.course.online.model.Member;
import com.course.online.service.CourseInstanceService;
import com.course.online.service.EnrollmentService;
import com.course.online.service.MemberService;
import com.course.online.util.CourseInstanceStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentControllerUT {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private CourseInstanceService courseInstanceService;

	@MockBean
	private EnrollmentService enrollmentService;

	@Test
	public void testEnrollMemberMethodWillReturnJsonValue() throws Exception {
		when(memberService.findMember(Mockito.anyInt())).thenReturn(getMemberObject());

		when(courseInstanceService.findCourseInstanceById(Mockito.anyInt())).thenReturn(getCourseInstanceObject());

		when(enrollmentService.enrollMember(Mockito.any())).thenReturn(getEnrolledObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollment").accept(MediaType.APPLICATION_JSON)
				.content("{\n" + "\t\"id\":\"1\",\n" +
						"\t\"memberId\":\"1\",\n" + 
						"\t\"courseInstanceId\":\"1\"\n" +
						"}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals("{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":null},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}", result.getResponse().getContentAsString());
	}

	@Test
	public void testFindEnrolledMemberByIdWillReturnJsonValue() throws Exception {
		when(enrollmentService.findEnrolledMember(Mockito.anyInt())).thenReturn(getEnrolledObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollment/1");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(
				"{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":null},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testListOfEnrolledMembersWillReturnMultipleJsonValues() throws Exception {
		when(enrollmentService.listOfEnrolledMembers()).thenReturn(getListOfEnrolledMembers());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollment/list");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("[{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":null},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null},{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":null},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}]", result.getResponse().getContentAsString());
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

	private CourseInstance getCourseInstanceObject() {

		CourseInstance courseInstance = new CourseInstance();

		courseInstance.setId(1);
		courseInstance.setStatus(CourseInstanceStatus.Active);

		return courseInstance;
	}

	private Enrollment getEnrolledObject() {
		Enrollment enrollment = new Enrollment();
		enrollment.setId(1);
		enrollment.setMember(getMemberObject());

		return enrollment;
	}

	private Member getMemberObject() {
		Member member = new Member();
		member.setId(1);
		member.setUserName("Ganga");
		member.setPassword("1234");
		member.setType("student");

		return member;
	}

}
