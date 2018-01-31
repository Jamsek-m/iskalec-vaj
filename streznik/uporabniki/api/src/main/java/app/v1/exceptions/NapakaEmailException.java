package app.v1.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NapakaEmailException extends Throwable implements ExceptionMapper<NapakaEmailException>{
	@Override
	public Response toResponse(NapakaEmailException exception) {
		ResponseObject obj = new ResponseObject();
		obj.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		obj.sporocilo = "Napaka pri pošiljanju potrditvenega e-maila!";
		return Response.status(Response.Status.NOT_FOUND).entity(obj).build();
	}
}
