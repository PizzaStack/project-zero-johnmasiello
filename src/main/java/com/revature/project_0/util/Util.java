package com.revature.project_0.util;

import java.time.Instant;
import java.util.Date;

public final class Util {
	public static final String ZERO_PADDING_ID_PATTERN = "%019d";
	public static final String ZERO_PADDING_SHORTER_ID_PATTERN = "%010d";
	public static final String NOT_AVAILABLE = "N/A";
	public static final String CURRENCY_SYMBOL = "$";
	
	public Util() {
		throw new IllegalStateException("Instances of Util are not allowed");
	}
	
	public static final <T> String zeroPadId(T id) {
		return String.format(ZERO_PADDING_ID_PATTERN, id);
	}
	
	public static final <T> String zeroPadCondensedId(T id) {
		return String.format(ZERO_PADDING_SHORTER_ID_PATTERN, id);
	}
	
	public static final <T> String currencyFormat(T amount) {
		return String.format("%s%,.2f", CURRENCY_SYMBOL, amount);
	}
	
	public static final Date getCurrentDate() {
		return Date.from(Instant.now());
	}
}
