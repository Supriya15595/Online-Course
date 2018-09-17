package com.course.online.dao;

import org.springframework.data.repository.CrudRepository;

import com.course.online.model.Login;

public interface LoginDao extends CrudRepository<Login, Integer> {
	
	public Login findByToken(String token);

}
