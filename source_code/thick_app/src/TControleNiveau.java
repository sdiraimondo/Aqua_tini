import java.lang.*;
import java.util.*;
import java.io.*;

/**
* Cette classe permet de contrôler le niveau de l'eau et de prévenir l'utilisateur grâce à une alarme d'un manque d'eau. Le Thread TContrôleNiveau va relevé le niveau de l'eau sur le capteur toutes les 30 minutes.
* @author Fabien Marcorelles
* @version 1.2
*/
public class TControleNiveau extends Thread
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;
	
	static final int LED_TEMPERATURE_HAUTE = 1;
	static final int LED_CO2 = 2;
	static final int LED_BICARBONATE = 3;
	static final int LED_TEMPERATURE_BASSE = 4;
	static final int LED_NIVEAU = 5;
	
	private int delai = 10000;//1800000;
	
	/**
	* Seuil de niveau d'eau, lu dans le fichier de consignes.
	*/
	private float NiveauConsigne;
	/**
	* Niveau d'eau relevé sur le capteur.
	*/
	private float NiveauActu = 0.0f;
	
	
	private Alarme alarme;
	private Capteurs capteur;
	private SGF sgf;
	
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
	* @param alarme Objet de la classe Alarme
	* @see Alarme
	* @param capteur Objet de la classe Capteurs
	* @see Capteurs
	* @param sgf Objet de la classe SGF
	* @see SGF
	*/
	public TControleNiveau(Alarme alarme, Capteurs capteur, SGF sgf)
	{
		this.alarme = alarme;
		this.capteur = capteur;
		this.sgf = sgf;
	}
	
	public void run()
	{
		NiveauConsigne =sgf.NiveauConsigne;
		
		while(true)
		{
			pause(delai);
			
			NiveauActu = capteur.recupNiveau(5,5000);
			os.println("niveau = " + NiveauActu);
			
			if(NiveauActu < NiveauConsigne)
			{
				try
				{
					alarme.allumerDiode(LED_NIVEAU);
					pause(1500);
				}
				catch(choixIncorrect e)
				{
					os.println("Erreur " + e);
				}
			}
			else
			{
				try
				{
					alarme.eteindreDiode(LED_NIVEAU);
					pause(1500);
				}
				catch(choixIncorrect e)
				{
					os.println("Erreur " + e);
				}
			}
		}
	}
}
