package repositories;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.uporabnik.Uporabnik;

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
		Query query = entityManager.createQuery("SELECT u.id, u.email, u.uporabniskoIme, " +
				"u.ime, u.priimek, u.letnik, u.status FROM Uporabnik u WHERE u.id = :id");
		query.setParameter("id", id);
		Uporabnik uporabnik = (Uporabnik) query.getSingleResult();
		if(uporabnik == null) {
			return null;
		}
		return uporabnik;
	}
	
	public long prestejUporabnikeZEmailom(String email) {
		Query query = entityManager.createQuery("SELECT u FROM Uporabnik u WHERE u.email = :email");
		query.setParameter("email", email);
		List<Uporabnik> lista = query.getResultList();
		return lista.size();
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
