import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Authentification extends HttpServlet {
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
        	String nom = req.getParameter("nom"),
                       motDePasse = req.getParameter("password");
		
        	HttpSession session = req.getSession(true);
        	if(verifie(nom, motDePasse))
		{ 
            		session.setAttribute("valide", "valide");
			res.sendRedirect("index.html");
        	}
		else
		{
			res.sendRedirect("ident.html");
		}
      		return;
    	}

    	public boolean verifie(String nom, String motDePasse)
	{
        	boolean ret = false;
		if (nom == null || motDePasse == null) ret = false;
        	if(nom.equals("moi") && motDePasse.equals("pass")) ret = true;
		return ret;
		
 	}
	
}