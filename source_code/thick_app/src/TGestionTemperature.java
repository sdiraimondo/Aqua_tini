import java.lang.*;
import java.util.*;
import java.io.*;
import java.lang.String;

/**
* Cette classe permet de g�rer la temp�rature, c'est � dire relev� gr�ce � un capteur la temp�rature et allumer ou �teindre la r�sistance chauffante. Le Thread TGestionTemperature va relev� la temp�rature de l'eau sur le capteur toutes les 6 minutes.
* @author Fabien Marcorelles
* @version 1.3
*/
public class TGestionTemperature extends Thread
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;
	
 	static final int MARCHE = 0; //permet de declencher l'actionneur
 	static final int ARRET = 1; //arret de l'actionneur
 	
 	static final char PORT_B = 'B';

 	static final int ACT_CHAUFFAGE = (byte)0x10; //actionneur chauffage

	
	static final int LED_TEMPERATURE_HAUTE = 1;
	static final int LED_TEMPERATURE_BASSE = 4;
	
	private float TemperatureConsignePlus;
	private float TemperatureConsigneMoins;
	/**
	* Temp�rature relev� sur le capteur.
	*/
	private float TemperatureActu;
	private float ToleranceTemperature;
	
	private int delai = 16000;//360000;
	
	private Actionneurs act;
	private Alarme alarme;
	private Capteurs capteur;
	private SGF sgf;
	
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
	
	/**
	* @param act Objet de la classe Actionneurs
	* @see Actionneurs
	* @param alarme Objet de la classe Alarme
	* @see Alarme
	* @param capteur Objet de la classe Capteurs
	* @see Capteurs
	* @param sgf Objet de la classe SGF
	* @see SGF
	*/
	public TGestionTemperature(Actionneurs act, Alarme alarme, Capteurs capteur, SGF sgf)
	{
		this.act = act;
		this.alarme = alarme;
		this.capteur = capteur;
		this.sgf = sgf;
	}
	
	public void run()
	{
		TemperatureConsignePlus = sgf.TemperatureConsignePlus;
		TemperatureConsigneMoins = sgf.TemperatureConsigneMoins;
		ToleranceTemperature = sgf.ToleranceTemperature;
		
		while(true)
		{
			pause(delai);
			
			TemperatureActu = capteur.recupTemperature(5,5000);
			os.println("Temp�rature "+TemperatureActu+"�C");
			
			//---------------------------------------------------------------------------------------------------//
			//         Temp�rature inf�rieur � la consigneMoins  et sup�rieur � la consigneMoins - Tolerance     //
			//---------------------------------------------------------------------------------------------------//
			if((TemperatureActu < TemperatureConsigneMoins) && (TemperatureActu > TemperatureConsigneMoins - ToleranceTemperature))
			{
				act.declencher(PORT_B, ACT_CHAUFFAGE, MARCHE);
				try
				{
					alarme.eteindreDiode(LED_TEMPERATURE_BASSE);
					alarme.eteindreDiode(LED_TEMPERATURE_HAUTE);
				}
				catch(choixIncorrect e)
				{
					os.println("Erreur " + e);
				}
			}

			//------------------------------------------------------------------------------------------------//
			//         Temp�rature sup�rieur � la consignePlus et inf�rieur � la consignePlus+Tolerance       //
			//------------------------------------------------------------------------------------------------//
			if((TemperatureActu > TemperatureConsignePlus) && (TemperatureActu < TemperatureConsignePlus + ToleranceTemperature))
			{
				act.declencher(PORT_B, ACT_CHAUFFAGE, ARRET);
				try
				{
					alarme.eteindreDiode(LED_TEMPERATURE_BASSE);
					alarme.eteindreDiode(LED_TEMPERATURE_HAUTE);
				}
				catch(choixIncorrect e)
				{
					os.println("Erreur " + e);
				}
			}

			
			//---------------------------------------------------------------//
			//         Temp�rature sup�rieur � la consigne + tol�rance       //
			//---------------------------------------------------------------//
			if(TemperatureActu > TemperatureConsignePlus + ToleranceTemperature)
			{
				act.declencher(PORT_B, ACT_CHAUFFAGE, ARRET);
				try
				{
					alarme.eteindreDiode(LED_TEMPERATURE_BASSE);
					alarme.allumerDiode(LED_TEMPERATURE_HAUTE);
				}
				catch(choixIncorrect e)
				{
					os.println("Erreur " + e);
				}
			}
			
			//---------------------------------------------------------------//
			//         Temp�rature inf�rieur � la consigne - tol�rance       //
			//---------------------------------------------------------------//
			if(TemperatureActu < TemperatureConsigneMoins - ToleranceTemperature)
			{
				act.declencher(PORT_B, ACT_CHAUFFAGE, MARCHE);
				try
				{
					alarme.eteindreDiode(LED_TEMPERATURE_HAUTE);
					alarme.allumerDiode(LED_TEMPERATURE_BASSE);
				}
				catch(choixIncorrect e)
				{
					os.println("Erreur " + e);
				}
			}
		}
	}
} 
