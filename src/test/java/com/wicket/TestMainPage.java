package com.wicket;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.wicket.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestMainPage {

	private WicketTester tester;

	@Before
	public void setUp() {
		tester = new WicketTester(new WicketApplication());
	}

	/*@Test
	public void homepageRendersSuccessfully() {
		// start and render the test page
		tester.startPage(ListContacts.class);
		// assert rendered page class
		tester.assertRenderedPage(ListContacts.class);
	}*/
}
