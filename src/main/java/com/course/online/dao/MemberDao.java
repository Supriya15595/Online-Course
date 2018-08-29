package com.course.online.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.course.online.model.Member;

public interface MemberDao extends CrudRepository<Member,Integer> {

}
