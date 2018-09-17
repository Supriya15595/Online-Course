package com.course.online.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
	
	public static String hash(String password)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] digest = md.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < digest.length; i++) {
				sb.append(Integer.toString((digest[i]&0xff)+0100,16).substring(1));
			}
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			
			throw new RuntimeException(e);
		}
	}
}
