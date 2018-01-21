package app.v1.exceptions;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SlabaZahtevaException implements ExceptionMapper<BadRequestException> {
	@Override
	public Response toResponse(BadRequestException exception) {
		ResponseObject obj = new ResponseObject();
		obj.status = Response.Status.BAD_REQUEST.getStatusCode();
		obj.sporocilo = exception.getMessage();
		return Response.status(Response.Status.BAD_REQUEST).entity(obj).build();
	}
}
