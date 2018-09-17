package com.course.online.util;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.course.online.dto.LoginDto;
import com.course.online.model.Login;
import com.course.online.model.Member;

public class LoginBuilder {

	public static LoginDto convert(Login login) {
		LoginDto loginDto = new LoginDto();
		
		Integer id = login.getId();
		String token = login.getToken();
		LocalDate expireDate = login.getExpireDate();
		
		Member member = login.getMember();
		Integer memberId = member.getId();
		
		loginDto.setId(id);
		loginDto.setExpireDate(expireDate);
		loginDto.setMemberId(memberId);
		loginDto.setToken(token);
		
		return loginDto;
	}

	public static Login convert(LoginDto loginDto)
	{
		Login login = new Login();
		BeanUtils.copyProperties(loginDto,login);
		return login;
	}
}
