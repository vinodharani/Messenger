package org.vino.restful.Messenger.dao;

import java.util.HashMap;
import java.util.Map;

import org.vino.restful.Messenger.model.Message;
import org.vino.restful.Messenger.model.Profile;

public class MessengerDAO {

	private static Map<Long, Message> messages = new HashMap<Long, Message>();
	private static Map<String, Profile> profiles = new HashMap<String, Profile>();

	public static Map<Long, Message> getMessages() {
		return messages;
	}

	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
}
