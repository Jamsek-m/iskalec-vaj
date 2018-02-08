package repositories;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.uporabnik.Uporabnik;
import response.uporabnik.UporabnikZGeslom;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UporabnikRepository {

	@PersistenceContext(unitName = "baza-jpa-unit")
	private EntityManager entityManager;
	
	public List<Uporabnik> pridobiVseUporabnike(QueryParameters query) {
		return JPAUtils.queryEntities(entityManager, Uporabnik.class, query);
	}
	
	public Uporabnik pridobiUporabnika(long id) {
		Uporabnik uporabnik = entityManager.find(Uporabnik.class, id);
		return uporabnik;
	}
	
	public UporabnikZGeslom poisciUporabnikaZEmailom(String email) {
		Query query = entityManager.createQuery("SELECT u FROM Uporabnik u WHERE u.email = :email");
		query.setParameter("email", email);
		List<Uporabnik> uporabniki = query.getResultList();
		if(!uporabniki.isEmpty()) {
			UporabnikZGeslom uporabnik = new UporabnikZGeslom(uporabniki.get(0));
			return uporabnik;
		} else {
			return null;
		}
	}
	
	public long prestejUporabnikeZEmailom(String email) {
		Query query = entityManager.createQuery("SELECT u FROM Uporabnik u WHERE u.email = :email");
		query.setParameter("email", email);
		List<Uporabnik> lista = query.getResultList();
		return lista.size();
	}
	
	public List<Uporabnik> poisciNUjemajocih(String niz, int n) {
		Query query = entityManager.createQuery("SELECT u FROM Uporabnik u WHERE u.ime LIKE :niz " +
				"OR u.priimek LIKE :niz");
		query.setParameter("niz", "%" + niz + "%");
		List<Uporabnik> uporabniki = query.setMaxResults(n).getResultList();
		return uporabniki;
	}
	
	@Transactional
	public void dodajUporabnika(Uporabnik uporabnik) {
		entityManager.persist(uporabnik);
	}
	
	@Transactional
	public void posodobiUporabnika(Uporabnik uporabnik) {
		entityManager.merge(uporabnik);
	}
	
	@Transactional
	public void odstraniUporabnika(long id) {
		Uporabnik uporabnik = pridobiUporabnika(id);
		entityManager.remove(uporabnik);
	}
}
