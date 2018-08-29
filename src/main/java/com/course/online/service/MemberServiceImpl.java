package com.course.online.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.course.online.dao.MemberDao;
import com.course.online.model.Member;

public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;
	
	@Override
	public Member addMember(Member member) {
		member = memberDao.save(member);
		return member;
	}

}
