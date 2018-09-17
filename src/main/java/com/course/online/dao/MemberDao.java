package com.course.online.dao;

import org.springframework.data.repository.CrudRepository;

import com.course.online.model.Member;

public interface MemberDao extends CrudRepository<Member,Integer> {
	
	public Member findByEmail(String email);

}
