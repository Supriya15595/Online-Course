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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.course.online.model.Member;
import com.course.online.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerUT {

	@Autowired
	private MockMvc mvc;

	@MockBean
	MemberService memberService;
	
	private Member getAddedMember() {
		Member member = new Member();
		member.setId(100);
		member.setUserName("aaa1@gmail");
		member.setPassword("letmein1");
		return member;
	}

	@Test
	public void testAddMemberMethodReturnsJsonObject() throws Exception {
		when(memberService.addMember(Mockito.any())).thenReturn(getAddedMember());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addMember") .accept(MediaType.APPLICATION_JSON).content("{\n" +
                "\t\"username\":\"aaa1@gmail\",\n" +
                "\t\"password\":\"letmein1\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals("{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}",result.getResponse().getContentAsString());
	}

	@Test
	public void testDeleteMemberMethodReturnsJsonObject() throws Exception
	{
		when(memberService.deleteMember(Mockito.anyInt())).thenReturn(getAddedMember());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/deleteMember/100");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals("{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testFindMemberMethodReturnsJsonObject() throws Exception
	{
		when(memberService.findMember(Mockito.anyInt())).thenReturn(getAddedMember());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/findMember/100");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals("{\"id\":100,\"userName\":\"aaa1@gmail\",\"password\":\"letmein1\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testFindAllMembersMethodResturnsNestedJsonObject() throws Exception
	{
		when(memberService.findAllMembers()).thenReturn(getListofMembers());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/findAllMembers");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals("[{\"id\":1,\"userName\":\"aaa@gmail\",\"password\":\"aaa123\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null},{\"id\":2,\"userName\":\"xxx@gmail\",\"password\":\"xxx123\",\"email\":null,\"type\":null,\"createdOn\":null,\"status\":null}]", result.getResponse().getContentAsString());
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
