package repositories;

import entities.uporabnik.StatusUporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class StatusUporabnikRepository {
	
	@PersistenceContext(unitName = "baza-jpa-unit")
	private EntityManager entityManager;
	
	public List<StatusUporabnik> vrniVseStatuse() {
		Query query = entityManager.createQuery("SELECT s FROM StatusUporabnik s");
		return query.getResultList();
	}
	
	public StatusUporabnik pridobiStatusSSifro(String sifra) {
		Query query = entityManager.createQuery("SELECT s FROM StatusUporabnik s where s.sifra = :sifra");
		query.setParameter("sifra", sifra);
		return (StatusUporabnik) query.getSingleResult();
	}
	
}
