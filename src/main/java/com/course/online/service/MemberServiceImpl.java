package com.course.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.MemberDao;
import com.course.online.model.Member;

@Component
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;

	@Override
	public Member addMember(Member member) {
		member = memberDao.save(member);
		return member;
	}

	@Override
	public Member findMember(Integer id) {
		Member member = memberDao.findById(id).get();
		return member;
	}

	@Override
	public Member deleteMember(int id) {
		
		Member member = findMember(id);
		
		memberDao.delete(member);
		
		return member;
	}

	@Override
	public Iterable<Member> findAllMembers() {
		
		return memberDao.findAll();
	}
	
	

}
