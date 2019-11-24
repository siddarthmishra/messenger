package org.siddarth.javabrains.messenger.service;

import java.util.List;

import org.siddarth.javabrains.messenger.database.DatabaseClass;
import org.siddarth.javabrains.messenger.model.Profile;

public class ProfileService {

	DatabaseClass databaseClass = new DatabaseClass();

	public List<Profile> getAllProfiles() {
		return databaseClass.getAllProfiles();
	}

	public Profile getProfile(String profileName) {
		return databaseClass.getProfile(profileName);
	}

	public Profile addProfile(Profile profile) throws Exception {
		return databaseClass.addProfile(profile);
	}

	public Profile updateProfile(Profile profile) throws Exception {
		return databaseClass.upateProfile(profile);
	}

	public Profile deleteProfile(String profileName) throws Exception {
		return databaseClass.deleteProfile(profileName);
	}
}