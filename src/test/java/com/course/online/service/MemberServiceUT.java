package com.course.online.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.course.online.dao.MemberDao;
import com.course.online.model.Member;

@RunWith(SpringRunner.class)
public class MemberServiceUT {

	@MockBean
	MemberDao memberDao;
	

	@TestConfiguration
	static class DefaultMemberServiceTestContextConfiguration {

		@Bean
		public MemberService memberService() {
			return new MemberServiceImpl();
		}
	}
	
	@Autowired
	private MemberService memberService;
	
	@Test
	public void testaddMemberMethod() {
		Member member = new Member();
		member.setUserName("abc");
		member.setPassword("abc123");
		member.setEmail("abc@gmail.com");
		when(memberDao.save(member)).thenReturn(getMemberTest());
		Member addMember = memberService.addMember(member);
		assertNotNull(addMember.getId());
	}
	private Member getMemberTest() {
		Member member = new Member();
		member.setUserName("abc");
		member.setPassword("abc123");
		member.setEmail("abc@gmail.com");
		member.setId(10);
		return member;
	}
}
