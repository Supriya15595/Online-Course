package com.course.online.controller;

import static org.junit.Assert.assertEquals;
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
import com.course.online.model.Member;
import com.course.online.service.CourseService;
import com.course.online.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerUT {

	@Autowired
	private MockMvc mvc;

	@MockBean
	CourseService courseService;

	@MockBean
	MemberService memberService;
	
	private Member getMemberObject() {

		Member member = new Member();

		member.setId(10);
		member.setUserName("aaa");
		member.setType("tutor");

		return member;
	}

	@Test
	public void testAddCourseMethodWillReturnJsonValue() throws Exception {

		// Test if findMember is working
		when(memberService.findMember(10)).thenReturn(getMemberObject());
		
		Course course = new Course();
		
		course.setId(10);
		course.setLanguage("English");
		course.setName("java");
		course.setRatings(5);
		course.setMember(getMemberObject());

		// Test if courseService autowiring is working
		when(courseService.addCourse(Mockito.any())).thenReturn(course);

		// Mocking the request
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/course").accept(MediaType.APPLICATION_JSON).content("{\n" +
                "\t\"id\":\"10\",\n" +
                "\t\"name\":\"java\",\n" +
                "\t\"language\":\"English\",\n" +
		"\t\"ratings\":\"5\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals("{\"id\":10,\"name\":\"java\",\"language\":\"English\",\"ratings\":5,\"member\":{\"id\":10,\"userName\":\"aaa\",\"password\":null,\"email\":null,\"type\":\"tutor\",\"createdOn\":null,\"status\":null},\"status\":null,\"createdOn\":null,\"memberId\":null}", result.getResponse().getContentAsString());

	}
	
	@Test
	public void testFindCourseByIdMethodWillReturnJsonValue() throws Exception
	{
		when(courseService.findCourseById(Mockito.anyInt())).thenReturn(getAddedCourse());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/10");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals("{\"id\":10,\"name\":\"java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null,\"memberId\":null}", result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testListOfCourseMethodWillReturnMultipleJsonValue() throws Exception
	{
		when(courseService.findAllCourses()).thenReturn(getListOfCourses());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/list");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals("[{\"id\":1,\"name\":\"Java\",\"language\":null,\"ratings\":0,\"member\":{\"id\":10,\"userName\":\"aaa\",\"password\":null,\"email\":null,\"type\":\"tutor\",\"createdOn\":null,\"status\":null},\"status\":null,\"createdOn\":null,\"memberId\":null},{\"id\":2,\"name\":\"J2ee\",\"language\":null,\"ratings\":0,\"member\":{\"id\":10,\"userName\":\"aaa\",\"password\":null,\"email\":null,\"type\":\"tutor\",\"createdOn\":null,\"status\":null},\"status\":null,\"createdOn\":null,\"memberId\":null}]", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDeleteCourseMethodWillReturnJsonValue() throws Exception
	{
		when(courseService.deleteCourse(Mockito.anyInt())).thenReturn(getAddedCourse());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/course/delete/1");
		MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
		assertEquals("{\"id\":10,\"name\":\"java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null,\"memberId\":null}", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testUpdateStatusToActiveWillReturnJsonValue() throws Exception
	{
		when(courseService.updateStatusToActive(Mockito.anyInt())).thenReturn(getAddedCourse());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/course/update/1");
		MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
		assertEquals("{\"id\":10,\"name\":\"java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null,\"memberId\":null}", mvcResult.getResponse().getContentAsString());
	}

	private Iterable<Course> getListOfCourses() {
		
		List<Course> listOfCourses = new ArrayList<Course>();
		Course course1 = new Course();
		course1.setId(1);
		course1.setName("Java");
		course1.setMember(getMemberObject());
		
		Course course2 = new Course();
		course2.setId(2);
		course2.setName("J2ee");
		course2.setMember(getMemberObject());
		
		listOfCourses.add(course1);
		listOfCourses.add(course2);
		
		return listOfCourses;
	}

	private Course getAddedCourse() {
		
		Course course = new Course();
		
		course.setId(10);
		course.setLanguage("English");
		course.setName("java");
		return course;
	}
	

}
