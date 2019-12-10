package org.kpmp.packages;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.packages.State;

public class StateTest {

	private State state;

	@Before
	public void setUp() throws Exception {
		state = new State("packageId", "state", false, "codicil");
	}

	@After
	public void tearDown() throws Exception {
		state = null;
	}

	@Test
	public void testSetPackageId() {
		state.setPackageId("coolPackage");
		assertEquals("coolPackage", state.getPackageId());
	}

	@Test
	public void testSetState() {
		state.setState("newState");
		assertEquals("newState", state.getState());
	}

	@Test
	public void testSetCodicil() {
		state.setCodicil("this is why we can't have nice things");
		assertEquals("this is why we can't have nice things", state.getCodicil());
	}

	@Test
	public void testConstructor() throws Exception {
		State constructorTest = new State("a package id", "a state", false, "reasons");
		assertEquals("a package id", constructorTest.getPackageId());
		assertEquals("a state", constructorTest.getState());
		assertEquals("reasons", constructorTest.getCodicil());
	}

	@Test
	public void testSetStateChangeDate() throws Exception {
		Date date = new Date();
		state.setStateChangeDate(date);
		assertEquals(date, state.getStateChangeDate());
	}

}
