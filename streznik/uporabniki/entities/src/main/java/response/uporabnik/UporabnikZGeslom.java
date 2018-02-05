package response.uporabnik;

import entities.uporabnik.Letnik;
import entities.uporabnik.StatusUporabnik;
import entities.uporabnik.Uporabnik;
import entities.uporabnik.Vloga;

import java.util.Set;

public class UporabnikZGeslom {
	
	private long id;
	
	private String uporabniskoIme;
	
	private String ime;
	
	private String priimek;
	
	private String email;
	
	private String geslo;
	
	private Letnik letnik;
	
	private StatusUporabnik status;
	
	private Set<Vloga> vloge;
	
	public UporabnikZGeslom(Uporabnik upb) {
		this.id = upb.getId();
		this.uporabniskoIme = upb.getUporabniskoIme();
		this.ime = upb.getIme();
		this.priimek = upb.getPriimek();
		this.email = upb.getEmail();
		this.geslo = upb.getGeslo();
		this.letnik = upb.getLetnik();
		this.status = upb.getStatus();
		this.vloge = upb.getVloge();
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
}
