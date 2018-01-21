package entities.uporabnik;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="enota")
public class Enota implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String sifra;
	
	@Column
	private String naziv;
	
	public Enota() {}
	
	public Enota(String sifra, String naziv) {
		this.naziv = naziv;
		this.sifra = sifra;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSifra() {
		return sifra;
	}
	
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	@Override
	public String toString() {
		return "{\"Enota\":{"
				+ "                        \"id\":\"" + id + "\""
				+ ",                         \"sifra\":\"" + sifra + "\""
				+ ",                         \"naziv\":\"" + naziv + "\""
				+ "}}";
	}
}
