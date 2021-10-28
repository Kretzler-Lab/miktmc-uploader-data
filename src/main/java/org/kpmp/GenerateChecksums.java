package org.kpmp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kpmp.packages.CustomPackageRepository;
import org.kpmp.packages.Package;
import org.kpmp.packages.PackageKeys;
import org.kpmp.packages.PackageRepository;
import org.kpmp.packages.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateChecksums implements CommandLineRunner {

    CustomPackageRepository customPackageRepository;
    private PackageRepository packageRepository;
    private PackageService packageService;

    @Value("${generate-checksums}")
    private Boolean generateChecksums;

    @Autowired
    public GenerateChecksums(CustomPackageRepository customPackageRepository, PackageRepository packageRepository, PackageService packageService) {
        this.customPackageRepository = customPackageRepository;
        this.packageRepository = packageRepository;
        this.packageService = packageService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<JSONObject> jsonPackages = customPackageRepository.findAll();
        for (JSONObject packageInfo : jsonPackages) {
            packageInfo = packageService.calculateChecksums(packageInfo);
        }
    }
}
