import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ReglerConsignes extends HttpServlet
 {
 	private SGF evenements = new SGF();
	
	public void doGet (HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException 
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	
		HttpSession session = request.getSession(true);
		String Etat = (String)session.getAttribute("valide");
		out.println(Etat);
 		if(Etat==null)
		{
			res.sendRedirect("ident.html");
		}
		else
 		{
			int heureNeon1_Marche = evenements.recupEvenements("heureNeon1_Marche");
			int minuteNeon1_Marche = evenements.recupEvenements("minuteNeon1_Marche");
			int heureNeon2_Marche = evenements.recupEvenements("heureNeon2_Marche");
			int minuteNeon2_Marche = evenements.recupEvenements("minuteNeon2_Marche");
			int heureNourriture1_Marche = evenements.recupEvenements("heureNourriture1_Marche");
			int minuteNourriture1_Marche = evenements.recupEvenements("minuteNourriture1_Marche");
			int heureNourriture2_Marche = evenements.recupEvenements("heureNourriture2_Marche");
			int minuteNourriture2_Marche = evenements.recupEvenements("minuteNourriture2_Marche");
			int heureNourriture3_Marche = evenements.recupEvenements("heureNourriture3_Marche");
			int minuteNourriture3_Marche = evenements.recupEvenements("minuteNourriture3_Marche");
			int jourEngrais_Marche = evenements.recupEvenements("jourEngrais_Marche");	
			int heureEngrais_Marche = evenements.recupEvenements("heureEngrais_Marche");
			int minuteEngrais_Marche = evenements.recupEvenements("minuteEngrais_Marche");
			float TemperatureConsigneMoins = evenements.recupConsignes("TemperatureConsigneMoins");
			float TemperatureConsignePlus = evenements.recupConsignes("TemperatureConsignePlus");
			
			
			out.println("<SCRIPT language=\"Javascript\">");
			out.println("<!--");
			out.println("function verifFormulaire()");
			out.println("{");
			out.println("var valid = 1");
					
			out.println("if(document.FormConsignes.PhMoins.value > document.FormConsignes.PhPlus.value)");
			out.println("{");
			out.println("alert(\"Attention le pH minimum est supérieur au pH maximum !\")");
			out.println("valid = 0");
			out.println("}");
					
			out.println("if(document.FormConsignes.TemperatureConsigneMoins.value > document.FormConsignes.TemperatureConsignePlus.value)");
			out.println("{");
			out.println("alert(\"Attention la température minimum est supérieur à la température maximum !\")");
			out.println("valid = 0");
			out.println("}");
					
			out.println("if(document.FormConsignes.TemperatureConsignePlus.value - document.FormConsignes.TemperatureConsigneMoins.value > 2)");
			out.println("{");
			out.println("alert(\"Attention la différence entre la température minimum et la température maximum est supérieur à 2°C !\")");
			out.println("valid = 0");
			out.println("}");
			
			out.println("if(document.FormConsignes.TemperatureConsignePlus.value > 26 || document.FormConsignes.TemperatureConsigneMoins.value < 24)");
			out.println("{");
			out.println("alert(\"Attention la température doit être comprise entre 24°C et 26°C !\")");
			out.println("valid = 0");
			out.println("}");
			
			out.println("if(valid == 1)");
			out.println("{");
			out.println("document.FormConsignes.submit()");
			out.println("return true");
			out.println("}");
			out.println("else");
			out.println("{");
			out.println("return false;");
			out.println("}");
					
			out.println("}");
			out.println("//-->");
			out.println("</SCRIPT>");
		
		
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Paramétrage des consignes</TITLE");
			out.println("<SCRIPT language=\"javascript\" src=\"/usr/local/jakarta-tomcat-4.1.31/webapps/aqua/WEB-INF/classes/fonction.js\"></SCRIPT>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("<u>Paramétrage des consignes</u> :<br><br>");
			out.println("<form name=\"FormConsignes\" method=get action=\"consignes\">");
		
			out.println("Neon 1 Allumage :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNeon1_Marche\" value=" + heureNeon1_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNeon1_Marche\" value=" + minuteNeon1_Marche + ">");
			out.println("<br><br>");
			
			out.println("Neon 1 Extinction :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNeon1_Arret\" value=" + heureNeon1_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNeon1_Arret\" value=" + minuteNeon1_Marche + ">");
			out.println("<br><br>");
			
			out.println("Neon 2 Allumage :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNeon2_Marche\" value=" + heureNeon2_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNeon2_Marche\" value=" + minuteNeon2_Marche + ">");
			out.println("<br><br>");
			
			out.println("Neon 2 Extinction :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNeon2_Arret\" value=" + heureNeon2_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNeon2_Arret\" value=" + minuteNeon2_Marche + ">");
			out.println("<br><br>");
			out.println("<hr width=\"50%\" size=\"1\"><br>");
			out.println("La distribution de la nourriture se fait 3 fois pas jour<br><br>");
			out.println("Première distribution :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNourriture1_Marche\" value=" + heureNourriture1_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNourriture1_Marche\" value=" + minuteNourriture1_Marche + ">");
			out.println("<br><br>");
			out.println("Seconde distribution :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNourriture2_Marche\" value=" + heureNourriture2_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNourriture2_Marche\" value=" + minuteNourriture2_Marche + ">");
			out.println("<br><br>");
			out.println("Troisiéme distribution :<br>");
			out.println("Heure <input type=\"text\" name=\"heureNourriture3_Marche\" value=" + heureNourriture3_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteNourriture3_Marche\" value=" + minuteNourriture3_Marche + ">");
			out.println("<br><br>");
			
			out.println("<hr width=\"50%\" size=\"1\"><br>");
			out.println("Distribution d'engrais :<br>");
			out.println("Jour <select name=\"jourEngrais_Marche\" size=\"0\"");
			out.println("<option value=\"1\">Lundi</option>");
			out.println("<option value=\"2\">Mardi</option>");
			out.println("<option value=\"3\">Mercredi</option>");
			out.println("<option value=\"4\">Jeudi</option>");
			out.println("<option value=\"5\">Vendredi</option>");
			out.println("<option value=\"6\">Samedi</option>");
			out.println("<option value=\"7\">Dimanche</option></select>");
			out.println("<br>");
			out.println("Heure <input type=\"text\" name=\"heureEngrais_Marche\" value=" + heureEngrais_Marche + ">");
			out.println("Minute <input type=\"text\" name=\"minuteEngrais_Marche\" value=" + minuteEngrais_Marche + ">");
			out.println("<br><br>");
			out.println("<hr width=\"50%\" size=\"1\"><br>");
			
			out.println("<table border=\"0\" width=\"50%\"><tr><td align=\"left\">");
			out.println("PH minimum souhaité :<br>");
			out.println("Valeur <select name=\"PhMoins\" size=\"0\"");
			out.println("<option value=\"6.5\">6.5</option>");
			out.println("<option value=\"7.0\">7</option>");
			out.println("<option value=\"7.5\">7.5</option></select>");
			out.println("</td><td align=\"left\">");
			out.println("PH maximum souhaité :<br>");
			out.println("Valeur <select name=\"PhPlus\" size=\"0\"");
			out.println("<option value=\"7.0\">7</option>");
			out.println("<option value=\"7.5\">7.5</option>");
			out.println("<option value=\"8.0\">8</option></select>");
			out.println("</td></tr></table>");
			out.println("<br>");
			out.println("<hr width=\"50%\" size=\"1\"><br>");
			
			
			out.println("Température souhaitée :<br>");
			out.println("Minimum <input type=\"text\" name=\"TemperatureConsigneMoins\" value=" + TemperatureConsigneMoins + ">");
			out.println("Maximum <input type=\"text\" name=\"TemperatureConsignePlus\" value=" + TemperatureConsignePlus + ">");
			out.println("<br><br>");
			
			
			
			out.println("<input type=\"button\" value=\"Envoyer\" onClick=\"verifFormulaire()\">");
			out.println("</form>");
			out.println("</BODY>");
			out.println("</HTML>");
			out.close();
		}
	}
	
	
	public String getServletInfo() 
	{
	 return "Paramétrage des consignes";
	}
}
