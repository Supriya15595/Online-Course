package com.course.online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.online.dto.MemberDto;
import com.course.online.model.Member;
import com.course.online.service.MemberService;
import com.course.online.util.MemberBuilder;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@PostMapping("/member")
	public @ResponseBody MemberDto addMember(@RequestBody MemberDto memberDto)
	{
		Member member = MemberBuilder.convert(memberDto);
		member=memberService.addMember(member);
		return MemberBuilder.convert(member);
	}
	
	@PostMapping("/member/delete/{id}")
	public @ResponseBody MemberDto deleteMember(@PathVariable int id)
	{
		Member member =  memberService.deleteMember(id);
		MemberDto memberDto = MemberBuilder.convert(member);
		return memberDto;
	}
	
	@GetMapping("/member/{id}")
	public @ResponseBody MemberDto findMember(@PathVariable int id)
	{
		Member member = memberService.findMember(id);
		MemberDto memeberDto = MemberBuilder.convert(member);
		return memeberDto;
	}
	
	@GetMapping("/member/list")
	public @ResponseBody List<MemberDto> listOfMembers()
	{
		List<MemberDto> memberDtoList = MemberBuilder.convert(memberService.findAllMembers());
		return memberDtoList;
	}
}
