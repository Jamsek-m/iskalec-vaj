package entities.uporabnik;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "register_confirm")
public class PotrditevRegistracije implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "uporabnik_id")
	private Uporabnik uporabnik;
	
	@Column(name = "kljuc")
	private String kljuc;
	
	public PotrditevRegistracije() {
	}
	
	public PotrditevRegistracije(Uporabnik uporabnik, String kljuc) {
		this.uporabnik = uporabnik;
		this.kljuc = kljuc;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Uporabnik getUporabnik() {
		return uporabnik;
	}
	
	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}
	
	public String getKljuc() {
		return kljuc;
	}
	
	public void setKljuc(String kljuc) {
		this.kljuc = kljuc;
	}
}
