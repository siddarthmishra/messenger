package org.siddarth.javabrains.messenger.resources;

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

@Path("injectDemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	// Matrix Param uses semi-colon (;)
	// http://localhost:8080/messenger/webapi/injectDemo/annotations;param=value

	// Header Param passed as headers (Header - Value)

	// Cookie Param from the Cookies Tab

	// @FormParam : to access form submissions sent from HTML form to REST API

	@GET
	@Path("annotations")
	public String getParamUsingAnnatations(@MatrixParam("param") String matrixParam,
			@HeaderParam("customHeaderValue") String hearder, @CookieParam("JSESSIONID") String cookie) {
		return "Matrix Param " + matrixParam + "\nHeader Param " + hearder + "\nCookie Param " + cookie;
	}

	/*
	 * Above methods can be tedious when we have more number of parameters. Other
	 * challenge could be that you need to know the param name beforehand e.g for
	 * cookie param JSESSIONID. You need to know beforehand that JSESSIONID exists.
	 * What if you want to access all values of Headers or all the cookies. This can
	 * be done using context. @Context is a special annotation that applies to a few
	 * classes one of them being UriInfo
	 */

	@GET
	@Path("context")
	public String getParamUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
		StringBuffer sb = new StringBuffer();
		sb.append(uriInfo.getAbsolutePath().toString() + "\n");
		sb.append(httpHeaders.getCookies().toString() + "\n");
		return sb.toString();
	}

	/*
	 * There is 3rd way to access these parameters and thats using Bean Param.It
	 * works is you create separate bean which has all these annotations are placed
	 * and then in your resource class you don't include all these annotations. You
	 * just access using bean param. It is demonstrated in MessageResource.java
	 */

}
