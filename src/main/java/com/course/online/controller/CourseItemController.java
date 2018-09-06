package com.course.online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.online.dto.CourseItemDto;
import com.course.online.model.Course;
import com.course.online.model.CourseItem;
import com.course.online.service.CourseItemService;
import com.course.online.service.CourseService;
import com.course.online.util.CourseItemBuilder;

@Controller
public class CourseItemController {

	@Autowired
	CourseItemService courseItemService;

	@Autowired
	CourseService courseService;

	@PostMapping("/courseItem")
	public @ResponseBody CourseItemDto addCourseItem(@RequestBody CourseItemDto courseItemDto) {

		// Convert CourseItemDto to CourseItemEntity
		CourseItem courseItem = CourseItemBuilder.convert(courseItemDto);

		// Find the course with specified id
		Integer courseId = courseItemDto.getCourseId();
		Course course = courseService.findCourseById(courseId);
		courseItem.setCourse(course);

		courseItem = courseItemService.addCourseItem(courseItem);

		// Covert CourseItem to CourseItemDto
		courseItemDto = CourseItemBuilder.convert(courseItem);

		return courseItemDto;
	}

	@GetMapping("/courseItem/{courseItemId}")
	public @ResponseBody CourseItemDto findCourseItem(@PathVariable int courseItemId) {
		CourseItem courseItem = courseItemService.findCourseItem(courseItemId);

		// Convert CourseItem entity to CourseItemDto
		CourseItemDto courseItemDto = CourseItemBuilder.convert(courseItem);

		return courseItemDto;
	}

	@GetMapping("/courseItem/list")
	public @ResponseBody Iterable<CourseItemDto> listOfCourseItem() {
		Iterable<CourseItem> listOfCourseItem = courseItemService.findAllCourseItems();

		// Convert list of CourseItem Entity to list of CourseItemDto

		List<CourseItemDto> courseItemDtoList = CourseItemBuilder.convert(listOfCourseItem);

		return courseItemDtoList;
	}

	@GetMapping("/courseItem/course/{courseId}")
	public @ResponseBody Iterable<CourseItemDto> listCourseItemsOfCourse(@PathVariable Integer courseId) {
		Iterable<CourseItem> listOfCourseItem = courseItemService.listCourseItemsOfCourse(courseId);

		List<CourseItemDto> courseItemDtoList = CourseItemBuilder.convert(listOfCourseItem);

		return courseItemDtoList;
	}
}
