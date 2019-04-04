package org.kpmp;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.kpmp.packages.CustomPackageRepository;
import org.kpmp.packages.FilePathHelper;
import org.kpmp.packages.PackageKeys;
import org.kpmp.packages.PackageZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "org.kpmp" })
public class RegenerateZipFiles implements CommandLineRunner {

	private CustomPackageRepository packageRepository;
	private PackageZipService zipService;
	private FilePathHelper pathHelper;

	@Autowired
	public RegenerateZipFiles(CustomPackageRepository packageRepository, PackageZipService zipService,
			FilePathHelper pathHelper) {
		this.packageRepository = packageRepository;
		this.zipService = zipService;
		this.pathHelper = pathHelper;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RegenerateZipFiles.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean needsRegenerateZip = true;
		List<JSONObject> jsons = packageRepository.findAll(needsRegenerateZip);
		for (JSONObject packageInfo : jsons) {
			String packageId = packageInfo.getString(PackageKeys.PACKAGE_ID.getKey());
			String zipFileName = pathHelper.getZipFileName(packageId);
			if (packageInfo.getBoolean(PackageKeys.REGENERATE_ZIP.getKey())) {
				try {
					packageInfo.remove(PackageKeys.REGENERATE_ZIP.getKey());
					zipService.createZipFile(packageInfo.toString());
					packageRepository.updateField(packageId, PackageKeys.REGENERATE_ZIP.getKey(), false);
				} catch (IOException e) {
					System.err.println("Unable to delete file, invalid permissions: " + zipFileName);
				}
			}
		}
	}
}
