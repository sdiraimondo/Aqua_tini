import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DireBonjour extends HttpServlet {
  public void doGet (HttpServletRequest req, HttpServletResponse res ) 
                 throws ServletException, IOException  {
    String nom = req.getParameter("nom");
    res.setContentType( "text/html" );
    PrintWriter out = res.getWriter();
    out.println( "<HTML>" );
    out.println( "<HEAD>");
    out.println( "<TITLE>Dire bonjour au monsieur</TITLE>" );
    out.println( "</HEAD>" );
    out.println( "<BODY>" );
    out.println( "<H1>Bonjour " + nom + "</H1>" );
    out.println( "</BODY>" );
    out.println( "</HTML>" );
    out.close();
  }
  
  public void doPost (HttpServletRequest req, HttpServletResponse res ) 
                 throws ServletException, IOException  {
    doGet(req, res);
  }
  
  public String getServletInfo() {
    return "Servlet qui dit bonjour au monsieur!";
  }
}
