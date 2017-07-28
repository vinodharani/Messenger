package org.vino.restful.Messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.vino.restful.Messenger.dao.MessengerDAO;
import org.vino.restful.Messenger.model.Comment;
import org.vino.restful.Messenger.model.CustomError;
import org.vino.restful.Messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = MessengerDAO.getMessages();

	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}

	public Comment getCommentByCommentId(long messageId, long commentId) {
		Message message = messages.get(messageId);
		
		if (message == null) {
			CustomError customError = new CustomError("MESSAGE NOT FOUND", 404, "http://www.errordocument.com");
			Response response = Response.status(Status.NOT_FOUND).entity(customError).build();
			
			throw new WebApplicationException(response);
		}
		
		Map<Long, Comment> comments = message.getComments();
		Comment comment = comments.get(commentId);
		
		if (comment == null) {
			CustomError customError = new CustomError("COMMENT NOT FOUND", 404, "http://www.errordocument.com");
			Response response = Response.status(Status.NOT_FOUND).entity(customError).build();
			
			throw new WebApplicationException(response);
		}
		
		return comment;
	}

	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);

		return comment;
	}

	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();

		if (comment.getId() < 0) {
			return null;
		}

		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
