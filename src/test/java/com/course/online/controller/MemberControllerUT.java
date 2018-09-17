package com.course.online.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerUT {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MemberService memberService;

	@Test
	public void testloginMethodWillReturnJsonObject() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/login");

		MockHttpServletResponse response = new MockHttpServletResponse();

//		MockFilterChain mockChain = new MockFilterChain();
//		mockChain.doFilter(request, response);
//		
		when(memberService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(getLoginObject());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login").accept(MediaType.APPLICATION_JSON)
				.content("{\n" + "\t\"email\":\"aaa1@gmail\",\n" + "\t\"password\":\"letmein1\"\n" + "}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

		assertEquals("{\"id\":1,\"memberId\":100,\"token\":\"21i1j217as\",\"expireDate\":null}",
				mvcResult.getResponse().getContentAsString());

	}

	

	@Test
	public void testAddMemberMethodReturnsJsonObject() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());

		when(memberService.addMember(Mockito.any())).thenReturn(getAddedMember());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member").header("auth-token", "21i1j217as")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\n" + "\t\"username\":\"aaa1@gmail\",\n" + "\t\"password\":\"letmein1\"\n" + "}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(
				"{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testDeleteMemberMethodReturnsJsonObject() throws Exception {
		
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(memberService.deleteMember(Mockito.anyInt())).thenReturn(getAddedMember());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member/delete/100").header("auth-token", "21i1j217as");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(
				"{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testFindMemberMethodReturnsJsonObject() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(memberService.findMember(Mockito.anyInt())).thenReturn(getAddedMember());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/member/100").header("auth-token", "21i1j217as");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(
				"{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testListOfMembersWillReturnMultipleJsonValue() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(memberService.listOfMembers()).thenReturn(getListofMembers());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/member/list").header("auth-token", "21i1j217as");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(
				"[{\"id\":1,\"userName\":\"aaa@gmail\",\"password\":\"aaa123\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null},{\"id\":2,\"userName\":\"xxx@gmail\",\"password\":\"xxx123\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}]",
				result.getResponse().getContentAsString());
	}

	@Test
	public void testUpdatePasswordMethodWillReturnJsonValue() throws Exception {
		when(memberService.findByToken(Mockito.anyString())).thenReturn(getLoginObject());
		
		when(memberService.updatePassword(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(getAddedMember());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member/updatePassword")
				.header("auth-token", "21i1j217as")
				.accept(MediaType.APPLICATION_JSON).content("{\n" + "\t\"email\":\"aaa1@gmail\",\n"
						+ "\t\"currentPassword\":\"letmein1\",\n" + "\t\"newPassword\":\"qwerty\"\n" + "}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

		assertEquals(
				"{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}",
				mvcResult.getResponse().getContentAsString());
	}
	
	private Login getLoginObject() {

		Login login = new Login();

		login.setId(1);
		login.setToken("21i1j217as");
		login.setMember(getAddedMember());

		return login;
	}

	private Member getAddedMember() {
		Member member = new Member();
		member.setId(100);
		member.setUserName("aaa1@gmail");
		member.setPassword("letmein1");
		return member;
	}

	private Iterable<Member> getListofMembers() {

		List<Member> listOfMembers = new ArrayList<Member>();

		Member member1 = new Member();
		member1.setId(1);
		member1.setUserName("aaa@gmail");
		member1.setPassword("aaa123");

		Member member2 = new Member();
		member2.setId(2);
		member2.setUserName("xxx@gmail");
		member2.setPassword("xxx123");

		listOfMembers.add(member1);
		listOfMembers.add(member2);

		return listOfMembers;
	}
}
