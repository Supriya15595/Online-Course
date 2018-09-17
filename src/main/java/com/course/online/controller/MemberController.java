package com.course.online.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.course.online.AppContext;
import com.course.online.dao.LoginDao;
import com.course.online.dto.LoginCredentialsDto;
import com.course.online.dto.LoginDto;
import com.course.online.dto.MemberCredentialsDto;
import com.course.online.dto.MemberDto;
import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.service.MemberService;
import com.course.online.util.LoginBuilder;
import com.course.online.util.MemberBuilder;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private LoginDao loginDao;

	@PostMapping("/login")
	public LoginDto login(@Valid @RequestBody LoginCredentialsDto loginCredentials) {
		String email = loginCredentials.getEmail();
		String password = loginCredentials.getPassword();

		Login login = memberService.login(email, password);

		LoginDto loginDto = LoginBuilder.convert(login);

		return loginDto;
	}

	@PostMapping("/member")
	public @ResponseBody MemberDto addMember(@Valid @RequestBody MemberDto memberDto) {
		Member member = MemberBuilder.convert(memberDto);
		member = memberService.addMember(member);
		/*
		 * URI location =
		 * ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand
		 * (member.getId()).toUri();
		 * 
		 * return ResponseEntity.created(location).build();
		 */

		return MemberBuilder.convert(member);
	}

	@PostMapping("/member/delete")
	public @ResponseBody MemberDto deleteMember() {

		Member loggedInMember = AppContext.getMember();

		int id = loggedInMember.getId();

		Member member = memberService.deleteMember(id);
		MemberDto memberDto = MemberBuilder.convert(member);
		return memberDto;
	}

	@GetMapping("/member")
	public @ResponseBody MemberDto findMember() {

		Member loggedInMember = AppContext.getMember();
		int id = loggedInMember.getId();
		
		Member member = memberService.findMember(id);
		MemberDto memeberDto = MemberBuilder.convert(member);
		return memeberDto;
	}

	@GetMapping("/member/list")
	public @ResponseBody List<MemberDto> listOfMembers() {
		List<MemberDto> memberDtoList = MemberBuilder.convert(memberService.listOfMembers());
		return memberDtoList;
	}

	@PostMapping("/member/updatePassword")
	public @ResponseBody MemberDto updatePassword(@RequestBody MemberCredentialsDto memberCredentials) {
		String email = memberCredentials.getEmail();
		String currentPassword = memberCredentials.getCurrentPassword();
		String newPassword = memberCredentials.getNewPassword();

		Member member = memberService.updatePassword(email, currentPassword, newPassword);

		// Convert the Member Entity into MemberDto
		MemberDto memberDto = MemberBuilder.convert(member);

		return memberDto;

	}
}
