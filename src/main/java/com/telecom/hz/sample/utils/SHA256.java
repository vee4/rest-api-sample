package com.telecom.hz.sample.utils;

import java.io.IOException;

public class SHA256 {
	public static void main(String[] args) throws IOException {
//		if(args.length <=0) {
//			System.out.println(String.format("command usage:java -jar xxx.jar $file_path"));
//			throw new IllegalArgumentException("miss file path");
//		}
//		FileOutputStream fos = new FileOutputStream("D:/AppData/sample/dest/tmp.jpg");
		char[] sha256 = CommonUtils.sha256digest(CommonUtils.read("C:/Users/admin/Pictures/empty.txt"));
		System.out.println("digest result:"+ new String(sha256));
//		fos.write();
//		System.out.println(sha256);
//		fos.close();
	}
}
