package org.vino.restful.Messenger.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/demo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class DemoController {

	@GET
	@Path("otherParam")
	public String demoOfOtherParam(@MatrixParam("matParam") String matrixParam,
			@HeaderParam("headParam") String headerParam, @CookieParam("cooParam") String cookieParam) {
		return "Matrix Parameter received: " + matrixParam + "\n Header Param received: " + headerParam
				+ "\n Cookie Param received: " + cookieParam;
	}

	@GET
	@Path("context")
	public String demoOfContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
		String absolutePath = uriInfo.getAbsolutePath()
				.toString(); /*
								 * Many useful methods are found in both UriInfo &
								 * HttpHeaders
								 */
		String cookies = httpHeaders.getCookies().toString();

		return "Absolute Path receiver: " + absolutePath + "\nCookie via HTTP Headers: " + cookies;
	}
}
