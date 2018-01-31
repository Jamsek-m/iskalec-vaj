package app.v1.exceptions;

import exceptions.SendEmailException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NapakaEmailException implements ExceptionMapper<SendEmailException>{
	@Override
	public Response toResponse(SendEmailException exception) {
		ResponseObject obj = new ResponseObject();
		obj.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		obj.sporocilo = exception.getMessage();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(obj).build();
	}
}
