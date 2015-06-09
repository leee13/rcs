package me.leee13.utils.rcs.pub;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	public static String md5(String string){
		StringBuffer sb = new StringBuffer();
		
		try {
			byte[] buf = string.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(buf);
			byte[] digestBuf = md5.digest();
			for (byte b : digestBuf) {
				sb.append(Integer.toHexString(b&0xff));
			}
		} catch (NoSuchAlgorithmException e) {
			/*empty*/
		}
		
		return sb.toString();
	}

}
