package org.kpmp.upload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.dao.PackageTypeOther;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

public class UploadControllerTest {

	@Mock
	private UploadService uploadService;
	@Mock
	private FileHandler fileHandler;
	private UploadController controller;
	private UploadPackageRepository uploadPackageRepository;
	private MetadataHandler metadataHandler;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new UploadController(uploadService, fileHandler, uploadPackageRepository, metadataHandler);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}

	@Test
	public void testUploadPackageInfo_whenPackageTypeIsNotOther() {
		PackageInformation packageInformation = mock(PackageInformation.class);
		when(packageInformation.getPackageType()).thenReturn("something");
		when(uploadService.saveUploadPackage(packageInformation, null)).thenReturn(5);
		when(uploadService.saveSubmitterInfo(packageInformation)).thenReturn(55);
		when(uploadService.findInstitutionId(packageInformation)).thenReturn(66);

		UploadPackageIds packageIds = controller.uploadPackageInfo(packageInformation);

		verify(uploadService).saveUploadPackage(packageInformation, null);
		verify(uploadService).saveSubmitterInfo(packageInformation);
		verify(uploadService).findInstitutionId(packageInformation);
		verify(uploadService, times(0)).savePackageTypeOther(any(String.class));
		assertEquals(5, packageIds.getPackageId());
		assertEquals(55, packageIds.getSubmitterId());
		assertEquals(66, packageIds.getInstitutionId());
	}

	@Test
	public void testUploadPackageInfo_whenPackageTypeIsOtherAndPackageTypeOtherIsBlank() throws Exception {
		PackageInformation packageInformation = mock(PackageInformation.class);
		when(packageInformation.getPackageType()).thenReturn("Other");
		when(packageInformation.getPackageTypeOther()).thenReturn("");

		try {
			controller.uploadPackageInfo(packageInformation);
			fail("Should have thrown an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("Package type 'Other' selected, but not defined further.", e.getMessage());
			verify(uploadService, times(0)).saveUploadPackage(packageInformation, null);
			verify(uploadService, times(0)).saveSubmitterInfo(packageInformation);
			verify(uploadService, times(0)).findInstitutionId(packageInformation);
			verify(uploadService, times(0)).savePackageTypeOther(any(String.class));
		}

	}

	@Test
	public void testUploadPackageInfo_whenPackageTypeIsOtherAndPackageTypeOtherIsNull() throws Exception {
		PackageInformation packageInformation = mock(PackageInformation.class);
		when(packageInformation.getPackageType()).thenReturn("Other");
		when(packageInformation.getPackageTypeOther()).thenReturn(null);

		try {
			controller.uploadPackageInfo(packageInformation);
			fail("Should have thrown an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("Package type 'Other' selected, but not defined further.", e.getMessage());
			verify(uploadService, times(0)).saveUploadPackage(packageInformation, null);
			verify(uploadService, times(0)).saveSubmitterInfo(packageInformation);
			verify(uploadService, times(0)).findInstitutionId(packageInformation);
			verify(uploadService, times(0)).savePackageTypeOther(any(String.class));
		}

	}

	@Test
	public void testUploadPackageInfo_whenPackageTypeIsOtherAndPackageTypeOtherHasAValue() throws Exception {
		PackageInformation packageInformation = mock(PackageInformation.class);
		when(packageInformation.getPackageType()).thenReturn("Other");
		when(packageInformation.getPackageTypeOther()).thenReturn("my special package");
		PackageTypeOther packageTypeOther = new PackageTypeOther();
		when(uploadService.savePackageTypeOther("my special package")).thenReturn(packageTypeOther);
		when(uploadService.saveUploadPackage(packageInformation, packageTypeOther)).thenReturn(5);
		when(uploadService.saveSubmitterInfo(packageInformation)).thenReturn(55);
		when(uploadService.findInstitutionId(packageInformation)).thenReturn(66);

		UploadPackageIds packageIds = controller.uploadPackageInfo(packageInformation);

		verify(uploadService).saveUploadPackage(packageInformation, packageTypeOther);
		verify(uploadService).saveSubmitterInfo(packageInformation);
		verify(uploadService).findInstitutionId(packageInformation);
		verify(uploadService).savePackageTypeOther("my special package");
		assertEquals(5, packageIds.getPackageId());
		assertEquals(55, packageIds.getSubmitterId());
		assertEquals(66, packageIds.getInstitutionId());
	}

	@Test
	public void testHandleFileUpload_oneChunk() throws IllegalStateException, IOException {
		MultipartFile file = mock(MultipartFile.class);
		File savedFile = mock(File.class);
		when(fileHandler.saveMultipartFile(file, 1, "filename", false)).thenReturn(savedFile);

		controller.handleFileUpload(file, "fileMetadata", 1, 2, 3, "filename", 1, 0);

		ArgumentCaptor<UploadPackageIds> idCaptor = ArgumentCaptor.forClass(UploadPackageIds.class);
		ArgumentCaptor<File> fileCaptor = ArgumentCaptor.forClass(File.class);
		ArgumentCaptor<String> metadataCaptor = ArgumentCaptor.forClass(String.class);
		verify(uploadService).addFileToPackage(fileCaptor.capture(), metadataCaptor.capture(), idCaptor.capture());
		UploadPackageIds ids = idCaptor.getValue();
		assertEquals(1, ids.getPackageId());
		assertEquals(2, ids.getSubmitterId());
		assertEquals(3, ids.getInstitutionId());
		assertEquals(savedFile, fileCaptor.getValue());
		assertEquals("fileMetadata", metadataCaptor.getValue());
	}

	@Test
	public void testHandleFileUpload_secondChunk() throws IllegalStateException, IOException {
		MultipartFile file = mock(MultipartFile.class);
		File savedFile = mock(File.class);
		when(fileHandler.saveMultipartFile(file, 1, "filename", true)).thenReturn(savedFile);

		controller.handleFileUpload(file, "fileMetadata", 1, 2, 3, "filename", 2, 1);

		ArgumentCaptor<UploadPackageIds> idCaptor = ArgumentCaptor.forClass(UploadPackageIds.class);
		ArgumentCaptor<File> fileCaptor = ArgumentCaptor.forClass(File.class);
		ArgumentCaptor<String> metadataCaptor = ArgumentCaptor.forClass(String.class);
		verify(uploadService).addFileToPackage(fileCaptor.capture(), metadataCaptor.capture(), idCaptor.capture());
		UploadPackageIds ids = idCaptor.getValue();
		assertEquals(1, ids.getPackageId());
		assertEquals(2, ids.getSubmitterId());
		assertEquals(3, ids.getInstitutionId());
		assertEquals(savedFile, fileCaptor.getValue());
		assertEquals("fileMetadata", metadataCaptor.getValue());
	}

	@Test
	public void testHandleFileUpload_whenException() throws Exception {
		// doThrow(new
		// IOException("BANG")).when(uploadService).addFileToPackage(any(MultipartFile.class),
		// any(String.class), any(UploadPackageIds.class));
		//
		// try {
		// controller.handleFileUpload(mock(MultipartFile.class),
		// "fileMetadataString", 1, 2, 3);
		// fail("Should have thrown exception");
		// } catch (Exception expected) {
		// assertEquals("BANG", expected.getMessage());
		// }
	}

}
