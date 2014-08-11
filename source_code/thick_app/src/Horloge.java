// Projet Aquarium 2006
//Horloge.java récupère le Jour de la semaine, l'heure et les minutes sur la carte TINI
// Ecrit par Ivan TURZANSKI 
//Vesion 1.01

import com.dalsemi.system.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import com.taylec.tini.*;
import com.dalsemi.system.TINIOS;
import com.dalsemi.comm.*;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.adapter.*;
import com.dalsemi.onewire.container.OneWireContainer;
import java.util.Enumeration;



public class Horloge
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;
	
	private int jours;
	private int heures;
	private int minutes;
	private int secondes;


	public int getJour()
	{
		return jours ;
	}
	public int getHeure()
	{
		return heures;
	}
	public int getMinute()
	{
		return minutes ;
	}
	public int getSeconde()
	{
		return secondes ;
	}
// recupJour renvoi le numéro du Jour de la semaine. ex : Jour = 1 = Lundi
	
   public void recupDate()
   {
	
        int  espaces = 0,
	     i =3;
       	char caracteres;
	  
	String jour = new String();
      
        long time = com.dalsemi.system.Clock.getTickCount();
	Date date = new java.util.Date(time);
	String dateStr = new String();
	dateStr = date.toString(); 
        
	
	//jour en string
	jour = dateStr.substring (0,3);

	//comparaison
	if(jour.compareTo("Mon")==0) jours = 1; 
	if(jour.compareTo("Tue")==0) jours = 2;
	if(jour.compareTo("Wed")==0) jours = 3;
	if(jour.compareTo("Thu")==0) jours = 4;
	if(jour.compareTo("Fri")==0) jours = 5;
	if(jour.compareTo("Sat")==0) jours = 6;
	if(jour.compareTo("Sun")==0) jours = 7;

	do
	{
		caracteres =dateStr.charAt(i);
		if(caracteres==' ') espaces=espaces + 1;
		i = i +1;
	} while (espaces < 3);
		
	heures = 10 * ((int)(dateStr.charAt(i))-48) + (int)(dateStr.charAt(i+1))-48;
		 		 
	minutes = 10 * ((int)(dateStr.charAt(i+3))-48) + (int)(dateStr.charAt(i+4))-48;
		
	secondes = 10 * ((int)(dateStr.charAt(i+6))-48) + (int)(dateStr.charAt(i+7))-48;
	}   
}