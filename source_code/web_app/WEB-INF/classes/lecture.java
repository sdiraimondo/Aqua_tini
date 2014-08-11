import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class lecture extends HttpServlet
{

	public void init()
	{
	
  	}

	public void doGet (HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException 
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String nomFichier ="/usr/local/jakarta-tomcat-4.1.31/webapps/aqua/WEB-INF/classes/alarme.log";
		String ligne;

		BufferedReader entree = new BufferedReader (new FileReader(nomFichier));
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Paramétrage des consignes</TITLE");
		out.println("</HEAD>");
		out.println("<BODY>");
		do
		{
			ligne = entree.readLine();
			if(ligne != null) 
			out.println(ligne);
			out.println("<br>");
		}
		while(ligne != null);
		entree.close();
	
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
	
	public String getServletInfo() 
	{
		return "Paramétrage des consignes";
	}
} 
