package services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Letnik;
import entities.uporabnik.StatusUporabnik;
import entities.uporabnik.Uporabnik;
import entities.uporabnik.Vloga;
import repositories.LetnikRepository;
import repositories.StatusUporabnikRepository;
import repositories.UporabnikRepository;
import repositories.VlogaRepository;
import requests.uporabnik.UporabnikRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.ws.rs.BadRequestException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UporabnikServiceImpl implements UporabnikService {
	
	@Inject
	private UporabnikRepository uporabnikRepository;
	
	@Inject
	private StatusUporabnikRepository statusUporabnikRepository;
	
	@Inject
	private VlogaRepository vlogaRepository;
	
	@Inject
	private LetnikRepository letnikRepository;
	
	@Override
	public List<Uporabnik> poisciVseUporabnike(QueryParameters query) {
		return uporabnikRepository.pridobiVseUporabnike(query);
	}
	
	@Override
	public Uporabnik poisciUporabnika(long id) {
		return uporabnikRepository.pridobiUporabnika(id);
	}
	
	@Override
	public Uporabnik dodajUporabnika(UporabnikRequest req) {
		if(jeNeveljavenEmail(req.email)) {
			throw new EntityExistsException("E-mail ze obstaja!");
		}
		if(!req.geslo1.equals(req.geslo2)) {
			throw new BadRequestException("Gesli se ne ujemata!");
		}
		Uporabnik uporabnik = extractUporabnik(req);
		uporabnikRepository.dodajUporabnika(uporabnik);
		return uporabnik;
	}
	
	private boolean jeNeveljavenEmail(String email) {
		return uporabnikRepository.prestejUporabnikeZEmailom(email) != 0;
	}
	
	private Uporabnik extractUporabnik(UporabnikRequest req) {
		Letnik letnik = letnikRepository.pridobiEnLetnik(req.letnik);
		Uporabnik uporabnik = new Uporabnik();
		uporabnik.setEmail(req.email);
		uporabnik.setGeslo(req.geslo1);
		uporabnik.setIme(req.ime);
		uporabnik.setPriimek(req.priimek);
		uporabnik.setUporabniskoIme(req.uporabniskoIme);
		uporabnik.setLetnik(letnik);
		uporabnik.setStatus(statusUporabnikRepository.pridobiStatusSSifro("NOT_CONFIRMED"));
		uporabnik.setVloge(new HashSet<>());
		Vloga vlogaUser = vlogaRepository.pridobiStatusSSifro("USER");
		uporabnik.getVloge().add(vlogaUser);
		return uporabnik;
	}
}
