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

import com.course.online.exception.CourseInstanceNotActiveException;
import com.course.online.model.CourseInstance;
import com.course.online.model.Enrollment;
import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.service.CourseInstanceService;
import com.course.online.service.EnrollmentService;
import com.course.online.service.MemberService;
import com.course.online.util.CourseInstanceStatus;
import com.course.online.util.MemberStatus;

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
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(memberService.findMember(Mockito.anyInt())).thenReturn(getMemberObject());

		when(courseInstanceService.findCourseInstanceById(Mockito.anyInt())).thenReturn(getCourseInstanceObject());

		when(enrollmentService.enrollMember(Mockito.any())).thenReturn(getEnrolledObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollment").header("auth-token", "21i1j217as")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\n" + "\t\"id\":\"1\",\n" +
						"\t\"memberId\":\"1\",\n" + 
						"\t\"courseInstanceId\":\"1\"\n" +
						"}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals("{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":\"Active\"},\"courseInstance\":{\"id\":1,\"startdate\":null,\"endDate\":null,\"course\":null,\"createdOn\":null,\"status\":\"Active\"},\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}", result.getResponse().getContentAsString());
	}



	@Test
	public void testFindEnrolledMemberByIdWillReturnJsonValue() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(enrollmentService.findEnrolledMember(Mockito.anyInt())).thenReturn(getEnrolledObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollment/1").header("auth-token", "21i1j217as");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(
				"{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":\"Active\"},\"courseInstance\":{\"id\":1,\"startdate\":null,\"endDate\":null,\"course\":null,\"createdOn\":null,\"status\":\"Active\"},\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testListOfEnrolledMembersWillReturnMultipleJsonValues() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(enrollmentService.listOfEnrolledMembers()).thenReturn(getListOfEnrolledMembers());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollment/list").header("auth-token", "21i1j217as");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("[{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":\"Active\"},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null},{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":\"Active\"},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}]", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testFindEnrolledMemberByMemberIdWillReturnMultipleJsonValue() throws Exception
	{
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(enrollmentService.findEnrolledMemberByMemberId(Mockito.anyInt())).thenReturn(getListOfEnrolledMembers());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollment/member/1").header("auth-token", "21i1j217as");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("[{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":\"Active\"},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null},{\"id\":1,\"member\":{\"id\":1,\"userName\":\"Ganga\",\"password\":\"1234\",\"email\":null,\"type\":\"student\",\"createdOn\":null,\"status\":\"Active\"},\"courseInstance\":null,\"createdOn\":null,\"memberId\":null,\"courseInstanceId\":null}]", result.getResponse().getContentAsString());
	}
	
	private Login getLoginObject() {
		Login login = new Login();

		login.setId(1);
		login.setToken("21i1j217as");
		login.setMember(getAddedMember());

		return login;
	}

	private Member getAddedMember() {
		Member member = new Member();
		member.setId(100);
		member.setUserName("aaa1@gmail");
		member.setPassword("letmein1");
		return member;
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
		enrollment.setCourseInstance(getCourseInstanceObject());

		return enrollment;
	}

	private Member getMemberObject() {
		Member member = new Member();
		member.setId(1);
		member.setUserName("Ganga");
		member.setPassword("1234");
		member.setType("student");
		member.setStatus(MemberStatus.Active);

		return member;
	}

}
