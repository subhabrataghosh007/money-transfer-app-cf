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
}
