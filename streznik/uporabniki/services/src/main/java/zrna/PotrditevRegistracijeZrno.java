package zrna;

import entities.uporabnik.PotrditevRegistracije;
import entities.uporabnik.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class PotrditevRegistracijeZrno {
	
	@PersistenceContext(unitName = "baza-jpa-unit")
	private EntityManager entityManager;
	
	@Transactional
	public PotrditevRegistracije kreirajNovoPotrditev(Uporabnik uporabnik) {
		String kljuc = "";
		PotrditevRegistracije obstojeci = null;
		do {
			kljuc = generateConfirmationKey();
			obstojeci = this.poisciZKljucem(kljuc);
		} while(obstojeci != null);
		PotrditevRegistracije potrditev = new PotrditevRegistracije(uporabnik, kljuc);
		this.dodaj(potrditev);
		return potrditev;
	}
	
	private String generateConfirmationKey() {
		Random random = new SecureRandom();
		String key = "";
		final String ZNAKI = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
		for(int i = 0; i < 10; ++i) {
			key += ZNAKI.charAt(random.nextInt(ZNAKI.length()));
		}
		return key;
	}
	
	@Transactional
	private void dodaj(PotrditevRegistracije reg) {
		entityManager.persist(reg);
	}
	
	public PotrditevRegistracije poisciZKljucem(String kljuc) {
		Query query = entityManager.createQuery("SELECT p FROM PotrditevRegistracije p WHERE p.kljuc = :kljuc");
		query.setParameter("kljuc", kljuc);
		List<PotrditevRegistracije> potrditve = query.getResultList();
		if(potrditve.isEmpty()) {
			return null;
		}
		return potrditve.get(0);
	}
	
	@Transactional
	public void izbrisi(long id) {
		PotrditevRegistracije reg = entityManager.find(PotrditevRegistracije.class, id);
		entityManager.remove(reg);
	}
	
}
