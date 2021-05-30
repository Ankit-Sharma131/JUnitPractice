package com.in28minutes.junit.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringHelperTest {

	@Test
	public void testTruncateAInFirst2Positions() {
		StringHelper helper = new StringHelper();
		assertEquals("CD", helper.truncateAInFirst2Positions("AACD"));
		assertEquals("CD",helper.truncateAInFirst2Positions("ACD"));
	}

}