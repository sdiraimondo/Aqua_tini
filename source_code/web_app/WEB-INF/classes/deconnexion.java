import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class deconnexion extends HttpServlet {
	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
        	HttpSession session = req.getSession(true);
        	session.invalidate();
		res.sendRedirect("ident.html");
    	}
}