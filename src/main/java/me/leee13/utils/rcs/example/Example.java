package me.leee13.utils.rcs.example;

import me.leee13.utils.rcs.pub.RCSUtil;

public class Example {

	public static void main(String[] args) {
		
//		String stringPattern = "IT-${M()}-${M(123)}-${U()}-${U(time_low)}-${U(2)}-${U(3)}-${U(4)}-${D()}";
		String stringPattern = "IT-${S(3)}";
		
		RCSUtil rcsUtil = RCSUtil.getInstance();
		
		String id = rcsUtil.createRandomCharSequence(stringPattern);
		
		System.out.println(stringPattern);
		System.out.println(id);

	}

}
