import java.io.*;
import java.util.*;
import java.lang.*;


public class TGestionPh extends Thread
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;

	static final int MARCHE = 0; //permet de declencher l'actionneur
	static final int ARRET = 1; //arret de l'actionneur
 
	static final char PORT_A = 'A';
	static final char PORT_C = 'C'; 

	static final int ACT_BICARBONATE = (byte)0x01; //actionneur bicarbonate Ph+ - PORT A
	static final int ACT_CO2 = (byte)0x40; //actionneur Ph- CO2 - PORT C

	static final int LED_CO2 = 2;
	static final int LED_BICARBONATE = 3;
	
	static final int NBRE_RELEVES = 5;
	static final int INTERVALLE_RELEVE = 1000;
	static final int INTERVALLE_BICARBONATE = 100;

	float PhTolerance;
	float PhMoins;
	float PhPlus;
	float PhActu = 0.0f;
	
	private int delai = 0;
	
	private Actionneurs act;
	private Alarme alarme;
	private Capteurs capteur;
	private SGF sgf;


	/**
	* Effectue une pause
	* @param delay Temps en ms
	*/
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
	* @param intervalle
	*/
	public TGestionPh(Actionneurs act, Alarme alarme, Capteurs capteur, SGF sgf, int intervalle)
	{
		this.act = act;
		this.alarme = alarme;
		this.capteur = capteur;
		this.sgf = sgf;
		delai = intervalle;
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
	public TGestionPh(Actionneurs act, Alarme alarme, Capteurs capteur, SGF sgf)
	{
		this.act = act;
		this.alarme = alarme;
		this.capteur = capteur;
		this.sgf = sgf;
		delai = 10000;// par défaut : 600000 (10 minutes)
	}
	
	/**
	* Crée une impulsion sur la prise du bicarbonate
	* @param delay Temps en ms
	*/
	void impulsionBicarbonate(int delai)
	{
		act.declencher(PORT_A, ACT_BICARBONATE, MARCHE);
		pause(delai);
		act.declencher(PORT_A, ACT_BICARBONATE, ARRET);
	}

	/**
	* Thread de régulation du pH
	*/
	public void run()
	{
		PhTolerance = sgf.TolerancePh;
		PhMoins = sgf.PhMoins;
		PhPlus = sgf.PhPlus;
		
		while(true)
		{
			pause(delai);

			PhActu = capteur.recupPh(NBRE_RELEVES, INTERVALLE_RELEVE);

			// pH correct, alors le CO2 ainsi que les LEDs sont (éventuellement) arrêtés
			os.println("pH = "+PhActu);
			
			if((PhActu > PhMoins) && (PhActu < PhPlus))
			{
				act.declencher(PORT_A, ACT_CO2, ARRET);
				act.declencher(PORT_A, ACT_BICARBONATE, ARRET);
				try
				{
					alarme.eteindreDiode(LED_CO2);
					alarme.eteindreDiode(LED_BICARBONATE);
				}
				catch(choixIncorrect e)
				{
					os.println("[ERREUR] " + e);
				}
			}

			// pH inférieur à la consigne (mais pas à consigne - tolerance) - Bicarbonate de soude
			if((PhActu < PhMoins) && (PhActu > (float)(PhMoins - PhTolerance)))
			{
				act.declencher(PORT_C, ACT_CO2, ARRET);
				impulsionBicarbonate(INTERVALLE_BICARBONATE);
				
				//act.declencher(PORT_A, ACT_BICARBONATE, MARCHE);
				//act.declencher(PORT_A, ACT_BICARBONATE, ARRET);
				
				try
				{
					alarme.eteindreDiode(LED_BICARBONATE);
					alarme.eteindreDiode(LED_CO2);
				}
				catch(choixIncorrect e)
				{
					os.println("[ERREUR] " + e);
				}
			}

			// pH inférieur à la consigne - tolérance - Bicarbonate de soude
			if(PhActu < (PhMoins - PhTolerance))
			{
				act.declencher(PORT_C, ACT_CO2, ARRET);
				impulsionBicarbonate(INTERVALLE_BICARBONATE);
				
				//act.declencher(PORT_A, ACT_BICARBONATE, MARCHE);
				//act.declencher(PORT_A, ACT_BICARBONATE, ARRET);
				try
				{
					alarme.allumerDiode(LED_BICARBONATE);
					alarme.eteindreDiode(LED_CO2);
				}
				catch(choixIncorrect e)
				{
					os.println("[ERREUR] " + e);
				}
			}

			// pH supérieur à la consigne (mais pas à consigne + tolérance) - CO2
			if((PhActu > PhPlus) && (PhActu < (float)(PhPlus + PhTolerance)))
			{
				act.declencher(PORT_C, ACT_CO2, MARCHE);
				act.declencher(PORT_A, ACT_BICARBONATE, ARRET);
				try
				{
					alarme.eteindreDiode(LED_BICARBONATE);
					alarme.allumerDiode(LED_CO2);
				}
				catch(choixIncorrect e)
				{
					os.println("[ERREUR] " + e);
				}
			}

			// pH supérieur à la consigne + tolérance - CO2
			if(PhActu > (PhPlus + PhTolerance))
			{
				act.declencher(PORT_C, ACT_CO2, MARCHE);
				act.declencher(PORT_A, ACT_BICARBONATE, ARRET);
				try
				{
					alarme.eteindreDiode(LED_BICARBONATE);
					alarme.allumerDiode(LED_CO2);
				}
				catch(choixIncorrect e)
				{
					os.println("[ERREUR] " + e);
				}
			}
		}
	}
} 
