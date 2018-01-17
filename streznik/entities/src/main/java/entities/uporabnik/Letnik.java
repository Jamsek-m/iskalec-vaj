package entities.uporabnik;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="letnik")
public class Letnik implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int letnik;
	
	@ManyToOne
	@JoinColumn(name = "enota_id")
	private Enota enota;
	
	public Letnik() {}
	
	public Letnik(int letnik, Enota enota) {
		this.letnik = letnik;
		this.enota = enota;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getLetnik() {
		return letnik;
	}
	
	public void setLetnik(int letnik) {
		this.letnik = letnik;
	}
	
	public Enota getEnota() {
		return enota;
	}
	
	public void setEnota(Enota enota) {
		this.enota = enota;
	}
	
	@Override
	public String toString() {
		return "{\"Letnik\":{"
				+ "                        \"id\":\"" + id + "\""
				+ ",                         \"letnik\":\"" + letnik + "\""
				+ ",                         \"enota\":" + enota
				+ "}}";
	}
}
