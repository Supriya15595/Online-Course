package com.course.online.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.course.online.dto.CourseDto;
import com.course.online.dto.MemberDto;
import com.course.online.model.Course;
import com.course.online.model.Member;


public class CourseBuilder {
	
	public static Course convert(CourseDto courseDto)
	{
		Course course = new Course();
		BeanUtils.copyProperties(courseDto,course);
		return course;
	}
	
	public static CourseDto convert(Course course)
	{
		CourseDto courseDto = new CourseDto();
		BeanUtils.copyProperties(course, courseDto);
		return courseDto;
	}
	
	public static List<CourseDto> convert(Iterable<Course> courseList)
	{
		List<CourseDto> dtoList=new ArrayList<>();
		for (Course course : courseList) {
			CourseDto courseDto = new CourseDto();
			BeanUtils.copyProperties(course, courseDto);
			dtoList.add(courseDto);
		}
		
		return dtoList;
	}
}
