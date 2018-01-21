package services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Uporabnik;
import requests.uporabnik.UporabnikRequest;

import java.util.List;

public interface UporabnikService {
	
	public List<Uporabnik> poisciVseUporabnike(QueryParameters query);
	
	public Uporabnik poisciUporabnika(long id);
	
	public Uporabnik dodajUporabnika(UporabnikRequest req);
	
}
