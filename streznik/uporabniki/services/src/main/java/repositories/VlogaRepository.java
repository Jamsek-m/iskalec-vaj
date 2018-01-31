package repositories;

import entities.uporabnik.Vloga;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class VlogaRepository {
	
	@PersistenceContext(unitName = "baza-jpa-unit")
	private EntityManager entityManager;
	
	public List<Vloga> vrniVseVloge() {
		Query query = entityManager.createQuery("SELECT v FROM Vloga v");
		return query.getResultList();
	}
	
	public Vloga pridobiVlogoSSifro(String sifra) {
		Query query = entityManager.createQuery("SELECT v FROM Vloga v where v.sifra = :sifra");
		query.setParameter("sifra", sifra);
		return (Vloga) query.getSingleResult();
	}
	
}
