package org.siddarth.javabrains.messenger.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.siddarth.javabrains.messenger.exception.DataNotFoundException;
import org.siddarth.javabrains.messenger.model.Comment;
import org.siddarth.javabrains.messenger.model.ErrorMessage;
import org.siddarth.javabrains.messenger.model.Message;
import org.siddarth.javabrains.messenger.model.Profile;

public class DatabaseClass {

	private static Map<Long, Message> messages = new HashMap<>();
	private static long lastMessageSize;

	private static Map<String, Profile> profiles = new HashMap<>();
	private static long lastProfileSize;

	private static Map<Long, Comment> comments = new HashMap<>();
	private static long lastCommentSize;

	public DatabaseClass() {/*
							 * Message m1, m2, m3, m4, m5, m6; Comment c1, c2, c3, c4; Map<Long, Comment>
							 * mc1, mc2, mc3; try { m1 = new Message(1L, "Hello World", "siddarth", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2019-08-15")); m2 = new Message(2L,
							 * "Hello Jersey", "siddarth", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2018-08-15")); m3 = new Message(3L,
							 * "Hello Nikki", "siddarth", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2017-08-15")); m4 = new Message(4L,
							 * "Hello World", "siddarth", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2019-08-15")); m5 = new Message(5L,
							 * "Hello Jersey", "siddarth", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2018-08-15")); m6 = new Message(6L,
							 * "Hello Nikki", "siddarth", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2017-08-15")); c1 = new Comment(1L, 1L,
							 * "message 1, comment 1", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2019-08-15"), "author 1"); c2 = new
							 * Comment(2L, 1L, "message 1, comment 2", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2019-08-15"), "author 2"); c3 = new
							 * Comment(3L, 2L, "message 2, comment 3", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2018-08-15"), "author 3"); c4 = new
							 * Comment(4L, 3L, "message 3, comment 4", new
							 * SimpleDateFormat("yyyy-MM-dd").parse("2019-08-15"), "author 4");
							 * comments.put(1L, c1); comments.put(2L, c2); comments.put(3L, c3);
							 * comments.put(4L, c4); mc1 = new HashMap<>(); mc2 = new HashMap<>(); mc3 = new
							 * HashMap<>(); mc1.put(1L, c1); mc1.put(2L, c2); mc2.put(3L, c3); mc3.put(4L,
							 * c4); m1.setComments(new ArrayList<>(mc1.values())); m2.setComments(new
							 * ArrayList<>(mc2.values())); m3.setComments(new ArrayList<>(mc3.values()));
							 * messages.put(1L, m1); messages.put(2L, m2); messages.put(3L, m3);
							 * messages.put(4L, m4); messages.put(5L, m5); messages.put(6L, m6);
							 *
							 * } catch (ParseException e) { e.printStackTrace(); } Profile p1 = new
							 * Profile(1L, "siddarth", "Siddarth", "Mishra");
							 * profiles.put(p1.getProfileName(), p1);
							 */
	}

	public static Map<Long, Message> getMessages() {
		return messages;
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}

	public Message getMessage(long id) {
		return messages.get(id);
	}

	public Message addMessage(Message message) throws Exception {
		if (lastMessageSize == 0) {
			lastMessageSize = messages.size();
		}
		long id = lastMessageSize + 1L;
		message.setId(id);
		if (!messages.containsKey(id)) {
			messages.put(message.getId(), message);
			lastMessageSize++;
		} else {
			throw new Exception("ID " + id + " already exists in database");
		}
		return messages.get(id);
	}

	public Message updateMessage(Message message) throws Exception {
		long id = message.getId();
		if (messages.containsKey(id))
			messages.put(id, message);
		else
			throw new DataNotFoundException("Message with ID " + id + " not found");
		return messages.get(id);
	}

	public Message deleteMessage(long id) {
		return messages.remove(id);
	}

