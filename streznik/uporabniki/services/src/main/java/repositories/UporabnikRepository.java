package repositories;

import entities.uporabnik.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class UporabnikRepository {

	@PersistenceContext(unitName = "baza-jpa-unit")
	private EntityManager entityManager;
	
	public List<Uporabnik> pridobiVseUporabnike() {
		Query query = entityManager.createQuery("SELECT u FROM Uporabnik u");
		return query.getResultList();
	}
}
