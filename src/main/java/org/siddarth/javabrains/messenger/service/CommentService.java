package org.siddarth.javabrains.messenger.service;

import java.util.List;

import org.siddarth.javabrains.messenger.database.DatabaseClass;
import org.siddarth.javabrains.messenger.model.Comment;

public class CommentService {

	DatabaseClass databaseClass = new DatabaseClass();

	public List<Comment> getComments(long messageId) {
		return databaseClass.getComments(messageId);
	}

	public Comment getComments(long messageId, long commentId) {
		return databaseClass.getComment(messageId, commentId);
	}

	public Comment addComment(Comment comment) throws Exception {
		return databaseClass.addComment(comment);
	}

	public Comment updateComment(Comment comment) throws Exception {
		return databaseClass.updateComment(comment);
	}

	public Comment deleteComment(long messageId, long commentId) throws Exception {
		return databaseClass.deleteComment(messageId, commentId);
	}

}
