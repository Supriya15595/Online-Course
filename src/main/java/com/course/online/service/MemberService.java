package com.course.online.service;

import org.springframework.stereotype.Component;

import com.course.online.model.Member;

@Component
public interface MemberService {
	
	public Member addMember(Member member);
	
	public Member findMember(Integer id);

	public Member deleteMember(int id);
	
	public Iterable<Member> findAllMembers();
	
}
