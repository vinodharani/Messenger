package org.vino.restful.Messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.vino.restful.Messenger.model.CustomError;

@Provider /*
			 * Registers the mapper, so JAX RS knows that this mapper maps to
			 * this exception
			 */
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		CustomError customError = new CustomError(exception.getMessage(), 404, "http://www.errordocument.com");
		return Response.status(Status.NOT_FOUND).entity(customError).build();
	}
}
