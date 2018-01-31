package api.v1.exceptionmappers;

import entitete.exceptions.SendErrorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SendErrorMapper implements ExceptionMapper<SendErrorException> {
	@Override
	public Response toResponse(SendErrorException exception) {
		ExceptionMapperObject obj = new ExceptionMapperObject(
				Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
				exception.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(obj).build();
	}
}
