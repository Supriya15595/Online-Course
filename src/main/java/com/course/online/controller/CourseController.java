package com.course.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.online.dto.CourseDto;
import com.course.online.model.Course;
import com.course.online.model.Member;
import com.course.online.service.CourseService;
import com.course.online.service.MemberService;
import com.course.online.util.CourseBuilder;

@Controller
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	MemberService memberService;

	@PostMapping("/course")
	public @ResponseBody CourseDto addCourse(@RequestBody CourseDto courseDto) {
		// Converting courseDto to entity
		Course course = CourseBuilder.convert(courseDto);

		// Adding the member to course
		Integer memberId = courseDto.getMemberId();
		Member member = memberService.findMember(memberId);
		course.setMember(member);

		// Saving the course object
		course = courseService.addCourse(course);

		// Converting the Course Entity to CourseDto and returning the value
		courseDto = CourseBuilder.convert(course);

		return courseDto;
	}

	@GetMapping("/course/{id}")
	public @ResponseBody CourseDto findCourseById(@PathVariable int id) {
		
		// Searching for course by id
		Course course = courseService.findCourseById(id);
		
		// Converting the returned course object to courseDto
		CourseDto courseDto = CourseBuilder.convert(course);

		return courseDto;

	}
	
	@GetMapping("/course/list")
	public @ResponseBody Iterable<CourseDto> listOfCourse() {
		
		//Getting all courses from dao
		Iterable<Course> listOfCourses = courseService.findAllCourses();
		
		//Converting list of courses to list of courseDto
		Iterable<CourseDto> listOfCourseDto = CourseBuilder.convert(listOfCourses);
		
		return listOfCourseDto;
	}
	
	
}
