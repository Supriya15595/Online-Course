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

import com.course.online.dao.CourseDao;
import com.course.online.model.Course;

@RunWith(SpringRunner.class)
public class CourseServiceUT {

	@TestConfiguration
	static class DefaultCourseServiceTestContextConfiguration {

		@Bean
		public CourseService courseService() {
			return new CourseServiceImpl();
		}
	}

	@Autowired
	CourseService courseService;

	@MockBean
	CourseDao courseDao;

	private Course getCourseObject() {

		Course course = new Course();
		course.setId(1);
		course.setName("java");
		course.setRatings(5);

		return course;
	}

	@Test
	public void testAddCourseMethodWillReturnCourseObject() {

		Course course = new Course();
		course.setId(1);
		course.setName("java");
		course.setRatings(5);

		when(courseDao.save(course)).thenReturn(getCourseObject());

		Course addedCourse = courseService.addCourse(course);

		assertNotNull(addedCourse.getId());
	}

	@Test
	public void testFindCourseByIdWillReturnCourseObject() {

		when(courseDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getCourseObject()));

		Course course = courseService.findCourseById(Mockito.anyInt());

		assertNotNull(course.getId());
	}

	@Test
	public void testFindAllCourses() {
		when(courseDao.findAll()).thenReturn(getListOfCourses());
		Iterable<Course> listOfCourses = courseService.findAllCourses();
		for (Course course : listOfCourses) {
			assertNotNull(course.getId());
		}
	}

	private Iterable<Course> getListOfCourses() {

		List<Course> listOfCourses = new ArrayList<Course>();
		
		Course course1 = new Course();
		course1.setId(1);
		course1.setName("Java");
		course1.setLanguage("Kannadda");
		
		Course course2 = new Course();
		course2.setId(1);
		course2.setName("Java");
		course2.setLanguage("Kannadda");
		
		listOfCourses.add(course1);
		listOfCourses.add(course2);
		
		return listOfCourses;
	}
}
