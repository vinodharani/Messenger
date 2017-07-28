package org.vino.restful.Messenger.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.vino.restful.Messenger.dao.MessengerDAO;
import org.vino.restful.Messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = MessengerDAO.getProfiles();

	public ProfileService() {
		profiles.put("Vino", new Profile(1L, "Vino", "Vinodhbabu", "Dharani"));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);

		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}

		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public void removeProfile(String profileName) {
		profiles.remove(profileName);
	}
}
