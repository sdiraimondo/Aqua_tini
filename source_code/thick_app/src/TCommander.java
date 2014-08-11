//Projet Aquarium 2006
//Thread TCommander Déclenche les différents actionneurs de l'aquarium au heures définies; recupère dans Horloge.java la date 
//Ecrit par Ivan Turzanski
//Version 1.02



import java.lang.*;
import java.util.*;
import java.io.*;


public class TCommander extends Thread
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;
	
  	static final int MARCHE = 0; //permet de declencher l'actionneur
  	static final int ARRET = 1; //arret de l'actionneur
	
 	static final char PORT_A = 'A';
 	static final char PORT_B = 'B';
 	static final char PORT_C = 'C'; 

 	static final int ACT_NEON1 = (byte)0x02; //actionneur lumiere 1
 	static final int ACT_NEON2 = (byte)0x80; //actionneur lumière 2
 	static final int ACT_NOURRITURE = (byte)0x10; //actionneur nourriture
 	static final int ACT_OXYGENE = (byte)0x10; //actionneur oxygene
 	static final int ACT_ENGRAIS = (byte)0x40;//actionneur filtration
 	static final int ACT_FILTRATION = (byte)0x80; //actionneur engrais
	
	
	private int i = 0;
	private int jours = 0; 
	private int minutes = 0;
	private int heures = 0;
	private int secondes = 0;
	
	private int manger = 0;
	private int engrais = 0;
	
	private int heureNeon1_Marche;
	private int minuteNeon1_Marche;
	
	private int heureNeon2_Marche;
	private int minuteNeon2_Marche;
	
	private int heureNeon1_Arret;
	private int minuteNeon1_Arret;
	
	private int heureNeon2_Arret;
	private int minuteNeon2_Arret; 
	
	private int heureNourriture1_Marche;
	private int minuteNourriture1_Marche;
	
	private int heureNourriture2_Marche;
	private int minuteNourriture2_Marche;
	
	private int heureNourriture3_Marche;
	private int minuteNourriture3_Marche;
		
	private int heureFiltration1_Marche;
	private int minuteFiltration1_Marche;
	
	private int heureFiltration2_Marche;
	private int minuteFiltration2_Marche;
	
	private int heureFiltration3_Marche;
	private int minuteFiltration3_Marche;
	
	private int jourEngrais_Marche;
	private int heureEngrais_Marche;
	private int minuteEngrais_Marche;
	
	
	private static Horloge Temps;
	private Actionneurs act;
	private SGF sgf;
	
	public TCommander(Actionneurs act, SGF sgf)   //constructeur
	{
		this.act = act;
		this.sgf = sgf;
	}
	
	//methode effectuant un sleep
	private static final void pause(int delay_)
	{
		try
		{
			Thread.sleep(delay_);
		}
		catch (InterruptedException e_)
		{
		}
	}
	
	
	public void run()
	{
		Actionneurs act = new Actionneurs();
		Horloge Temps = new Horloge();
		int i, j = 0;

		heureNeon1_Marche = sgf.heureNeon1_Marche;
		minuteNeon1_Marche = sgf.minuteNeon1_Marche;
		
		heureNeon2_Marche = sgf.heureNeon2_Marche;
		minuteNeon2_Marche = sgf.minuteNeon2_Marche;
		
		heureNeon1_Arret = sgf.heureNeon1_Arret;
		minuteNeon1_Arret = sgf.minuteNeon1_Arret;
		
		heureNeon2_Arret = sgf.heureNeon2_Arret;
		minuteNeon2_Arret = sgf.minuteNeon2_Arret;
		
		heureNourriture1_Marche = sgf.heureNourriture1_Marche;
		minuteNourriture1_Marche = sgf.minuteNourriture1_Marche;
		
		heureNourriture2_Marche = sgf.heureNourriture2_Marche;
		minuteNourriture2_Marche = sgf.minuteNourriture2_Marche;
		
		heureNourriture3_Marche = sgf.heureNourriture3_Marche;
		minuteNourriture3_Marche = sgf.minuteNourriture3_Marche;
		
		heureFiltration1_Marche = sgf.heureFiltration1_Marche;
		minuteFiltration1_Marche = sgf.minuteFiltration1_Marche;

		heureFiltration2_Marche = sgf.heureFiltration2_Marche;
		minuteFiltration2_Marche = sgf.minuteFiltration2_Marche;

		heureFiltration3_Marche = sgf.heureFiltration3_Marche;
		minuteFiltration3_Marche = sgf.minuteFiltration3_Marche;
		
		jourEngrais_Marche = sgf.jourEngrais_Marche;
		heureEngrais_Marche = sgf.heureEngrais_Marche;
		minuteEngrais_Marche = sgf.minuteEngrais_Marche;
		
		
		
		
		while(true)
		{
			Temps.recupDate();
			jours = Temps.getJour();
			heures = Temps.getHeure();
			minutes = Temps.getMinute();
	
			if((heures == 0) && (minutes ==0))
			{
			manger = 0;
			engrais = 0;
			}
			
			//----------------------------------------------------//
			//                          Oxygenation               //
			//----------------------------------------------------//
			act.declencher(PORT_A, ACT_OXYGENE, MARCHE);
				
			
			//----------------------------------------------------//
			//        Allumage et Extenction des Néons 1 et 2     //
			//----------------------------------------------------// 
			i= heures * 60 + minutes;
			if(((heureNeon1_Marche * 60 + minuteNeon1_Marche) < i) && ((heureNeon1_Arret*60 + minuteNeon1_Arret) > i))
			 	act.declencher(PORT_C, ACT_NEON1, MARCHE);
			else 
				act.declencher(PORT_C, ACT_NEON1, ARRET);
			
			if(((heureNeon2_Marche * 60 + minuteNeon2_Marche) < i) && ((heureNeon2_Arret*60 + minuteNeon2_Arret) > i)) 
				act.declencher(PORT_A, ACT_NEON2, MARCHE);	
			else 
				act.declencher(PORT_A, ACT_NEON2, ARRET);
			
				
		        //----------------------------------------------------//
			//             Distribution de nourriture             //
			//----------------------------------------------------//
			if (jours == 2)
			{
				act.declencher(PORT_B, ACT_FILTRATION, MARCHE);
			}
			else
			{
				if(((manger == 0) &&  (heures == heureNourriture1_Marche ) && (minutes == minuteNourriture1_Marche)))
				{
					act.declencher(PORT_C, ACT_NOURRITURE, MARCHE);
					pause(1000);
					act.declencher(PORT_C, ACT_NOURRITURE, ARRET);
					manger = manger + 1;
				}   
	        	   
				if(((manger == 1) && (heures == heureNourriture2_Marche ) && (minutes == minuteNourriture2_Marche)))
				{
					act.declencher(PORT_C, ACT_NOURRITURE, MARCHE);
					pause(1000);
					act.declencher(PORT_C, ACT_NOURRITURE, ARRET); 
					manger = manger + 1;
					
				}
				
				if(((manger == 2) && (heures == heureNourriture3_Marche ) && (minutes == minuteNourriture3_Marche)))
				{
					act.declencher(PORT_C, ACT_NOURRITURE, MARCHE);
					pause(1000);
					act.declencher(PORT_C, ACT_NOURRITURE, ARRET);
					manger = manger + 1;
				}
			
				//----------------------------------------------------//
				//                      Filtration                    //
				//----------------------------------------------------//
				if
				((((heureNourriture1_Marche * 60 + minuteNourriture1_Marche) <= i) && ((heureFiltration1_Marche * 60 + minuteFiltration1_Marche) >= i))
				|| (((heureNourriture2_Marche * 60 + minuteNourriture2_Marche) <= i) && ((heureFiltration2_Marche * 60 + minuteFiltration2_Marche) >= i))
				|| (((heureNourriture3_Marche * 60 + minuteNourriture3_Marche) <= i) && ((heureFiltration3_Marche * 60 + minuteFiltration3_Marche) >= i))
				)
				{
					act.declencher(PORT_B, ACT_FILTRATION, ARRET);
				}
				else 
				{
					act.declencher(PORT_B, ACT_FILTRATION, MARCHE);   
				}
			}
			
			
			//----------------------------------------------------//
			//                      Engrais                       //
			//----------------------------------------------------//
			if ((engrais == 0) && (jours == jourEngrais_Marche ) && (heures == heureEngrais_Marche ) && (minutes == minuteEngrais_Marche))
			{
				act.declencher(PORT_B, ACT_ENGRAIS, MARCHE);  
				act.declencher(PORT_B, ACT_ENGRAIS, ARRET);
				engrais = engrais + 1;
				pause(2000);
			}
			else 
			{
				act.declencher(PORT_B, ACT_ENGRAIS, ARRET);
			}
			pause(5000);
		}
	}
} 