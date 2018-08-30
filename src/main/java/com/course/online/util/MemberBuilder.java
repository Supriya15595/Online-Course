package com.course.online.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.course.online.dto.MemberDto;
import com.course.online.model.Member;


public class MemberBuilder {
	
	public static Member convert(MemberDto memberDto)
	{
		Member member = new Member();
		BeanUtils.copyProperties(memberDto,member);
		return member;
	}
	
	public static MemberDto convert(Member member)
	{
		MemberDto memberDto = new MemberDto();
		BeanUtils.copyProperties(member, memberDto);
		return memberDto;
	}
	
	public static List<MemberDto> convert(Iterable<Member> memberList)
	{
		List<MemberDto> dtoList=new ArrayList<>();
		for (Member member : memberList) {
			MemberDto memberDto = new MemberDto();
			BeanUtils.copyProperties(member, memberDto);
			dtoList.add(memberDto);
		}
		
		return dtoList;
	}
}
