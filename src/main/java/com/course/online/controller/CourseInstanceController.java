package com.course.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.online.dto.CourseInstanceDto;
import com.course.online.model.Course;
import com.course.online.model.CourseInstance;
import com.course.online.service.CourseInstanceService;
import com.course.online.service.CourseService;
import com.course.online.util.CourseInstanceBuilder;
import com.course.online.util.CourseInstanceStatus;

@Controller
public class CourseInstanceController {

	@Autowired
	private CourseInstanceService courseInstanceService;
	
	@Autowired
	private CourseService courseService;
	
	@PostMapping("/courseInstance/{courseId}")
	public @ResponseBody CourseInstanceDto addCourseInstance(@PathVariable Integer courseId,@RequestBody CourseInstanceDto courseInstanceDto)
	{
		//Finding the Course Object to be mapped with CourseInstance
		Course course = courseService.findCourseById(courseId);
		
		//Converting the CourseInstanceDto to CourseInstance Entity
		CourseInstance courseInstance = CourseInstanceBuilder.convert(courseInstanceDto);
		
		courseInstance.setCourse(course);
		courseInstance.setStatus(CourseInstanceStatus.Active);
			
		courseInstance = courseInstanceService.addCourseInstance(courseInstance);
		
		//Convert courseInstance Entity returned after performing insert into courseInstanceDto before returning
		courseInstanceDto =  CourseInstanceBuilder.convert(courseInstance);
		
		return courseInstanceDto;
		
	}
	
	@GetMapping("/courseInstance/all")
	public @ResponseBody Iterable<CourseInstanceDto> listAllCourseInstance()
	{
		Iterable<CourseInstance> listOfCourseInstance = courseInstanceService.listOfCourseInstances();
		
		//Converting list of CourseInstance Entity to list of CourseInstanceDto
		Iterable<CourseInstanceDto> courseInstanceDtoList =  CourseInstanceBuilder.convert(listOfCourseInstance);
		
		return courseInstanceDtoList;
		
	}
}
