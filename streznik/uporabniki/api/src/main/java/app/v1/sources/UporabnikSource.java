package app.v1.sources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Uporabnik;
import exceptions.SendEmailException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repositories.UporabnikRepository;
import requests.uporabnik.UporabnikRequest;
import response.uporabnik.UporabnikZGeslom;
import services.UporabnikService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UporabnikSource {
	
	@Inject
	private UporabnikService uporabnikService;
	
	@Inject
	private UporabnikRepository uporabnikRepository;
	
	@Context
	protected UriInfo uriInfo;
	
	@GET
	public Response getUporabniki() {
		QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
		List<Uporabnik> uporabniki = uporabnikService.poisciVseUporabnike(query);
		return Response.status(Response.Status.OK).entity(uporabniki).build();
	}
	
	@GET
	@Path("{id}")
	public Response getEnegaUporabnika(@PathParam("id") long id) {
		Uporabnik uporabnik = uporabnikService.poisciUporabnika(id);
		return Response.status(Response.Status.OK).entity(uporabnik).build();
	}
	
	@GET
	@Path("isci")
	public Response poisciZImenomInPriimkom(@QueryParam("q") String query) {
		List<Uporabnik> uporabniki = uporabnikService.poisciZNizom(query);
		return Response.status(Response.Status.OK).entity(uporabniki).build();
	}
	
	@POST
	public Response dodajUporabnika(UporabnikRequest uporabnikRequest) throws SendEmailException {
		uporabnikService.dodajUporabnika(uporabnikRequest);
		return Response.status(Response.Status.CREATED).entity(uporabnikRequest).build();
	}
	
	@GET
	@Path("email/{email}")
	public Response pridobiZEmailom(@PathParam("email") String email) {
		UporabnikZGeslom upb = uporabnikRepository.poisciUporabnikaZEmailom(email);
		return Response.status(Response.Status.OK).entity(upb).build();
	}
	
	/*@POST
	@Path("test")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response testiraj(@FormDataParam("slika")File file,
							 @FormDataParam("slika")FormDataContentDisposition file_meta,
							 @FormDataParam("naziv") String naziv,
							 @FormDataParam("naziv") double cena,
							 @FormDataParam("naziv") String opis){
		
		return Response.status(Response.Status.OK).entity(file.getAbsolutePath()).build();
	}*/
}
