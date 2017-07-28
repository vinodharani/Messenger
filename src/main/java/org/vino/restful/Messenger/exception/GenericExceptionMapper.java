package org.vino.restful.Messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.vino.restful.Messenger.model.CustomError;

/* This is a bad practice. Catching all the exceptions in one place is not a good idea */
/* The provider annotations is commented to make the WebApplicationException to work */
// @Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		CustomError customError = new CustomError(exception.getMessage(), 500, "http://www.errordocument.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(customError).build();
	}
}
