// package com.qindesign.servlet.example;
// 
// //import com.qindesign.servlet.AuthenticatedHttpServlet;
// import com.qindesign.servlet.*;

import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
// import java.io.IOException;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;


public class consignes extends HttpServlet
 {
 	private SGF evenements = new SGF();
	private static PrintWriter fichierCreationEvenements;
	private static PrintWriter fichierCreationConsignes;
 
	public void doGet (HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException 
	{
		String nomfichierEvenements = "/usr/local/jakarta-tomcat-4.1.31/webapps/aqua/WEB-INF/classes/evenements.dat";
		String nomfichierConsignes = "/usr/local/jakarta-tomcat-4.1.31/webapps/aqua/WEB-INF/classes/consignes.dat";
		File fichierEvenements = new File(nomfichierEvenements);
		
		int heureNeon1_Marche = Integer.parseInt(req.getParameter("heureNeon1_Marche"));
		int minuteNeon1_Marche = Integer.parseInt(req.getParameter("minuteNeon1_Marche"));
		int heureNeon1_Arret = Integer.parseInt(req.getParameter("heureNeon1_Arret"));
		int minuteNeon1_Arret = Integer.parseInt(req.getParameter("minuteNeon1_Arret"));
		int heureNeon2_Marche = Integer.parseInt(req.getParameter("heureNeon2_Marche"));
		int minuteNeon2_Marche = Integer.parseInt(req.getParameter("minuteNeon2_Marche"));
		int heureNeon2_Arret = Integer.parseInt(req.getParameter("heureNeon2_Arret"));
		int minuteNeon2_Arret = Integer.parseInt(req.getParameter("minuteNeon2_Arret"));
		int heureNourriture1_Marche = Integer.parseInt(req.getParameter("heureNourriture1_Marche"));
		int minuteNourriture1_Marche = Integer.parseInt(req.getParameter("minuteNourriture1_Marche"));
		int heureNourriture2_Marche = Integer.parseInt(req.getParameter("heureNourriture2_Marche"));
		int minuteNourriture2_Marche = Integer.parseInt(req.getParameter("minuteNourriture2_Marche"));
		int heureNourriture3_Marche = Integer.parseInt(req.getParameter("heureNourriture3_Marche"));
		int minuteNourriture3_Marche = Integer.parseInt(req.getParameter("minuteNourriture3_Marche"));
		int heureFiltration1_Marche = evenements.recupEvenements("heureFiltration1_Marche");
		int minuteFiltration1_Marche = evenements.recupEvenements("minuteFiltration1_Marche");
		int heureFiltration2_Marche = evenements.recupEvenements("heureFiltration2_Marche");
		int minuteFiltration2_Marche = evenements.recupEvenements("minuteFiltration2_Marche");
		int heureFiltration3_Marche = evenements.recupEvenements("heureFiltration3_Marche");
		int minuteFiltration3_Marche = evenements.recupEvenements("minuteFiltration3_Marche");
		int jourEngrais_Marche = Integer.parseInt(req.getParameter("jourEngrais_Marche"));
		int heureEngrais_Marche = Integer.parseInt(req.getParameter("heureEngrais_Marche"));
		int minuteEngrais_Marche = Integer.parseInt(req.getParameter("minuteEngrais_Marche"));
		float PhMoins = Float.valueOf(req.getParameter("PhMoins")).floatValue();
		float PhPlus = Float.valueOf(req.getParameter("PhPlus")).floatValue();
		float TemperatureConsigneMoins =  Float.valueOf(req.getParameter("TemperatureConsigneMoins")).floatValue();
		float TemperatureConsignePlus = Float.valueOf(req.getParameter("TemperatureConsignePlus")).floatValue();
		
		if(minuteNourriture1_Marche < 30)
		{
			minuteFiltration1_Marche = minuteNourriture1_Marche + 30;
		}
		else
		{
			minuteFiltration1_Marche = ((minuteNourriture1_Marche + 30) - 60);
			if(heureFiltration1_Marche == 23)
			{
				heureFiltration1_Marche = 0;
			}
			else
			{
				heureFiltration1_Marche = heureFiltration1_Marche + 1;
			}
		}
		
		if(minuteNourriture2_Marche < 30)
		{
			minuteFiltration2_Marche = minuteNourriture2_Marche + 30;
		}
		else
		{
			minuteFiltration2_Marche = ((minuteNourriture2_Marche + 30) - 60);
			if(heureFiltration2_Marche == 23)
			{
				heureFiltration2_Marche = 0;
			}
			else
			{
				heureFiltration2_Marche = heureFiltration2_Marche + 1;
			}
		}
		
		if(minuteNourriture3_Marche < 30)
		{
			minuteFiltration3_Marche = minuteNourriture3_Marche + 30;
		}
		else
		{
			minuteFiltration3_Marche = ((minuteNourriture3_Marche + 30) - 60);
			if(heureFiltration3_Marche == 23)
			{
				heureFiltration3_Marche = 0;
			}
			else
			{
				heureFiltration3_Marche = heureFiltration3_Marche + 1;
			}
		}
	
	
	
		try
		{
			FileWriter fw = new FileWriter(nomfichierEvenements);
			BufferedWriter bw = new BufferedWriter(fw);
			fichierCreationEvenements = new PrintWriter(bw);
		}
		catch(java.io.IOException e)
		{
			System.out.println("Erreur création fichier evenements.dat " + e.toString());
		}
		fichierCreationEvenements.println("heureNeon1_Marche="+heureNeon1_Marche);
		fichierCreationEvenements.println("minuteNeon1_Marche="+minuteNeon1_Marche);
		fichierCreationEvenements.println("heureNeon2_Marche="+heureNeon2_Marche);
		fichierCreationEvenements.println("minuteNeon2_Marche="+minuteNeon2_Marche);
		fichierCreationEvenements.println("heureNeon1_Arret="+heureNeon1_Arret);
		fichierCreationEvenements.println("minuteNeon1_Arret="+minuteNeon1_Arret);
		fichierCreationEvenements.println("heureNeon2_Arret="+heureNeon2_Arret);
		fichierCreationEvenements.println("minuteNeon2_Arret="+minuteNeon2_Arret);
		fichierCreationEvenements.println("heureNourriture1_Marche="+heureNourriture1_Marche);
		fichierCreationEvenements.println("minuteNourriture1_Marche="+minuteNourriture1_Marche);
		fichierCreationEvenements.println("heureNourriture2_Marche="+heureNourriture2_Marche);
		fichierCreationEvenements.println("minuteNourriture2_Marche="+minuteNourriture2_Marche);
		fichierCreationEvenements.println("heureNourriture3_Marche="+heureNourriture3_Marche);
		fichierCreationEvenements.println("minuteNourriture3_Marche="+minuteNourriture3_Marche);
		fichierCreationEvenements.println("heureFiltration1_Marche="+heureFiltration1_Marche);
		fichierCreationEvenements.println("minuteFiltration1_Marche="+minuteFiltration1_Marche);
		fichierCreationEvenements.println("heureFiltration2_Marche="+heureFiltration2_Marche);
		fichierCreationEvenements.println("minuteFiltration2_Marche="+minuteFiltration2_Marche);
		fichierCreationEvenements.println("heureFiltration3_Marche="+heureFiltration3_Marche);
		fichierCreationEvenements.println("minuteFiltration3_Marche="+minuteFiltration3_Marche);
		fichierCreationEvenements.println("jourEngrais_Marche="+jourEngrais_Marche);
		fichierCreationEvenements.println("heureEngrais_Marche="+heureEngrais_Marche);
		fichierCreationEvenements.println("minuteEngrais_Marche="+minuteEngrais_Marche);

		fichierCreationEvenements.close();
		
		try
		{
			FileWriter fw = new FileWriter(nomfichierConsignes);
			BufferedWriter bw = new BufferedWriter(fw);
			fichierCreationConsignes = new PrintWriter(bw);
		}
		catch(java.io.IOException e)
		{
			System.out.println("Erreur création fichier evenements.dat " + e.toString());
		}
		fichierCreationConsignes.println("TemperatureConsigneMoins=" + TemperatureConsigneMoins);
		fichierCreationConsignes.println("TemperatureConsignePlus=" + TemperatureConsignePlus);
		fichierCreationConsignes.println("ToleranceTemperature=1.0");
		fichierCreationConsignes.println("NiveauConsigne=3.0");
		fichierCreationConsignes.println("PhMoins=" + PhMoins);
		fichierCreationConsignes.println("PhPlus=" + PhPlus);
		fichierCreationConsignes.println("PhTolerance=0.5");
		fichierCreationConsignes.close();
	
	String jours ="";
		
		
	if(jourEngrais_Marche == 1) jours = "Lundi"; 
	if(jourEngrais_Marche == 2) jours = "Mardi"; 
	if(jourEngrais_Marche == 3) jours = "Mercredi";
	if(jourEngrais_Marche == 4) jours = "Jeudi";
	if(jourEngrais_Marche == 5) jours = "Vendredi";   
	if(jourEngrais_Marche == 6) jours = "Samedi";
	if(jourEngrais_Marche == 7) jours = "Dimanche";
		
		
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	out.println("<HTML>");
	out.println("<HEAD>");
	out.println("<TITLE>Paramétrage des consignes</TITLE");
	out.println("</HEAD>");
	out.println("<BODY>");
	if(minuteNeon1_Marche < 10)
		out.println("Allumage du néon 1 = " + heureNeon1_Marche +":" + "0"+minuteNeon1_Marche);
	else
		out.println("Allumage du néon 1 = " + heureNeon1_Marche +":" + minuteNeon1_Marche);
	out.println("<br><br>");
	if(minuteNeon1_Arret < 10)
		out.println("Extinction du néon 1 = " + heureNeon1_Arret +":" + "0"+minuteNeon1_Arret);
	else
		out.println("Extinction du néon 1 = " + heureNeon1_Arret +":" + minuteNeon1_Arret);
	out.println("<br><br>");
	if(minuteNeon2_Marche < 10)
		out.println("Allumage du néon 2 = " + heureNeon2_Marche+ ":" + "0"+minuteNeon2_Marche);
	else
		out.println("Allumage du néon 2 = " + heureNeon2_Marche+ ":" +minuteNeon2_Marche);
	out.println("<br><br>");
	if(minuteNeon2_Arret < 10)
		out.println("Extinction du néon 2 = " + heureNeon2_Marche +":" + "0"+minuteNeon2_Arret);
	else
		out.println("Extinction du néon 2 = " + heureNeon2_Marche +":" +minuteNeon2_Arret);
	out.println("<br><br>");
	if(minuteNourriture1_Marche < 10)
		out.println("Première distribution de nourriture = " + heureNourriture1_Marche +":" + "0"+minuteNourriture1_Marche);
	else
		out.println("Première distribution de nourriture = " + heureNourriture1_Marche +":" +minuteNourriture1_Marche);
	out.println("<br><br>");
	if(minuteNourriture2_Marche < 10)
		out.println("Seconde distribution de nourriture = " + heureNourriture2_Marche +":" + "0"+minuteNourriture2_Marche);
	else
		out.println("Seconde distribution de nourriture = " + heureNourriture2_Marche +":"+ minuteNourriture2_Marche);
	out.println("<br><br>");
	if(minuteNourriture3_Marche < 10)
		out.println("Troisième distribution de nourriture = " + heureNourriture3_Marche +":" + "0"+minuteNourriture3_Marche);
	else
		out.println("Troisième distribution de nourriture = " + heureNourriture3_Marche +":" +minuteNourriture3_Marche);
	out.println("<br><br>");
	if(minuteEngrais_Marche < 10)
		out.println("Distribution d'engrais = " + jours +" à " + heureEngrais_Marche +":" +"0"+ minuteEngrais_Marche);
	else
		out.println("Distribution d'engrais = " + jours +" à " + heureEngrais_Marche +":" +minuteEngrais_Marche);
	out.println("<br><br>");
	
	out.println("Valeur du Ph compris entre = " + PhMoins +" et " + PhPlus);
	out.println("<br><br>");
	
	out.println("Température comprise entre = " + TemperatureConsigneMoins +"°c et " + TemperatureConsignePlus +"°c");
	out.println("<br><br>");
	
		
	out.println("</BODY>");
	out.println("</HTML>");
	out.close();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException	
			{
			 doGet(req, res);
			}
	public String getServletInfo() 
	{
	 return "Paramétrage des consignes";
	}
}
