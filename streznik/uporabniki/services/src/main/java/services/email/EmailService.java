package services.email;


import entities.uporabnik.Uporabnik;
import exceptions.SendEmailException;

public interface EmailService {
	
	public static final String TIP_REGISTRACIJA = "registracija";
	
	public void posljiRegistracijskiEmail(Uporabnik prejemnik, String zadeva, String potrditveniKljuc, String hostname) throws SendEmailException;
	
}
