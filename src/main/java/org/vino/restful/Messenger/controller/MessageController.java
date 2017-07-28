package org.vino.restful.Messenger.controller;

import java.net.URI;
import java.net.URISyntaxException;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.vino.restful.Messenger.controller.paramBean.MessageBean;
import org.vino.restful.Messenger.model.Message;
import org.vino.restful.Messenger.service.MessageService;

/**
 * Root resource (exposed at "messages" path)
 */
@Path("/messages")
/*
 * Content Negotiation. The below line is commented to show how method specific
 * content negotiation works.
 */
/* @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) */
@Produces(MediaType.APPLICATION_JSON) /*
										 * If every method in the class
										 * produces/consumes the same content
										 * type, you can annotate the class
										 * instead of every single method
										 */
@Consumes(MediaType.APPLICATION_JSON) /*
										 * Content type for the request and the
										 * response. (Consumes and Produces)
										 */
/*
 * To have the response content type as JSON, uncomment the moxy dependency in
 * your POM file
 */
public class MessageController {

	MessageService messageService = new MessageService();

	@GET /* Mapping the HTTP method to the JAVA method */
	/*
	 * public List<Message> getMessages(@QueryParam("year") int
	 * year, @QueryParam("start") int start,
	 * 
	 * @QueryParam("size") int size) {
	 */
	/*
	 * The above code is commented because @BeanParam makes it easier to handle
	 * multiple parameters
	 */
	/* @BeanParam makes the code more readable and clean */
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessagesAsJSON(@BeanParam MessageBean messageBean) {
		System.out.println("JSON Method Called");
		/* Filtering based on year */
		if (messageBean.getYear() > 0) {
			return messageService.getMessageForYear(messageBean.getYear());
			/* Filtering based on size aka pagination */
		} else if (messageBean.getStart() > 0 || messageBean.getSize() > 0) {
			return messageService.getPaginatedMessages(messageBean.getStart(), messageBean.getSize());
		}

		return messageService.getAllMessages();
	}
	
	/*
	 * getMessagesAsJSON & getMessagesAsXML is the same methods except it
	 * produces different MediaType as response. They will be picked
	 * automatically by Jersey depending on the type requested by the client in
	 * the request header
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getMessagesAsXML(@BeanParam MessageBean messageBean) {
		System.out.println("XML Method Called");
		/* Filtering based on year */
		if (messageBean.getYear() > 0) {
			return messageService.getMessageForYear(messageBean.getYear());
			/* Filtering based on size aka pagination */
		} else if (messageBean.getStart() > 0 || messageBean.getSize() > 0) {
			return messageService.getPaginatedMessages(messageBean.getStart(), messageBean.getSize());
		}

		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}") /*
							 * This method will be mapped to
							 * /messages/{messageId}
							 */
	public Message getSpecificMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		/*
		 * PathParam maps the value in the URI to the variable in the argument
		 * of this method
		 */
		/*
		 * Jersey can convert the String in the URI to long or whatever data
		 * type you like
		 */

		/*
		 * The below message instance will be converted to JSON to be sent as a
		 * response
		 */
		Message message = messageService.getMessage(id);

		/* HATEOAS Implementation */
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComment(uriInfo, message), "comment");
		
		return message;
	}

	private String getUriForComment(UriInfo uriInfo, Message message) {
		/*
		 * Comment is a sub resource to Message and does not have a defined path
		 */
		/*
		 * To get the path of the comment, use the overloaded path method and
		 * point to the MessageController's method that redirects to
		 * CommentController
		 */
		/*
		 * Use the resolveTemplate method to replace the commentId string in the
		 * URI with the actual commentId value of the message
		 */
		String uri = uriInfo.getBaseUriBuilder().path(MessageController.class)
				.path(MessageController.class, "redirectToCommentController")
				.resolveTemplate("messageId", message.getId()).path(CommentController.class).build().toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(ProfileController.class).path(message.getAuthor()).build()
				.toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(MessageController.class).path(Long.toString(message.getId())).build()
				.toString();
	}

	// @POST /* Adding a message */
	/*
	 * The JSON sent is automatically converted to a Message object and sent as
	 * an argument to this method
	 */
	/* public Message addMessage(Message message) {
		return messageService.addMessage(message);
		 A message instance is converted to JSON and sent as a response 
	} */

	/* The above message is one way of doing POST where we add a message */

	/*
	 * This method not only creates a message and sends it as a response, but
	 * also send status code as a part of the response to the client.
	 */
	/*
	 * This method returns a Response object instead of a Message object.
	 * Response object can be created as shown as below
	 */
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message addedMessage = messageService.addMessage(message);

		/* This response has status code & message instance in the response */
		/*
		 * return Response.status(Status.CREATED).entity(addedMessage).build();
		 */

		/* A better alternative to the above line is below */
		/*
		 * .created method not only sets the response status code to created
		 * (201) but also can attach the URI of the newly created message
		 * instance. Also, this can throw URISyntaxException
		 */
		/*
		 * return Response.created(new URI("/messenger/webapi/messages/" +
		 * addedMessage.getId())).entity(addedMessage) .build();
		 */

		/*
		 * The above code is not best practice again because the entire URI is
		 * hardcoded. A better alternative is below.
		 */
		/*
		 * Use @Context to the UriInfo. Use UriInfo to get the absolute path and
		 * create a new URI for the newly added resource.
		 * .getAbsolutePathBuilder adds on to the absolutePath
		 */
		String messageId = String.valueOf(addedMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(messageId).build();
		return Response.created(uri).entity(addedMessage).build();
	}
	
	@PUT /* Updating a message */
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE /* Deleting a message */
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	@Path("/{messageId}/comments")
	public CommentController redirectToCommentController() {
		return new CommentController();
	}
}