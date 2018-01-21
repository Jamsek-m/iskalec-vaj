package repositories;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.uporabnik.Letnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class LetnikRepository {
	
	@PersistenceContext(unitName = "baza-jpa-unit")
	private EntityManager entityManager;
	
	public List<Letnik> pridobiVse(QueryParameters query) {
		return JPAUtils.queryEntities(entityManager, Letnik.class, query);
	}
	
	public Letnik pridobiEnLetnik(int id) {
		Query query = entityManager.createQuery("SELECT l FROM Letnik l WHERE l.id = :id");
		query.setParameter("id", id);
		return (Letnik) query.getSingleResult();
	}
	
	
}
