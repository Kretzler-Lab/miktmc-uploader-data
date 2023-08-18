package org.kpmp.packages;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.kpmp.dmd.DmdResponse;
import org.kpmp.dmd.DmdService;
import org.kpmp.globus.GlobusService;
import org.kpmp.logging.LoggingService;
import org.kpmp.shibboleth.ShibbolethUserService;
import org.kpmp.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PackageController {

	@Value("${package.state.files.received}")
	private String filesReceivedState;
	@Value("${package.state.upload.started}")
	private String uploadStartedState;
	@Value("${package.state.metadata.received}")
	private String metadataReceivedState;
	@Value("${package.state.upload.failed}")
	private String uploadFailedState;

	@Value("${package.state.upload.succeeded}")
	private String uploadSucceededState;

	private static final MessageFormat finish = new MessageFormat("{0} {1}");
	private static final MessageFormat fileUploadRequest = new MessageFormat(
			"Posting file: {0} to package with id: {1}, filesize: {2}, chunk: {3} out of {4} chunks");
	private static final MessageFormat fileDownloadRequest = new MessageFormat(
			"Requesting package download with id {0}, filename {1}");

	private LoggingService logger;
	private PackageService packageService;
	private ShibbolethUserService shibUserService;
	private UniversalIdGenerator universalIdGenerator;
	private GlobusService globusService;

	private DmdService dmdService;

	@Autowired
	public PackageController(PackageService packageService, LoggingService logger,
			ShibbolethUserService shibUserService, UniversalIdGenerator universalIdGenerator,
			GlobusService globusService, DmdService dmdService) {
		this.packageService = packageService;
		this.logger = logger;
		this.shibUserService = shibUserService;
		this.universalIdGenerator = universalIdGenerator;
		this.globusService = globusService;
		this.dmdService = dmdService;
	}

	@RequestMapping(value = "/v1/packages", method = RequestMethod.GET)
	public @ResponseBody List<PackageView> getAllPackages(HttpServletRequest request)
			throws JSONException, IOException {
		logger.logInfoMessage(this.getClass(), null, "Request for all packages", request);
		return packageService.findAllPackages();
	}

	@RequestMapping(value = "/v1/packages", method = RequestMethod.POST)
	public @ResponseBody PackageResponse postPackageInformation(@RequestBody String packageInfoString,
			@RequestParam("hostname") String hostname, HttpServletRequest request) {
		String cleanHostName = hostname.replace("=", "");
		PackageResponse packageResponse = new PackageResponse();
		String packageId = universalIdGenerator.generateUniversalId();
		packageResponse.setPackageId(packageId);
		packageService.sendStateChangeEvent(packageId, uploadStartedState, null, cleanHostName);
		JSONObject packageInfo;
		try {
			packageInfo = new JSONObject(packageInfoString);
			packageInfo.put("largeFilesChecked", true);
			logger.logInfoMessage(this.getClass(), packageId, "Posting package info: " + packageInfo, request);
			User user = shibUserService.getUser(request);
			packageService.savePackageInformation(packageInfo, user, packageId);
			String largeFilesChecked = packageInfo.optBoolean("largeFilesChecked") ? "true" : "false";
			if ("true".equals(largeFilesChecked)) {
				packageResponse.setGlobusURL(globusService.createDirectory(packageId));
			}
			packageService.sendStateChangeEvent(packageId, metadataReceivedState, largeFilesChecked,
					packageResponse.getGlobusURL(), cleanHostName);
		} catch (Exception e) {
			logger.logErrorMessage(this.getClass(), packageId, e.getMessage(), request);
			packageService.sendStateChangeEvent(packageId, uploadFailedState, null, e.getMessage(), cleanHostName);
		}
		return packageResponse;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/v1/packages/{packageId}/files/move", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity movePackageFiles(@PathVariable String packageId, HttpServletRequest request) {
		ResponseEntity responseEntity;
		DmdResponse dmdResponse;
		try {
			logger.logInfoMessage(this.getClass(), packageId, "Moving files for package " + packageId, request);
			dmdResponse = dmdService.moveFiles(packageId);
			if (dmdResponse.isSuccess()) {
				String successMessage = "The following files were moved successfully: " + String.join(",", dmdResponse.getFileNameList());
				logger.logInfoMessage(this.getClass(), packageId, successMessage, request);
				responseEntity = ResponseEntity.ok().body(successMessage);
			} else {
				logger.logErrorMessage(this.getClass(), packageId, dmdResponse.getMessage(), request);
				responseEntity = ResponseEntity.status(INTERNAL_SERVER_ERROR).body("The following problem occurred while moving the files: " + dmdResponse.getMessage());
			}
		} catch (IOException e) {
			logger.logErrorMessage(this.getClass(), packageId, e.getMessage(), request);
			responseEntity = ResponseEntity.status(INTERNAL_SERVER_ERROR).body("There was a server error while moving the files.");
		}
		return responseEntity;
	}
	private boolean shouldAppend(int chunk) {
		return chunk != 0;
	}

}
