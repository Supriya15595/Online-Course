package com.course.online.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.course.online.dto.CourseItemDto;
import com.course.online.model.CourseItem;


public class CourseItemBuilder {
	
	public static CourseItem convert(CourseItemDto courseItemDto)
	{
		CourseItem courseItem = new CourseItem();
		BeanUtils.copyProperties(courseItemDto,courseItem);
		return courseItem;
	}
	
	public static CourseItemDto convert(CourseItem courseItem)
	{
		CourseItemDto courseItemDto = new CourseItemDto();
		BeanUtils.copyProperties(courseItem, courseItemDto);
		return courseItemDto;
	}
	
	public static List<CourseItemDto> convert(Iterable<CourseItem> courseItemList)
	{
		List<CourseItemDto> dtoList=new ArrayList<>();
		for (CourseItem courseItem : courseItemList) {
			CourseItemDto courseItemDto = new CourseItemDto();
			BeanUtils.copyProperties(courseItem, courseItemDto);
			dtoList.add(courseItemDto);
		}
		
		return dtoList;
	}
}
