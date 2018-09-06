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

import com.course.online.dao.MemberDao;
import com.course.online.model.Member;
import com.course.online.util.MemberStatus;
import com.course.online.util.PasswordIncorrectException;

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
		when(memberDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getMemberObject()));
		
		Member member = memberService.updatePassword(Mockito.anyInt(), "abc123", "xyzz");
		
		assertEquals("xyzz",member.getPassword());
	}
	
	@Test(expected=PasswordIncorrectException.class)
	public void testUpdatePasswordMethodWithIncorrectPasswordWillThrowPasswordIncorrectException()
	{
		when(memberDao.findById(Mockito.anyInt())).thenReturn(Optional.of(getMemberObject()));
		
		Member member = memberService.updatePassword(Mockito.anyInt(), "abc", "xyzz");
		
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
		member.setPassword("abc123");
		member.setEmail("abc@gmail.com");
		member.setStatus(MemberStatus.Active);
		return member;
	}
}
