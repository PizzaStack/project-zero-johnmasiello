package com.revature.project_0.util;

import static org.junit.Assert.assertNotEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class UtilTest {
	@Test
	public void testDateParsing() throws Exception {
		String strDate = "10-01-2001";
		Date date = Util.parseDate(strDate);
		assertNotEquals(null, date);
		System.out.println(date);
	}
}
