package services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Letnik;
import entities.uporabnik.PotrditevRegistracije;
import entities.uporabnik.Uporabnik;
import entities.uporabnik.Vloga;
import exceptions.SendEmailException;
import repositories.LetnikRepository;
import repositories.StatusUporabnikRepository;
import repositories.UporabnikRepository;
import repositories.VlogaRepository;
import requests.uporabnik.UporabnikRequest;
import response.uporabnik.UporabnikZGeslom;
import services.email.EmailService;
import zrna.PotrditevRegistracijeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class UporabnikServiceImpl implements UporabnikService {
	
	// Odvisnosti:
	@Inject
	private UporabnikRepository uporabnikRepository;
	@Inject
	private StatusUporabnikRepository statusUporabnikRepository;
	@Inject
	private VlogaRepository vlogaRepository;
	@Inject
	private LetnikRepository letnikRepository;
	@Inject
	private EmailService emailService;
	@Inject
	private PotrditevRegistracijeZrno potrditevRegistracijeZrno;
	
	// Metode:
	@Override
	public List<Uporabnik> poisciVseUporabnike(QueryParameters query) {
		return uporabnikRepository.pridobiVseUporabnike(query);
	}
	
	@Override
	public Uporabnik poisciUporabnika(long id){
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		if(uporabnik == null) {
			throw new NotFoundException();
		}
		return uporabnik;
	}
	
	@Override
	public List<Uporabnik> poisciZNizom(String niz) {
		return uporabnikRepository.poisciNUjemajocih(niz, 10);
	}
	
	@Override
	public UporabnikZGeslom poisciZEmailom(String email) {
		UporabnikZGeslom uporabnik = uporabnikRepository.poisciUporabnikaZEmailom(email);
		if(uporabnik == null) {
			throw new NotFoundException();
		}
		return uporabnik;
	}
	
	@Override
	public Uporabnik dodajUporabnika(UporabnikRequest req, String hostname) throws SendEmailException {
		if(jeNeveljavenEmail(req.email)) {
			throw new EntityExistsException("E-mail ze obstaja!");
		}
		Uporabnik uporabnik = extractUporabnik(req);
		uporabnikRepository.dodajUporabnika(uporabnik);
		PotrditevRegistracije potrditev = potrditevRegistracijeZrno.kreirajNovoPotrditev(uporabnik);
		String kljuc = potrditev.getKljuc();
		
		String zadeva = "Registracija uspešna!";
		emailService.posljiRegistracijskiEmail(uporabnik, zadeva, kljuc, hostname);
		return uporabnik;
	}
	
	@Override
	public void potrdiUporabnikovEmail(String kljuc) {
		PotrditevRegistracije potrditev = potrditevRegistracijeZrno.poisciZKljucem(kljuc);
		Uporabnik uporabnik = potrditev.getUporabnik();
		
		uporabnik.setStatus(statusUporabnikRepository.pridobiStatusSSifro("ACTIVE"));
		
		uporabnikRepository.posodobiUporabnika(uporabnik);
		
		potrditevRegistracijeZrno.izbrisi(potrditev.getId());
	}
	
	@Override
	public Uporabnik posodobiUporabnika(long id, UporabnikRequest req) {
		if(jeNeveljavenEmail(req.email)) {
			throw new EntityExistsException("E-mail ze obstaja!");
		}
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		uporabnik.setEmail(req.email);
		uporabnik.setGeslo(req.geslo);
		uporabnik.setIme(req.ime);
		uporabnik.setPriimek(req.priimek);
		uporabnik.setUporabniskoIme(req.uporabniskoIme);
		Letnik letnik = letnikRepository.pridobiEnLetnik(req.letnik);
		uporabnik.setLetnik(letnik);
		uporabnikRepository.posodobiUporabnika(uporabnik);
		return uporabnik;
	}
	
	@Override
	public void izbrisiUporabnika(long id) {
		uporabnikRepository.odstraniUporabnika(id);
	}
	
	@Override
	public void nastaviZaModeratorja(long id) {
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		uporabnik.getVloge().add(vlogaRepository.pridobiVlogoSSifro("MOD"));
		uporabnikRepository.posodobiUporabnika(uporabnik);
	}
	
	@Override
	public void nastaviZaAdmina(long id) {
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		uporabnik.getVloge().add(vlogaRepository.pridobiVlogoSSifro("ADMIN"));
		uporabnikRepository.posodobiUporabnika(uporabnik);
	}
	
	@Override
	public void vzemiPraviceModeratorja(long id) {
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		uporabnik.getVloge().remove(vlogaRepository.pridobiVlogoSSifro("MOD"));
		uporabnikRepository.posodobiUporabnika(uporabnik);
	}
	
	@Override
	public void vzemiPraviceAdmina(long id) {
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		uporabnik.getVloge().remove(vlogaRepository.pridobiVlogoSSifro("ADMIN"));
		uporabnikRepository.posodobiUporabnika(uporabnik);
	}
	
	private boolean jeNeveljavenEmail(String email) {
		return uporabnikRepository.prestejUporabnikeZEmailom(email) != 0;
	}
	
	private Uporabnik extractUporabnik(UporabnikRequest req) {
		Letnik letnik = letnikRepository.pridobiEnLetnik(req.letnik);
		Uporabnik uporabnik = new Uporabnik();
		uporabnik.setEmail(req.email);
		uporabnik.setGeslo(req.geslo);
		uporabnik.setIme(req.ime);
		uporabnik.setPriimek(req.priimek);
		uporabnik.setUporabniskoIme(req.uporabniskoIme);
		uporabnik.setLetnik(letnik);
		uporabnik.setStatus(statusUporabnikRepository.pridobiStatusSSifro("NOT_CONFIRMED"));
		uporabnik.setVloge(new HashSet<>());
		Vloga vlogaUser = vlogaRepository.pridobiVlogoSSifro("USER");
		uporabnik.getVloge().add(vlogaUser);
		return uporabnik;
	}
}
