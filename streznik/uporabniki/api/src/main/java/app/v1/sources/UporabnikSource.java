package app.v1.sources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Uporabnik;
import requests.uporabnik.UporabnikRequest;
import services.UporabnikService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UporabnikSource {
	
	@Inject
	private UporabnikService uporabnikService;
	
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
	public Response dodajUporabnika(UporabnikRequest uporabnikRequest) {
		try {
			uporabnikService.dodajUporabnika(uporabnikRequest);
			return Response.status(Response.Status.CREATED).entity(uporabnikRequest).build();
		} catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Napaka pri emailu!").build();
		}
	}
}
