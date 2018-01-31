package response.uporabnik;

import entities.uporabnik.Uporabnik;

import java.io.Serializable;
import java.util.List;

public class UporabnikPage implements Serializable {
	
	public Glava header;
	
	public List<Uporabnik> uporabniki;
	
	public UporabnikPage() {}
	
	public UporabnikPage(List<Uporabnik> uporabniki, long limit) {
		Glava glava = new Glava();
		glava.steviloStrani = (long) Math.ceil((double)uporabniki.size() / (double)limit);
		glava.steviloZadetkov = uporabniki.size();
		this.header = glava;
		this.uporabniki = uporabniki;
	}
	
}

class Glava {
	
	public long steviloStrani;
	
	public long steviloZadetkov;
	
}
