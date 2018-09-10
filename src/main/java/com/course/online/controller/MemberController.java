package com.course.online.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.online.dto.MemberDto;
import com.course.online.model.Member;
import com.course.online.service.MemberService;
import com.course.online.util.MemberBuilder;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@PostMapping("/member")
	public  ResponseEntity<Object> addMember(@RequestBody MemberDto memberDto)
	{
		Member member = MemberBuilder.convert(memberDto);
		member=memberService.addMember(member);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(member.getId()).toUri();
		
		return ResponseEntity.created(location).build();
//		return MemberBuilder.convert(member);
	}
	
	@PostMapping("/member/delete/{id}")
	public @ResponseBody MemberDto deleteMember(@PathVariable int id)
	{
		Member member =  memberService.deleteMember(id);
		MemberDto memberDto = MemberBuilder.convert(member);
		return memberDto;
	}
	
	@GetMapping("/member/{id}")
	public @ResponseBody MemberDto findMember(@PathVariable Integer id)
	{
		Member member = memberService.findMember(id);
		MemberDto memeberDto = MemberBuilder.convert(member);
		return memeberDto;
	}
	
	@GetMapping("/member/list")
	public @ResponseBody List<MemberDto> listOfMembers()
	{
		List<MemberDto> memberDtoList = MemberBuilder.convert(memberService.listOfMembers());
		return memberDtoList;
	}
	
	@PostMapping("/member/updadePassword/{id}/{currentPassword}/{newPassword}")
	public @ResponseBody MemberDto updatePassword(@PathVariable Integer id,@PathVariable String currentPassword,@PathVariable String newPassword)
	{
		Member member = memberService.updatePassword(id, currentPassword, newPassword);
		
		//Convert the Member Entity into MemberDto
		MemberDto memberDto = MemberBuilder.convert(member);
		
		return memberDto;
		
	}
}
