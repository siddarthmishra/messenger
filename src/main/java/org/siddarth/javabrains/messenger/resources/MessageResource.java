package org.siddarth.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.siddarth.javabrains.messenger.exception.DataNotFoundException;
import org.siddarth.javabrains.messenger.model.Message;
import org.siddarth.javabrains.messenger.resources.beans.MessageFilterBean;
import org.siddarth.javabrains.messenger.service.MessageService;

@Path("messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageService messageService = new MessageService();

	// @GET
	// http://localhost:8080/messenger/webapi/messages?year=0&start=0&size=2
	// http://localhost:8080/messenger/webapi/messages
	public List<Message> getAllMessages(@QueryParam("year") int year, @QueryParam("start") int start,
			@QueryParam("size") int size) {
		List<Message> messages = messageService.getAllMessages();
		if (year > 0 && start >= 0 & size > 0)
			return messageService.getAllMessagesForYearPaginated(messages, year, start, size);
		if (year > 0 && start == 0 && size == 0)
			return messageService.getAllMessagesForYear(messages, year);
		if (year == 0 && start >= 0 & size > 0)
			return messageService.getAllMessagesPaginated(messages, start, size);
		return messages;
	}

	@GET
	public List<Message> getAllMessages(@BeanParam MessageFilterBean filterBean) {
		List<Message> messages = messageService.getAllMessages();
		int year = filterBean.getYear();
		int start = filterBean.getStart();
		int size = filterBean.getSize();
		if (year > 0 && start >= 0 & size > 0)
			return messageService.getAllMessagesForYearPaginated(messages, year, start, size);
		if (year > 0 && start == 0 && size == 0)
			return messageService.getAllMessagesForYear(messages, year);
		if (year == 0 && start >= 0 & size > 0)
			return messageService.getAllMessagesPaginated(messages, start, size);
		return messages;
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		if (message == null) {
			throw new DataNotFoundException("Message with ID " + id + " not found");
		}
		String messageId = Long.toString(message.getId());
		String selfUrl = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(messageId).build().toString();
		message.addLink(selfUrl, "self");
		String profileName = message.getAuthor();
		String profileUrl = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(profileName).build()
				.toString();
		message.addLink(profileUrl, "profile");
		String commentUrl = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(MessageResource.class, "getCommentsResource").resolveTemplate("messageId", messageId).build()
				.toString();
		message.addLink(commentUrl, "comments");
		return message;
	}

	@POST
	public Response addMessage(@Context UriInfo uriInfo, Message message) throws Exception {
		message.setId(0);
		Message createdMessage = messageService.addMessage(message);
		String id = String.valueOf(createdMessage.getId());
		URI location = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(location).entity(createdMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) throws Exception {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public String deleteMessage(@PathParam("messageId") long id) {
		Message message = messageService.deleteMessage(id);
		return "Message with id " + message.getId() + " deleted successfully";
	}

	@Path("{messageId}/comments")
	public CommentResource getCommentsResource() {
		return new CommentResource();
	}

}
