package com.course.online.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.course.online.dto.CourseInstanceDto;
import com.course.online.model.CourseInstance;


public class CourseInstanceBuilder {
	
	public static CourseInstance convert(CourseInstanceDto courseInstanceDto)
	{
		CourseInstance courseInstance = new CourseInstance();
		BeanUtils.copyProperties(courseInstanceDto,courseInstance);
		return courseInstance;
	}
	
	public static CourseInstanceDto convert(CourseInstance courseInstance)
	{
		CourseInstanceDto courseInstanceDto = new CourseInstanceDto();
		BeanUtils.copyProperties(courseInstance, courseInstanceDto);
		return courseInstanceDto;
	}
	
	public static List<CourseInstanceDto> convert(Iterable<CourseInstance> courseInstanceList)
	{
		List<CourseInstanceDto> dtoList=new ArrayList<CourseInstanceDto>();
		for (CourseInstance courseInstance : courseInstanceList) {
			CourseInstanceDto courseInstanceDto = new CourseInstanceDto();
			BeanUtils.copyProperties(courseInstance, courseInstanceDto);
			dtoList.add(courseInstanceDto);
		}
		
		return dtoList;
	}
}
