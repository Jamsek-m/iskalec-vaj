package entitete;

import java.io.Serializable;
import java.util.HashMap;

public class EmailRequest implements Serializable {
	
	private String zadeva;
	
	private String tip;
	
	private String prejemnik;
	
	private HashMap<String, String> kontekst;
	
	public String getZadeva() {
		return zadeva;
	}
	
	public void setZadeva(String zadeva) {
		this.zadeva = zadeva;
	}
	
	public String getTip() {
		return tip;
	}
	
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public String getPrejemnik() {
		return prejemnik;
	}
	
	public void setPrejemnik(String prejemnik) {
		this.prejemnik = prejemnik;
	}
	
	public HashMap<String, String> getKontekst() {
		return kontekst;
	}
	
	public void setKontekst(HashMap<String, String> kontekst) {
		this.kontekst = kontekst;
	}
}
