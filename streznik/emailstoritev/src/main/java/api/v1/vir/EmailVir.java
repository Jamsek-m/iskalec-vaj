package api.v1.vir;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import entitete.EmailRequest;
import entitete.EmailResponse;
import entitete.exceptions.NiTipaException;
import entitete.exceptions.SendErrorException;
import service.EmailService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class EmailVir {
	
	@Inject
	private EmailService emailService;
	
	private static final Logger LOG = LogManager.getLogger(EmailVir.class.getName());
	
	@POST
	public Response posljiEmail(EmailRequest req) throws NiTipaException, SendErrorException {
		LOG.info("prejet zahtevek za e-mail!");
		
		emailService.handleEmail(req);
		return Response.status(Response.Status.CREATED).entity(new EmailResponse(
				Response.Status.CREATED.getStatusCode(),
				"Email je bil poslan!")
		).build();
	}
	
}
