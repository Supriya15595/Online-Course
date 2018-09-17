package com.course.online;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.course.online.model.Member;

public class AppContext {
	
	private static final String LOGGED_IN_MEMBER = "LOGGED_IN_MEMBER";

	public static void setMember(Member member) {
		getRequest().setAttribute(LOGGED_IN_MEMBER, member);
	}
	
	public static Member getMember() {
		return (Member) getRequest().getAttribute(LOGGED_IN_MEMBER);
	}
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

}
