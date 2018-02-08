package app.v1.exceptions;

import exceptions.NiPravicException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NiDovoljenjaException implements ExceptionMapper<NiPravicException> {
	
	@Override
	public Response toResponse(NiPravicException exception) {
		ResponseObject obj = new ResponseObject();
		obj.status = Response.Status.UNAUTHORIZED.getStatusCode();
		obj.sporocilo = exception.getMessage();
		return Response.status(Response.Status.UNAUTHORIZED).entity(obj).build();
	}
}
