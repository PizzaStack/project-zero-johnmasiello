package com.revature.project_0.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.jetbrains.annotations.Nullable;

public final class Util {
	public static final String ZERO_PADDING_ID_PATTERN = "%019d";
	public static final String ZERO_PADDING_SHORTER_ID_PATTERN = "%010d";
	public static final String NOT_AVAILABLE = "N/A";
	public static final String CURRENCY_SYMBOL = "$";
	private static final String DATE_PATTERN = "MM-dd-yyyy";
	private static final String PRINTABLE_DATE_PATTERN = "MM-DD-YYYY";
	private static final SimpleDateFormat simpleDateFormat;
	
	static {
		simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
	}
	
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
	
	public static final String printMember(@Nullable Object o) {
		return o != null ? String.valueOf(o) : "";
	}
	
	public static final Date parseDate(String strDate) {
		try {
			return simpleDateFormat.parse(strDate);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static final String getPrintableDatePattern() {
		return PRINTABLE_DATE_PATTERN;
	}
	
	public static final int parseStringAsInt(String strInt) {
		try {
			return Integer.parseInt(strInt);
		} catch (NumberFormatException e) {}
		return 0;
	}
}
