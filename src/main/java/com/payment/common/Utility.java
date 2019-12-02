package com.payment.common;

import java.math.BigDecimal;

public class Utility {

	public static boolean isValidAmount(String input) {
		try {
			new BigDecimal(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isEmpty(String str) {
		
		if (str == null) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}  else {
			return false;
		}
	}
}
