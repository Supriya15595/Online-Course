package com.course.online.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.online.dao.LoginDao;
import com.course.online.dao.MemberDao;
import com.course.online.exception.InvalidCredentialsException;
import com.course.online.exception.PasswordIncorrectException;
import com.course.online.model.Login;
import com.course.online.model.Member;
import com.course.online.util.HashPassword;
import com.course.online.util.MemberStatus;

@Component
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;

	@Autowired
	LoginDao loginDao;

	@Autowired
	Login login;

	@Override
	public Member addMember(Member member) {
		// Setting the currentDate attribute to present date
		Date currentDate = new Date();
		member.setCreatedOn(currentDate);

		// Setting the status attribute to active(default)
		member.setStatus(MemberStatus.Active);

		// Storing the hashed password into the database
		String password = member.getPassword();

		String hashedPassword = HashPassword.hash(password);

		member.setPassword(hashedPassword);

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

//		Member member = findMember(id);

		Member member = memberDao.findById(id).get();

		member.setStatus(MemberStatus.Inactive);

		memberDao.save(member);

		return member;
	}

	@Override
	public Iterable<Member> listOfMembers() {

		Iterable<Member> memberList = memberDao.findAll();
		return memberList;
	}

	@Override
	public Member updatePassword(String email, String currentPassword, String newPassword) {

		Member member = memberDao.findByEmail(email);

		String hashedPassword = HashPassword.hash(currentPassword);

		if (hashedPassword.equals(member.getPassword())) {
			String newHashedPassword = HashPassword.hash(newPassword);

			member.setPassword(newHashedPassword);
			memberDao.save(member);
		} else {
			throw new PasswordIncorrectException("Password Mismatch");
		}

		return member;
	}

	@Override
	public Login login(String email, String password) {

		Member member = memberDao.findByEmail(email);

		String hashPassword = HashPassword.hash(password);

		if (member.getPassword().equals(hashPassword)) {
			UUID uuid = UUID.randomUUID();

			login.setMember(member);
			login.setToken(uuid.toString());

			LocalDate date = LocalDate.now().plusDays(5);
			login.setExpireDate(date);

			loginDao.save(login);
		} else {
			throw new InvalidCredentialsException("Creadentials mismatch");
		}

		return login;
	}

	@Override
	public Login findByToken(String token) {

		Login login = loginDao.findByToken(token);

		return login;
	}

}
