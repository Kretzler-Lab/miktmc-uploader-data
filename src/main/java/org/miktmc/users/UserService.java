package org.miktmc.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.miktmc.packages.Package;
import org.miktmc.packages.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private PackageRepository packageRepository;
	private UserRepository userRepository;

	@Autowired
	public UserService(PackageRepository packageRepository, UserRepository userRepository) {
		this.packageRepository = packageRepository;
		this.userRepository = userRepository;
	}

	public List<User> findAllWithPackages() {
		List<Package> packages = packageRepository.findAll();
		Map<String, User> users = new HashMap<>();

		for (Package aPackage : packages) {
			users.put(aPackage.getSubmitter().getId(), aPackage.getSubmitter());
		}

		return new ArrayList<>(users.values());
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
}
