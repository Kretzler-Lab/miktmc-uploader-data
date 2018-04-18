package org.kpmp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InstitutionDemographicsTest {

	private InstitutionDemographics institution;

	@Before
	public void setUp() throws Exception {
		institution = new InstitutionDemographics();
	}

	@After
	public void tearDown() throws Exception {
		institution = null;
	}

	@Test
	public void testSetInstitutionShortName() {
		institution.setInstitutionShortName("institutionShortName");
		assertEquals("institutionShortName", institution.getInstitutionShortName());
	}

	@Test
	public void testSetInstitutionName() {
		institution.setInstitutionName("institutionName");
		assertEquals("institutionName", institution.getInstitutionName());
	}

	@Test
	public void testSetId() {
		institution.setId(565);
		assertEquals(565, institution.getId());
	}

}
