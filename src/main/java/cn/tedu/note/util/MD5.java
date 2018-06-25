package cn.tedu.note.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;

public class MD5 {
	private static String salt="cn.tedu.note";

	public static String md5(String str){
		try {
			byte[]data=str.getBytes("utf-8");
			MessageDigest md=MessageDigest.getInstance("md5");
			md.update(data);
			byte[]md5=md.digest();
			String code=Base64.encodeBase64String(md5);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/*
	 * 加盐版
	 */
	public static String saltMd5(String str){
		try {
			byte[]data=str.getBytes("utf-8");
			MessageDigest md=MessageDigest.getInstance("md5");
			md.update(data);
			md.update(salt.getBytes("utf-8"));
			byte[]md5=md.digest();
			String code=Base64.encodeBase64String(md5);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
