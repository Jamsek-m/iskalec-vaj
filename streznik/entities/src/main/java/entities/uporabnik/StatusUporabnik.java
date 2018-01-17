package entities.uporabnik;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sif_status_uporabnik")
public class StatusUporabnik implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String sifra;
	
	@Column
	private String naziv;
	
	public StatusUporabnik() {}
	
	public StatusUporabnik(String sifra, String naziv) {
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
		return "{\"StatusUporabnik\":{"
				+ "                        \"id\":\"" + id + "\""
				+ ",                         \"sifra\":\"" + sifra + "\""
				+ ",                         \"naziv\":\"" + naziv + "\""
				+ "}}";
	}
}
