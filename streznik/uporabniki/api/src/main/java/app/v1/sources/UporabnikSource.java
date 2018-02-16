package app.v1.sources;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Uporabnik;
import exceptions.NiPravicException;
import exceptions.SendEmailException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import requests.uporabnik.UporabnikEmailRequest;
import requests.uporabnik.UporabnikRequest;
import response.Odgovor;
import response.uporabnik.UporabnikZGeslom;
import services.UporabnikService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikSource {
	
	@Inject
	private UporabnikService uporabnikService;
	
	@Context
	protected UriInfo uriInfo;
	
	private String kljucAvtentikacije;
	
	@PostConstruct
	private void init() {
		this.kljucAvtentikacije = ConfigurationUtil.getInstance().get("politika.avtentikacija-kljuc").orElse("");
	}
	
	// Metoda za pridobivanje vseh uporabnikov - uporabljaj query za performance!
	@GET
	public Response getUporabniki() {
		QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
		List<Uporabnik> uporabniki = uporabnikService.poisciVseUporabnike(query);
		return Response.status(Response.Status.OK).entity(uporabniki).build();
	}
	
	// Metoda za pridobivanje podatkov o enem uporabniku
	@Path("{id}")
	@GET
	public Response getEnegaUporabnika(@PathParam("id") long id){
		Uporabnik uporabnik = uporabnikService.poisciUporabnika(id);
		return Response.status(Response.Status.OK).entity(uporabnik).build();
	}
	
	// Metoda za iskanje uporabnikov
	@GET
	@Path("isci")
	public Response poisciZImenomInPriimkom(@QueryParam("q") String query) {
		List<Uporabnik> uporabniki = uporabnikService.poisciZNizom(query);
		return Response.status(Response.Status.OK).entity(uporabniki).build();
	}
	
	// Metoda za registracijo: shrani uporabnika in poslje potrditveni email
	@POST
	public Response dodajUporabnika(UporabnikRequest uporabnikRequest) throws SendEmailException, NiPravicException {
		if(uporabnikRequest.kljuc.equals(this.kljucAvtentikacije)) {
			uporabnikService.dodajUporabnika(uporabnikRequest, uriInfo.getBaseUri().toString());
			return Response.status(Response.Status.CREATED).entity(uporabnikRequest).build();
		} else {
			throw new NiPravicException("Dostop dovoljen samo drugi storitvi!");
		}
	}
	
	@GET
	@Path("potrdi/{kljuc}")
	public Response potrdiUporabnika(@PathParam("kljuc") String kljuc) {
		uporabnikService.potrdiUporabnikovEmail(kljuc);
		/*Odgovor odgovor = new Odgovor() {
			String sporocilo = "Uspeh!";
		};*/
		return Response.status(Response.Status.OK).entity(new Odgovor("OK")).build();
	}
	
	// Metoda za avtentikacijo: vrne uporabnika z poljem geslo za podani email
	@POST
	@Path("email")
	public Response pridobiZEmailom(UporabnikEmailRequest req) throws NiPravicException {
		if(req.kljuc.equals(this.kljucAvtentikacije)) {
			UporabnikZGeslom upb = uporabnikService.poisciZEmailom(req.email);
			return Response.status(Response.Status.OK).entity(upb).build();
		} else {
			throw new NiPravicException("Dostop dovoljen samo drugi storitvi!");
		}
	}
	
	@POST
	@Path("test")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response testiraj(
			@FormDataParam("slika") InputStream slika,
			@FormDataParam("slika") FormDataContentDisposition metadata,
			@FormDataParam("ime") String imeSlike) {
		
		System.out.println(imeSlike);
		System.out.println(metadata.getName());
		return Response.status(Response.Status.OK).entity("OK").build();
	}

}
