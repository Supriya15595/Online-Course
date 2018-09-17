package com.course.online.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.course.online.dto.EnrollmentDto;
import com.course.online.model.Enrollment;


public class EnrollmentBuilder {
	
	public static Enrollment convert(EnrollmentDto enrollmentDto)
	{
		Enrollment enrollment = new Enrollment();
		BeanUtils.copyProperties(enrollmentDto,enrollment);
		return enrollment;
	}
	
	public static EnrollmentDto convert(Enrollment enrollment)
	{
		EnrollmentDto enrollmentDto = new EnrollmentDto();
		BeanUtils.copyProperties(enrollment, enrollmentDto);
		return enrollmentDto;
	}
	
	public static List<EnrollmentDto> convert(Iterable<Enrollment> enrollmentList)
	{
		List<EnrollmentDto> dtoList = new ArrayList<EnrollmentDto>();
		for (Enrollment member : enrollmentList) {
			EnrollmentDto enrollmentDto = new EnrollmentDto();
			BeanUtils.copyProperties(member, enrollmentDto);
			dtoList.add(enrollmentDto);
		}
		
		return dtoList;
	}
}
