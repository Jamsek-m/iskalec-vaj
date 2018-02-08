package services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Uporabnik;
import exceptions.SendEmailException;
import requests.uporabnik.UporabnikRequest;
import response.uporabnik.UporabnikZGeslom;

import java.util.List;

public interface UporabnikService {
	
	public List<Uporabnik> poisciVseUporabnike(QueryParameters query);
	
	public Uporabnik poisciUporabnika(long id);
	
	public List<Uporabnik> poisciZNizom(String niz);
	
	public UporabnikZGeslom poisciZEmailom(String email);
	
	public Uporabnik dodajUporabnika(UporabnikRequest req, String hostname) throws SendEmailException;
	
	public void potrdiUporabnikovEmail(String kljuc);
	
	public Uporabnik posodobiUporabnika(long id, UporabnikRequest req);
	
	public void izbrisiUporabnika(long id);
	
	public void nastaviZaModeratorja(long id);
	
	public void nastaviZaAdmina(long id);
	
	public void vzemiPraviceModeratorja(long id);
	
	public void vzemiPraviceAdmina(long id);
}