	public static Map<String, Profile> getProfile() {
		return profiles;
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(getProfile().values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	// @SuppressWarnings("unlikely-arg-type")
	public Profile addProfile(Profile profile) throws Exception {
		if (lastProfileSize == 0) {
			lastProfileSize = profiles.size();
		}
		long id = lastProfileSize + 1L;
		profile.setId(id);
		String profileName = profile.getProfileName();
		if (!profiles.containsKey(profileName) /*&& !profiles.containsValue(id)*/) {
			profiles.put(profileName, profile);
			lastProfileSize++;
		} else {
			throw new Exception("profileName " + profileName + " / ID " + id + " already exists in database");
		}
		return profiles.get(profileName);
	}

	public Profile upateProfile(Profile profile) throws Exception {
		String profileName = profile.getProfileName();
		if (profiles.containsKey(profileName)) {
			long tempId = profiles.get(profileName).getId();
			Date tempDate = profiles.get(profileName).getCreated();
			profile.setId(tempId);
			profile.setCreated(tempDate);
			profiles.put(profileName, profile);
		} else {
			throw new Exception("profileName " + profileName + " not found in database");
		}
		return profiles.get(profileName);
	}

	public Profile deleteProfile(String profileName) throws Exception {
		if (!profiles.containsKey(profileName)) {
			throw new Exception("profileName " + profileName + " does not exists in database");
		}
		return profiles.remove(profileName);
	}

	public static Map<Long, Comment> getComments() {
		return comments;
	}

	public List<Comment> getComments(long messageId) {
		Message message = messages.get(messageId);
		if (message == null) {
			ErrorMessage errorMessage = new ErrorMessage("Message Id " + messageId + " Not Found", 404, "", null);
			Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
			throw new WebApplicationException(response);
		}
		return message.getComments();
	}

	public Comment getComment(long messageId, long commentId) {
		List<Comment> allComments = getComments(messageId);
		Comment comment = null;
		for (Comment c : allComments) {
			if (c.getCommentId() == commentId) {
				comment = c;
				break;
			}
		}
		if (comment == null) {
			ErrorMessage errorMessage = new ErrorMessage("Comment Id " + commentId + " Not Found", 404, "", null);
			Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
			throw new NotFoundException(response);
		}
		return comment;
	}

	public Comment addComment(Comment comment) throws Exception {
		long messageId = comment.getMessageId();
		Message message = validateMessage(messageId);
		if (lastCommentSize == 0) {
			lastCommentSize = comments.size();
		}
		long commentId = lastCommentSize + 1L;
		comment.setCommentId(commentId);
		if (!comments.containsKey(commentId)) {
			comments.put(comment.getCommentId(), comment);
			lastCommentSize++;
			addCommentToMessage(comment, message);
		} else {
			throw new Exception("Comment ID " + commentId + " already exists in database");
		}
		return comments.get(commentId);
	}

	private Message validateMessage(long messageId) throws Exception {
		Message message = messages.get(messageId);
		if (message == null) {
			throw new Exception("Message ID " + messageId + " doesn't exists in database");
		}
		return message;
	}

	private void addCommentToMessage(Comment comment, Message message) throws Exception {
		List<Comment> listComment = message.getComments();
		listComment.add(comment);
		updateMessage(message);
	}

	public Comment updateComment(Comment comment) throws Exception {
		long messageId = comment.getMessageId();
		Message message = validateMessage(messageId);
		long commentId = comment.getCommentId();
		Comment verifyComment = validateComment(commentId);
		validateMessageComment(messageId, commentId, verifyComment);
		comments.put(commentId, comment);
		Comment updatedComment = comments.get(commentId);
		List<Comment> listComment = message.getComments();
		removeCommentFromList(listComment, commentId);
		listComment.add(updatedComment);
		updateMessage(message);
		return updatedComment;
	}

	private void validateMessageComment(long messageId, long commentId, Comment verifyComment) throws Exception {
		if (verifyComment.getMessageId() != messageId) {
			throw new Exception("Comment ID " + commentId + " & Message ID " + messageId + " are not related");
		}
	}

	private Comment validateComment(long commentId) throws Exception {
		Comment verifyComment = comments.get(commentId);
		if (verifyComment == null) {
			throw new Exception("Comment ID " + commentId + " is not found in database");
		}
		return verifyComment;
	}

	private void removeCommentFromList(List<Comment> listComment, long commentId) {
		for (Comment c : listComment) {
			if (c.getCommentId() == commentId) {
				listComment.remove(c);
				break;
			}
		}
	}

	public Comment deleteComment(long messageId, long commentId) throws Exception {
		Message message = validateMessage(messageId);
		Comment verifyComment = validateComment(commentId);
		validateMessageComment(messageId, commentId, verifyComment);
		Comment removedComment = comments.remove(commentId);
		List<Comment> listComment = message.getComments();
		removeCommentFromList(listComment, commentId);
		updateMessage(message);
		return removedComment;
	}
}
