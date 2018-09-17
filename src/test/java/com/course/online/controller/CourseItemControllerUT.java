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

import com.course.online.model.Course;
import com.course.online.model.CourseItem;
import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.service.CourseItemService;
import com.course.online.service.CourseService;
import com.course.online.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseItemControllerUT {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CourseService courseService;

	@MockBean
	private CourseItemService courseItemService;
	
	@MockBean
	private MemberService memberService;

	@Test
	public void testAddCourseItemMethodWillReturnJsonValue() throws Exception {
		
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		// Test id findCourse is working
		when(courseService.findCourseById(Mockito.anyInt())).thenReturn(getCourseObject());

		when(courseItemService.addCourseItem(Mockito.any())).thenReturn(getCourseItemObject());

		// Mocking the request
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/courseItem")
				.header("auth-token", "21i1j217as")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\n" + "\t\"id\":\"1\",\n" + "\t\"name\":\"Introduction to Hibernate\",\n"
						+ "\t\"courseId\":\"1\",\n" + "\t\"type\":\"video\"\n" + "}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals(
				"{\"id\":1,\"name\":\"Introduction to java\",\"description\":null,\"type\":\"video\",\"content\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null},\"createdOn\":null,\"courseId\":null}",
				result.getResponse().getContentAsString());

	}

	

	@Test
	public void testFindCourseItemMethodWillReturnSingleJsonObject() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(courseItemService.findCourseItem(Mockito.anyInt())).thenReturn(getCourseItemObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courseItem/1")
										.header("auth-token", "21i1j217as");

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals(
				"{\"id\":1,\"name\":\"Introduction to java\",\"description\":null,\"type\":\"video\",\"content\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null},\"createdOn\":null,\"courseId\":null}",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testListOfCourseItemMethodWillReturnMultipleJsonObjects() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(courseItemService.findAllCourseItems()).thenReturn(getListOfCourseItems());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courseItem/list")
										.header("auth-token", "21i1j217as");

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals(
				"[{\"id\":1,\"name\":\"Introduction to Java\",\"description\":null,\"type\":\"video\",\"content\":null,\"course\":null,\"createdOn\":null,\"courseId\":null},{\"id\":2,\"name\":\"Introduction to JDBC\",\"description\":null,\"type\":\"pdf\",\"content\":null,\"course\":null,\"createdOn\":null,\"courseId\":null}]",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testListCourseItemsOfCourseMethodWillReturnMultipleJsonValue() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(courseItemService.listCourseItemsOfCourse(Mockito.anyInt())).thenReturn(getListOfCourseItems());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courseItem/course/1").header("auth-token", "21i1j217as");

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("[{\"id\":1,\"name\":\"Introduction to Java\",\"description\":null,\"type\":\"video\",\"content\":null,\"course\":null,\"createdOn\":null,\"courseId\":null},{\"id\":2,\"name\":\"Introduction to JDBC\",\"description\":null,\"type\":\"pdf\",\"content\":null,\"course\":null,\"createdOn\":null,\"courseId\":null}]", result.getResponse().getContentAsString());

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

	private Iterable<CourseItem> getListOfCourseItems() {
		List<CourseItem> courseItemList = new ArrayList<CourseItem>();

		CourseItem courseItem1 = new CourseItem();

		courseItem1.setId(1);
		courseItem1.setName("Introduction to Java");
		courseItem1.setType("video");

		CourseItem courseItem2 = new CourseItem();

		courseItem2.setId(2);
		courseItem2.setName("Introduction to JDBC");
		courseItem2.setType("pdf");

		courseItemList.add(courseItem1);
		courseItemList.add(courseItem2);

		return courseItemList;
	}

	private CourseItem getCourseItemObject() {
		CourseItem courseItem = new CourseItem();

		courseItem.setId(1);
		courseItem.setName("Introduction to java");
		courseItem.setType("video");
		courseItem.setCourse(getCourseObject());

		return courseItem;
	}

	private Course getCourseObject() {

		Course course = new Course();

		course.setId(1);
		course.setLanguage("English");
		course.setName("Java");

		return course;
	}

}
