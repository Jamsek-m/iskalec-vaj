package services.email;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import entities.uporabnik.Uporabnik;
import exceptions.SendEmailException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@ApplicationScoped
public class EmailServiceImpl implements EmailService {
	
	private static final Logger LOG = LogManager.getLogger(EmailServiceImpl.class.getName());
	
	private Client client;
	
	private final String baseUrl = ConfigurationUtil.getInstance().get("storitve.email").orElse("");
	
	@PostConstruct
	private void init() {
		this.client = ClientBuilder.newClient();
	}
	
	@Override
	public void posljiRegistracijskiEmail(Uporabnik prejemnik, String zadeva, String potrditveniKljuc, String hostname) throws SendEmailException {
		HashMap<String, String> kontekst = new HashMap<>();
		kontekst.put("zadeva", zadeva);
		kontekst.put("uporabniskoIme", prejemnik.getUporabniskoIme());
		kontekst.put("kljuc", potrditveniKljuc);
		kontekst.put("hostname", hostname);
		this.posljiEmail(TIP_REGISTRACIJA, zadeva, prejemnik.getEmail(), kontekst);
	}
	
	private void posljiEmail(String tip, String zadeva, String prejemnik, HashMap<String, String> kontekst) throws SendEmailException {
		EmailRequest req = new EmailRequest(zadeva, tip, prejemnik, kontekst);
		WebTarget url = client.target(this.baseUrl);
		Response response = url.request().post(Entity.json(req));
		
		EmailResponse res = response.readEntity(EmailResponse.class);
		if(response.getStatus() != Response.Status.CREATED.getStatusCode()) {
			LOG.error(res.status + ": " + res.sporocilo);
			throw new SendEmailException(res.sporocilo);
		}
	}
}
