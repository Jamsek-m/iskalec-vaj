package entities.uporabnik;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="uporabnik")
public class Uporabnik implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "uporabnisko_ime")
	private String uporabniskoIme;
	
	@Column
	private String ime;
	
	@Column
	private String priimek;
	
	@Column
	private String email;
	
	@Column
	@XmlTransient
	private String geslo;
	
	@ManyToOne
	@JoinColumn(name="letnik_id")
	private Letnik letnik;
	
	@ManyToOne
	@JoinColumn(name="status_id")
	private StatusUporabnik status;
	
	@ManyToMany
	@JoinTable(name="uporabniske_vloge",
			joinColumns = @JoinColumn(name="uporabnik_id"),
			inverseJoinColumns = @JoinColumn(name="vloga_id"))
	private Set<Vloga> vloge;
	
	// TODO: dodaj predmete
	
	// TODO: dodaj nastavitve
	
	
	public Uporabnik() {}
	
	public Uporabnik(String uporabniskoIme, String ime, String priimek, String email, Letnik letnik, StatusUporabnik status) {
		this.uporabniskoIme = uporabniskoIme;
		this.ime = ime;
		this.priimek = priimek;
		this.email = email;
		this.letnik = letnik;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUporabniskoIme() {
		return uporabniskoIme;
	}
	
	public void setUporabniskoIme(String uporabniskoIme) {
		this.uporabniskoIme = uporabniskoIme;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPriimek() {
		return priimek;
	}
	
	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlTransient
	public String getGeslo() {
		return geslo;
	}
	
	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}
	
	public Letnik getLetnik() {
		return letnik;
	}
	
	public void setLetnik(Letnik letnik) {
		this.letnik = letnik;
	}
	
	public StatusUporabnik getStatus() {
		return status;
	}
	
	public void setStatus(StatusUporabnik status) {
		this.status = status;
	}
	
	public Set<Vloga> getVloge() {
		return vloge;
	}
	
	public void setVloge(Set<Vloga> vloge) {
		this.vloge = vloge;
	}
	
	@Override
	public String toString() {
		return "{\"Uporabnik\":{"
				+ "                        \"id\":\"" + id + "\""
				+ ",                         \"uporabniskoIme\":\"" + uporabniskoIme + "\""
				+ ",                         \"ime\":\"" + ime + "\""
				+ ",                         \"priimek\":\"" + priimek + "\""
				+ ",                         \"email\":\"" + email + "\""
				+ ",                         \"geslo\":\"" + geslo + "\""
				+ ",                         \"letnik\":" + letnik
				+ ",                         \"status\":" + status
				+ "}}";
	}
}
