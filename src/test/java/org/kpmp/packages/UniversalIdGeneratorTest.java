package org.kpmp.packages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.packages.UniversalIdGenerator;

public class UniversalIdGeneratorTest {

	private UniversalIdGenerator generator;

	@Before
	public void setUp() throws Exception {
		generator = new UniversalIdGenerator();
	}

	@After
	public void tearDown() throws Exception {
		generator = null;
	}

	@Test
	public void testGenerateUniversalId() throws Exception {
		String uuid = generator.generateUniversalId();

		assertNotNull(uuid);
		assertEquals(4, UUID.fromString(uuid).version());
	}

}