package org.siddarth.javabrains.messenger.model;

import java.util.Date;

public class Comment {

	private long commentId;
	private long messageId;
	private String commentContent;
	private Date created;
	private String author;

	public Comment() {
	}

	public Comment(long commentId, long messageId, String commentContent, Date created, String author) {
		this.commentId = commentId;
		this.messageId = messageId;
		this.commentContent = commentContent;
		this.created = created;
		this.author = author;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", messageId=" + messageId + ", commentContent=" + commentContent
				+ ", created=" + created + ", author=" + author + "]";
	}
}
