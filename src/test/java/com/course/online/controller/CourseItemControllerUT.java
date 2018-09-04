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
import com.course.online.service.CourseItemService;
import com.course.online.service.CourseService;

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

	@Test
	public void testAddCourseItemMethodWillReturnJsonValue() throws Exception {

		// Test id findCourse is working
		when(courseService.findCourseById(Mockito.anyInt())).thenReturn(getCourseObject());

		when(courseItemService.addCourseItem(Mockito.any())).thenReturn(getCourseItemObject());

		// Mocking the request
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addCourseItem/1")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\n" + "\t\"id\":\"1\",\n" + "\t\"name\":\"Introduction to Hibernate\",\n"
						+ "\t\"description\":\"Why Hibernate?\",\n" + "\t\"type\":\"video\"\n" + "}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertEquals("{\"id\":1,\"name\":\"Introduction to java\",\"description\":null,\"type\":null,\"content\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null},\"createdOn\":null}", result.getResponse().getContentAsString());

	}
	
	@Test
	public void testFindCourseItemMethodWillReturnSingleJsonObject() throws Exception
	{
		when(courseItemService.findCourseItem(Mockito.anyInt())).thenReturn(getCourseItemObject());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/findCourseItem/1");
		
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("{\"id\":1,\"name\":\"Introduction to java\",\"description\":null,\"type\":null,\"content\":null,\"course\":{\"id\":1,\"name\":\"Java\",\"language\":\"English\",\"ratings\":0,\"member\":null,\"status\":null,\"createdOn\":null},\"createdOn\":null}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testFindAllCoursesMethodWillReturnMultipleJsonObjects() throws Exception 
	{
		when(courseItemService.findAllCourseItems()).thenReturn(getListOfCourseItems());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courseItem/all");
		
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		assertEquals("[{\"id\":1,\"name\":\"Introduction to Java\",\"description\":null,\"type\":\"video\",\"content\":null,\"course\":null,\"createdOn\":null},{\"id\":2,\"name\":\"Introduction to JDBC\",\"description\":null,\"type\":\"pdf\",\"content\":null,\"course\":null,\"createdOn\":null}]", result.getResponse().getContentAsString());
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
