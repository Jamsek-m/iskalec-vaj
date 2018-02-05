package services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import entities.uporabnik.Letnik;
import entities.uporabnik.Uporabnik;
import entities.uporabnik.Vloga;
import exceptions.SendEmailException;
import repositories.LetnikRepository;
import repositories.StatusUporabnikRepository;
import repositories.UporabnikRepository;
import repositories.VlogaRepository;
import requests.uporabnik.UporabnikRequest;
import services.email.EmailService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.ws.rs.BadRequestException;
import java.util.HashSet;
import java.util.List;

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
	
	@Inject
	private EmailService emailService;
	
	@Override
	public List<Uporabnik> poisciVseUporabnike(QueryParameters query) {
		return uporabnikRepository.pridobiVseUporabnike(query);
	}
	
	@Override
	public Uporabnik poisciUporabnika(long id) {
		return uporabnikRepository.pridobiUporabnika(id);
	}
	
	@Override
	public List<Uporabnik> poisciZNizom(String niz) {
		return uporabnikRepository.poisciNUjemajocih(niz, 10);
	}
	
	@Override
	public Uporabnik dodajUporabnika(UporabnikRequest req) throws SendEmailException {
		if(jeNeveljavenEmail(req.email)) {
			throw new EntityExistsException("E-mail ze obstaja!");
		}
		if(!req.geslo1.equals(req.geslo2)) {
			throw new BadRequestException("Gesli se ne ujemata!");
		}
		Uporabnik uporabnik = extractUporabnik(req);
		uporabnikRepository.dodajUporabnika(uporabnik);
		
		//String zadeva = "Registracija uspešna!";
		//emailService.posljiRegistracijskiEmail(uporabnik, zadeva, "kljuc12345");
		return uporabnik;
	}
	
	@Override
	public Uporabnik posodobiUporabnika(long id, UporabnikRequest req) {
		if(jeNeveljavenEmail(req.email)) {
			throw new EntityExistsException("E-mail ze obstaja!");
		}
		if(!req.geslo1.equals(req.geslo2)) {
			throw new BadRequestException("Gesli se ne ujemata!");
		}
		Uporabnik uporabnik = uporabnikRepository.pridobiUporabnika(id);
		uporabnik.setEmail(req.email);
		uporabnik.setGeslo(req.geslo1);
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
		uporabnik.setGeslo(req.hashedGeslo);
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
