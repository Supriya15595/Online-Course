package com.course.online.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.course.online.dao.CourseInstanceDao;
import com.course.online.model.CourseInstance;
import com.course.online.util.CourseInstanceStatus;

@RunWith(SpringRunner.class)
public class CourseInstanceServiceUT {
	
	@TestConfiguration
	static class DefaultCourseInstanceServiceTestContextConfiguration {

		@Bean
		public CourseInstanceService courseInstanceService() {
			return new CourseInstanceServiceImpl();
		}
	}
	
	@Autowired
	private CourseInstanceService courseInstanceService;
	
	@MockBean
	private CourseInstanceDao courseInstanceDao;
	
	@Test
	public void testAddCourseInstanceMethodWillReturnCourseInstanceObject() {
		when(courseInstanceDao.save(Mockito.any())).thenReturn(getCourseInstanceObject());
		
		CourseInstance courseInstance = courseInstanceService.addCourseInstance(getCourseInstanceObject());
		
		assertNotNull(courseInstance.getId());
	}
	
	@Test
	public void testListAllCourseInstanceMethodWillReturnMultipleJsonValues()
	{
		when(courseInstanceDao.findAll()).thenReturn(getListOfCourseInstnce());
		
		Iterable<CourseInstance> listofCourseInstance = courseInstanceService.listOfCourseInstances();
		
		for (CourseInstance courseInstance : listofCourseInstance) {
			assertNotNull(courseInstance.getId());
		}
	}

	private Iterable<CourseInstance> getListOfCourseInstnce() {

		List<CourseInstance> courseInstanceList = new ArrayList<CourseInstance>();
		
		CourseInstance courseInstance1 = new CourseInstance();
		courseInstance1.setId(1);
		courseInstance1.setStatus(CourseInstanceStatus.Active);
		
		CourseInstance courseInstance2 = new CourseInstance();
		courseInstance2.setId(1);
		courseInstance2.setStatus(CourseInstanceStatus.Active);
		
		
		courseInstanceList.add(courseInstance1);
		courseInstanceList.add(courseInstance2);
		
		return courseInstanceList;
	}

	private CourseInstance getCourseInstanceObject() {
		
		CourseInstance courseInstance = new CourseInstance();
		
		courseInstance.setId(1);
		courseInstance.setStatus(CourseInstanceStatus.Active);
		
		return courseInstance;
	}

}
