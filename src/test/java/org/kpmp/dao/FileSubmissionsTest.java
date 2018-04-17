package org.kpmp.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileSubmissionsTest {

	private FileSubmissions fileSubmission;

	@Before
	public void setUp() throws Exception {
		fileSubmission = new FileSubmissions();
	}

	@After
	public void tearDown() throws Exception {
		fileSubmission = null;
	}

	@Test
	public void testSetId() {
		fileSubmission.setId(45);
		assertEquals(45, fileSubmission.getId());
	}

	@Test
	public void testSetFilename() {
		fileSubmission.setFilename("filename");
		assertEquals("filename", fileSubmission.getFilename());
	}

	@Test
	public void testSetCreatedAt() {
		Date createdAt = new Date();
		fileSubmission.setCreatedAt(createdAt);
		assertEquals(createdAt, fileSubmission.getCreatedAt());
	}

	@Test
	public void testSetUpdatedAt() {
		Date updatedAt = new Date();
		fileSubmission.setUpdatedAt(updatedAt);
		assertEquals(updatedAt, fileSubmission.getUpdatedAt());
	}

	@Test
	public void testSetDeletedAt() {
		Date deletedAt = new Date();
		fileSubmission.setDeletedAt(deletedAt);
		assertEquals(deletedAt, fileSubmission.getDeletedAt());
	}

}
