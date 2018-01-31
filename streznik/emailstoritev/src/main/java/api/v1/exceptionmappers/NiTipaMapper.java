package api.v1.exceptionmappers;

import entitete.exceptions.NiTipaException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NiTipaMapper implements ExceptionMapper<NiTipaException> {
	@Override
	public Response toResponse(NiTipaException exception) {
		ExceptionMapperObject obj = new ExceptionMapperObject(
				Response.Status.BAD_REQUEST.getStatusCode(),
				exception.getMessage());
		return Response.status(Response.Status.BAD_REQUEST).entity(obj).build();
	}
}
