package entities.uporabnik;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sif_vloga")
public class Vloga implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String sifra;
	
	@Column
	private String naziv;
	
	public Vloga() {}
	
	public Vloga(String sifra, String naziv) {
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
		return "{\"Vloga\":{"
				+ "                        \"id\":\"" + id + "\""
				+ ",                         \"sifra\":\"" + sifra + "\""
				+ ",                         \"naziv\":\"" + naziv + "\""
				+ "}}";
	}
}
