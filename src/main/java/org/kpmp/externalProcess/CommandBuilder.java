package org.kpmp.externalProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kpmp.packages.FilePathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandBuilder {

	private FilePathHelper filePathHelper;

	@Autowired
	public CommandBuilder(FilePathHelper filePathHelper) {
		this.filePathHelper = filePathHelper;
	}

	public String[] buildZipCommand(String packageId, String metadataJson) {
		List<String> commandArgs = new ArrayList<>();
		commandArgs.add("java");
		commandArgs.add("-jar");
		commandArgs.add("/home/gradle/zipWorker/zipWorker.jar");

		String packagePath = filePathHelper.getPackagePath(packageId);
		List<String> fileNames = filePathHelper.getFilenames(packagePath);
		for (String fileName : fileNames) {
			commandArgs.add("--zip.fileNames=" + packagePath + File.separator + fileName);
		}

		String zipFileName = filePathHelper.getZipFileName(packageId);
		commandArgs.add("--zip.zipFilePath=" + zipFileName);
		commandArgs.add("--zip.additionalFileData=metadata.json|" + metadataJson);

		return commandArgs.toArray(new String[0]);
	}
}