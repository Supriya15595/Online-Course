package com.course.online.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.course.online.dao.LoginDao;
import com.course.online.dao.MemberDao;
import com.course.online.exception.InvalidCredentialsException;
import com.course.online.exception.PasswordIncorrectException;
import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.util.HashPassword;
import com.course.online.util.MemberStatus;

@RunWith(SpringRunner.class)
public class MemberServiceUT {

	@MockBean
	MemberDao memberDao;
	
	@MockBean
	LoginDao loginDao;

	@TestConfiguration
	static class DefaultMemberServiceTestContextConfiguration {

		@Bean
		public MemberService memberService() {
			return new MemberServiceImpl();
		}
		
		@Bean
		public Login login()
		{
			return new Login();
		}
	}

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Login login;

	@Test
	public void testAddMemberMethodWillReturnJsonValue() {
		Member member = new Member();
		member.setUserName("abc");
		member.setPassword("abc123");
		member.setEmail("abc@gmail.com");
		when(memberDao.save(member)).thenReturn(getMemberObject());
		Member addMember = memberService.addMember(member);
		assertNotNull(addMember.getId());
	}

	@Test
	public void testFindMemberMethodWillReturnJsonValue() {
		when(memberDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getMemberObject()));
		Member member = memberService.findMember(Mockito.anyInt());
		assertNotNull(member.getUserName());
	}

	@Test
	public void testListOfMembersWillReturnMultileJsonValues() {
		when(memberDao.findAll()).thenReturn(getListOfMembers());
		Iterable<Member> memberList = memberService.listOfMembers();
		for (Member member : memberList) {
			assertNotNull(member.getId());
		}
	}
	
	@Test
	public void testDeleteMemberMemberMethodWillUpdateMemberStatusToInactive()
	{
		when(memberDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getMemberObject()));
		 
		 Member member = memberService.deleteMember(Mockito.anyInt());
		 
		 assertEquals("Inactive", member.getStatus().toString());
		 		
		
	}
	
	@Test
	public void testUpdatePasswordMethodWithCorrectPasswordWillReturnJasonValueWithUpdatedPassword()
	{
		when(memberDao.findByEmail(Mockito.anyString())).thenReturn(getMemberObject());
		
		Member member = memberService.updatePassword(Mockito.anyString(),"abc123", "xyzz");
		
		assertEquals("9d240bb2341a40d2735c05211310",member.getPassword());
	}
	
	@Test(expected=PasswordIncorrectException.class)
	public void testUpdatePasswordMethodWithIncorrectPasswordWillThrowPasswordIncorrectException()
	{
		when(memberDao.findByEmail(Mockito.anyString())).thenReturn(getMemberObject());
		
		Member member = memberService.updatePassword(Mockito.anyString(), "abc", "xyzz");
		
	}
	
	@Test
	public void testloginMethodWillReturnJsonValue()
	{
		when(memberDao.findByEmail(Mockito.anyString())).thenReturn(getMemberObject());
		
		when(loginDao.save(Mockito.any())).thenReturn(getLoginObject());
		
		Login login = memberService.login("abc", "abc123");
		
		assertNotNull(login.getId());
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testLoginMethodWithInvalidCredentialsWillThrowException()
	{
		when(memberDao.findByEmail(Mockito.anyString())).thenReturn(getMemberObject());
		
		when(loginDao.save(Mockito.any())).thenReturn(getLoginObject());
		
		memberService.login("abc@gmail.com", "abc1");
	}
	
	private Login getLoginObject() {
				
		login.setId(1);
		login.setMember(getMemberObject());
		return null;
	}

	private Iterable<Member> getListOfMembers() {

		List<Member> memberlist = new ArrayList<Member>();

		Member member1 = new Member();

		member1.setId(1);
		member1.setUserName("AAA");
		member1.setPassword("123");

		Member member2 = new Member();

		member2.setId(1);
		member2.setUserName("AAA");
		member2.setPassword("123");
		
		memberlist.add(member1);
		memberlist.add(member2);
		
		return memberlist;
	}
	
	private Member getMemberObject() {
		Member member = new Member();
		member.setId(10);
		member.setUserName("abc");
		member.setPassword(HashPassword.hash("abc123"));
		member.setEmail("abc@gmail.com");
		member.setStatus(MemberStatus.Active);
		return member;
	}
}
