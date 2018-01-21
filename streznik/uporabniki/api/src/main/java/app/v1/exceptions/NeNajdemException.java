package app.v1.exceptions;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeNajdemException implements ExceptionMapper<NotFoundException> {
	@Override
	public Response toResponse(NotFoundException exception) {
		ResponseObject obj = new ResponseObject();
		obj.status = Response.Status.NOT_FOUND.getStatusCode();
		obj.sporocilo = "Zahtevana eniteta ne obstaja! Preverite URL";
		return Response.status(Response.Status.NOT_FOUND).entity(obj).build();
	}
}
