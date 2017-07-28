package org.vino.restful.Messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.vino.restful.Messenger.dao.MessengerDAO;
import org.vino.restful.Messenger.exception.DataNotFoundException;
import org.vino.restful.Messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = MessengerDAO.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1L, "Hello World!", "Vino"));
		messages.put(2L, new Message(2L, "Hello Jersey!", "Bob"));
		messages.put(3L, new Message(3L, "Hello KC!", "Aaron"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}

	public Message getMessage(long id) {
		Message message = messages.get(id);
		
		if (message == null) {
			throw new DataNotFoundException("Message with ID as " + id + " could not be found.");
		}
		
		return message;
	}

	public List<Message> getMessageForYear(int year) {
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar calendar = Calendar.getInstance();

		for (Message message : messages.values()) {
			calendar.setTime(message.getCreated());

			if (calendar.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}

		return messagesForYear;
	}

	public List<Message> getPaginatedMessages(int start, int size) {
		List<Message> paginatedMessages = new ArrayList<Message>(messages.values());

		if (start + size > paginatedMessages.size()) {
			return new ArrayList<Message>();
		}

		return paginatedMessages.subList(start, start + size);
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);

		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}

		messages.put(message.getId(), message);
		return message;
	}

	public void removeMessage(long id) {
		messages.remove(id);
	}
}
