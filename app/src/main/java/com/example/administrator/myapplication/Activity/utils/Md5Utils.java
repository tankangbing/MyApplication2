package com.example.administrator.myapplication.Activity.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

	/**
	 * @param str
	 * @return 把字符串转换md5
	 */
	public static String md5(String str) {
		StringBuilder sb = new StringBuilder();
		// 不可逆
		// 1 获取这个的对象
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] digest = md.digest(str.getBytes());
			for (int i = 0; i < digest.length; i++) {
				int b = (int) (digest[i] & 0xff);// 加盐 1111 1111(-128-127)
				// sb.append(b); 0000 0000 0000 0000 0000 0000 1111 1111
				// 1111 1111
				// (0-256)
				String s = Integer.toHexString(b);
				if (s.length() == 1) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static String md5(InputStream is) {
		MessageDigest digester;
		StringBuilder sb = new StringBuilder();
		try {
			digester = MessageDigest.getInstance("MD5");
			byte[] bytes = new byte[8192];
			int byteCount;
			while ((byteCount = is.read(bytes)) > 0) {
				digester.update(bytes, 0, byteCount);
			}
			byte[] digest = digester.digest();
			for (int i = 0; i < digest.length; i++) {
				int b = (int) (digest[i] & 0xff);// 加盐 1111 1111(-128-127)
				// sb.append(b); 0000 0000 0000 0000 0000 0000 1111 1111
				// 1111 1111
				// (0-256)
				String s = Integer.toHexString(b);
				if (s.length() == 1) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(b));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		String str=md5("123");
		System.out.println(str);
		FileInputStream is=new FileInputStream("病毒.apk");
		String fileMd5=md5(is);
		System.out.println(fileMd5);
	}

}
