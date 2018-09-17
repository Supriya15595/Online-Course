package com.course.online.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
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

import com.course.online.model.Course;
import com.course.online.model.CourseInstance;
import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.service.CourseInstanceService;
import com.course.online.service.CourseService;
import com.course.online.service.MemberService;
import com.course.online.util.CourseInstanceStatus;
import com.course.online.util.CourseStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseInstanceControllerUT {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CourseService courseService;

	@MockBean
	private CourseInstanceService courseInstanceService;

	@MockBean
	private MemberService memberService;

	@Test
	public void testAddCourseInstanceMethodWillReturnJsonValue() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());

		when(courseService.findCourseById(Mockito.anyInt())).thenReturn(getCourseObject());

		when(courseInstanceService.addCourseInstance(Mockito.any())).thenReturn(getCourseInstanceObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/courseInstance")
				.header("auth-token", "21i1j217as")
				.accept(MediaType.APPLICATION_JSON).content("{\n" +
                "\t\"id\":\"1\",\n" +
                "\t\"courseId\":\"1\",\n" +
                "\t\"status\":\"Active\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON);

		// Testing if method present in courseInstanceService was invoked atleast once
//		 verify(courseInstanceService).addCourseInstance(Mockito.any());

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals("{\"id\":1,\"startdate\":null,\"endDate\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":\"Active\",\"createdOn\":null},\"createdOn\":null,\"status\":\"Active\",\"courseId\":null}", result.getResponse().getContentAsString());
	}

	

	@Test
	public void testFindCourseInstanceMethodWillReturnJsonValue() throws Exception
	{
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(courseInstanceService.findCourseInstanceById(Mockito.anyInt())).thenReturn(getCourseInstanceObject());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courseInstance/1").header("auth-token", "21i1j217as");
		
		MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("{\"id\":1,\"startdate\":null,\"endDate\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":\"Active\",\"createdOn\":null},\"createdOn\":null,\"status\":\"Active\",\"courseId\":null}", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testListOfCourseInstanceMethodWillReturnMutipleJsonValues() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(courseInstanceService.listOfCourseInstances()).thenReturn(getListOfCourseInstnce());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courseInstance/list").header("auth-token", "21i1j217as");

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		verify(courseInstanceService).listOfCourseInstances();

		assertEquals(
				"[{\"id\":1,\"startdate\":null,\"endDate\":null,\"course\":null,\"createdOn\":null,\"status\":\"Active\",\"courseId\":null},{\"id\":2,\"startdate\":null,\"endDate\":null,\"course\":null,\"createdOn\":null,\"status\":\"Terminated\",\"courseId\":null}]",
				result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDeleteCourseInstanceMethodWillReturnsJsonValue() throws Exception
	{
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(courseInstanceService.deleteCourseInstance(Mockito.anyInt())).thenReturn(getCourseInstanceObject());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/courseInstance/delete/1").header("auth-token", "21i1j217as");
		
		MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("{\"id\":1,\"startdate\":null,\"endDate\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":\"Active\",\"createdOn\":null},\"createdOn\":null,\"status\":\"Active\",\"courseId\":null}", mvcResult.getResponse().getContentAsString());
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



	private Iterable<CourseInstance> getListOfCourseInstnce() {

		List<CourseInstance> courseInstanceList = new ArrayList<CourseInstance>();

		CourseInstance courseInstance1 = new CourseInstance();
		courseInstance1.setId(1);
		courseInstance1.setStatus(CourseInstanceStatus.Active);

		CourseInstance courseInstance2 = new CourseInstance();
		courseInstance2.setId(2);
		courseInstance2.setStatus(CourseInstanceStatus.Terminated);

		courseInstanceList.add(courseInstance1);
		courseInstanceList.add(courseInstance2);

		return courseInstanceList;
	}

	private CourseInstance getCourseInstanceObject() {

		CourseInstance courseInstance = new CourseInstance();

		courseInstance.setId(1);
		courseInstance.setStatus(CourseInstanceStatus.Active);
		courseInstance.setCourse(getCourseObject());
		return courseInstance;
	}

	private Course getCourseObject() {

		Course course = new Course();

		course.setId(1);
		course.setName("Java");
		course.setLanguage("English");
		course.setStatus(CourseStatus.Active);
		
		return course;
	}

}
