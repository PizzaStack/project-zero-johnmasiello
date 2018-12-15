package com.revature.project_0.util;

public final class Util {
	public static final String ZERO_PADDING_ID_PATTERN = "%019d";
	
	public Util() {
		throw new IllegalStateException("Instances of Util are not allowed");
	}
	
	public static final <T> String zeroPadId(T id) {
		return String.format(ZERO_PADDING_ID_PATTERN, id);
	}
}
