package com.course.online;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.course.online.exception.AuthenticationInvalidException;
import com.course.online.model.Login;
import com.course.online.service.MemberService;

@Component
public class CourseFilter implements Filter {

	@Autowired
	private MemberService memberService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getRequestURI().contains("/login")) {
			chain.doFilter(request, response);
		} else {

			String authToken = req.getHeader("auth-token");
			Login login = memberService.findByToken(authToken);
			
			//Set the member to attribute
			AppContext.setMember(login.getMember());
			
			if (authToken != null && authToken.equals(login.getToken())) {
				chain.doFilter(request, response);
			} else {
				throw new AuthenticationInvalidException("Invalid authentication");
			}
		}

	}

	@Override
	public void destroy() {

	}

}
