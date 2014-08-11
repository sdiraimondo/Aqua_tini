import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class BonjourLeMonde extends HttpServlet {
  public void doGet (HttpServletRequest req,  HttpServletResponse res ) 
  throws ServletException, IOException  {
    res.setContentType( "text/html" );
    PrintWriter out = res.getWriter();
    out.println( "<HTML>" );
    out.println( "<HEAD>");
    out.println( "<TITLE>Bonjour le monde</TITLE>" );
    out.println( "</HEAD>" );
    out.println( "<BODY>" );
    out.println( "<H1>Bonjour le Monde!</H1>" );
    out.println( "</BODY>" );
    out.println( "</HTML>" );
    out.close();
  }
}
