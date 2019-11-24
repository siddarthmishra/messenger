package org.siddarth.javabrains.messenger.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.siddarth.javabrains.messenger.model.Comment;
import org.siddarth.javabrains.messenger.resources.beans.MessageFilterBean;
import org.siddarth.javabrains.messenger.service.CommentService;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getComments(@BeanParam MessageFilterBean filterBean) {
		long messageId = filterBean.getMessageId();
		return commentService.getComments(messageId);
	}

	@GET
	@Path("{commentId}")
	public Comment getComment(@BeanParam MessageFilterBean filterBean) {
		long messageId = filterBean.getMessageId();
		long commentId = filterBean.getCommentId();
		return commentService.getComments(messageId, commentId);
	}

	@POST
	public Comment addComment(@BeanParam MessageFilterBean filterBean, Comment comment) throws Exception {
		long messageId = filterBean.getMessageId();
		comment.setCreated(new Date());
		comment.setMessageId(messageId);
		return commentService.addComment(comment);
	}

	@PUT
	@Path("{commentId}")
	public Comment updateComment(@BeanParam MessageFilterBean filterBean, Comment comment) throws Exception {
		long messageId = filterBean.getMessageId();
		long commentId = filterBean.getCommentId();
		comment.setCreated(new Date());
		comment.setMessageId(messageId);
		comment.setCommentId(commentId);
		return commentService.updateComment(comment);
	}

	@DELETE
	@Path("{commentId}")
	public String deleteComment(@BeanParam MessageFilterBean filterBean) throws Exception {
		long messageId = filterBean.getMessageId();
		long commentId = filterBean.getCommentId();
		Comment deletedComment = commentService.deleteComment(messageId, commentId);
		return "Comment Id " + deletedComment.getCommentId() + " for Message Id " + deletedComment.getMessageId()
		+ " deleted successfully";
	}

}
