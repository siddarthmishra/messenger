package org.siddarth.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.siddarth.javabrains.messenger.database.DatabaseClass;
import org.siddarth.javabrains.messenger.model.Message;

public class MessageService {

	DatabaseClass databaseClass = new DatabaseClass();

	public List<Message> getAllMessages() {
		return databaseClass.getAllMessages();
	}

	public List<Message> getAllMessagesForYear(List<Message> messages, int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}

	public List<Message> getAllMessagesPaginated(List<Message> messages, int start, int size) {
		if (start + size > messages.size())
			return new ArrayList<Message>();
		return messages.subList(start, start + size);
	}

	public List<Message> getAllMessagesForYearPaginated(List<Message> messages, int year, int start, int size) {
		List<Message> messagesForYear = new ArrayList<>(getAllMessagesForYear(messages, year));
		return new ArrayList<>(getAllMessagesPaginated(messagesForYear, start, size));
	}

	public Message getMessage(long id) {
		return databaseClass.getMessage(id);
	}

	public Message addMessage(Message message) throws Exception {
		return databaseClass.addMessage(message);
	}

	public Message updateMessage(Message message) throws Exception {
		return databaseClass.updateMessage(message);
	}

	public Message deleteMessage(long id) {
		return databaseClass.deleteMessage(id);
	}

}
