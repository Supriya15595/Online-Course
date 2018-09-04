package com.course.online.service;

import static org.junit.Assert.assertNotNull;
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

import com.course.online.dao.CourseItemDao;
import com.course.online.model.CourseItem;

@RunWith(SpringRunner.class)
public class CourseItemServiceUT {

	@TestConfiguration
	static class DefaultCourseItemServiceTestContextConfiguration {

		@Bean
		public CourseItemService courseItemService() {
			return new CourseItemServiceImpl();
		}
	}

	@Autowired
	private CourseItemService courseItemService;

	@MockBean
	private CourseItemDao courseItemDao;
	
	private CourseItem getCourseItem() {

		CourseItem courseItem = new CourseItem();
		courseItem.setId(1);
		courseItem.setName("Introduction to OOPS");
		courseItem.setDescription("Inheritance,Encapsulation,Polymorphism and Abstraction OOPS cocepts explained");

		return courseItem;
	}

	@Test
	public void testAddCourseItemMethodWillReturnCourseItemObject()
	{
	
		when(courseItemDao.save(Mockito.any())).thenReturn(getCourseItem());
		
		CourseItem addedCourseItem = courseItemService.addCourseItem(getCourseItem());
		
		assertNotNull(addedCourseItem.getId());
	}
	
	@Test
	public void testFindCourseMethodWillReturnCourseItemObject()
	{
		when(courseItemDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getCourseItem()));
		CourseItem courseItem = courseItemService.findCourseItem(Mockito.anyInt());
		assertNotNull(courseItem.getId());
	}
	
	@Test
	public void testFindAllCoursesMethodWillReturnListOfCourseItems()
	{
		when(courseItemDao.findAll()).thenReturn(getListOfCourseItems());
		Iterable<CourseItem> listOfCourseItems = courseItemService.findAllCourseItems();
		for (CourseItem courseItem : listOfCourseItems) {
			assertNotNull(courseItem.getId());
		}
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
}
