package app.v1.exceptions;

import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntitetaObstajaException implements ExceptionMapper<EntityExistsException> {
	@Override
	public Response toResponse(EntityExistsException exception) {
		ResponseObject obj = new ResponseObject();
		obj.status = Response.Status.CONFLICT.getStatusCode();
		obj.sporocilo = exception.getMessage();
		return Response.status(Response.Status.CONFLICT).entity(obj).build();
	}
}
