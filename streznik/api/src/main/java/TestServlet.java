import entities.uporabnik.Uporabnik;
import repositories.UporabnikRepository;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
	
	@Inject
	private UporabnikRepository upRepo;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		PrintWriter writer = res.getWriter();
		
		writer.println("Hello world!");
		
		for(Uporabnik up : upRepo.pridobiVseUporabnike()) {
			writer.println(up.getEmail());
		}
		
		writer.close();
	
	}


}
