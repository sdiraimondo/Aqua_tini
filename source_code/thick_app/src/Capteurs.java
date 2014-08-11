// import com.taylec.tini.*;
// import com.dalsemi.comm.*;
// import com.dalsemi.onewire.adapter.*;
// import com.dalsemi.onewire.OneWireAccessProvider;
// import com.dalsemi.onewire.container.OneWireContainer;

import com.dalsemi.system.I2CPort;
import com.dalsemi.system.IllegalAddressException;

/**
* Lecture des capteurs sur bus I2C avec carte Taylec/TINI et de la carte module CAN PCF8591. Classe utilisé par TGestionPh, TGestionTemperature & TControleNiveau.
* @author Stephane Diraimondo
* @version 1.0
*/
public class Capteurs
{

	private static I2CPort port;
	private static byte[] config;
	private static byte[] data;
	
	private static final int CANNALI2C_TEMPERATURE = 1;
	private static final int CANNALI2C_PH = 2;
	private static final int CANNALI2C_NIVEAU = 3;

	private static final float TENSION_ALIM = 5.0f;	// Tension d'alimentation des capteurs
	private static final int INTERVALLE_I2C = 1000;	// Intervalle entre 2 accés sur bus I2C
	private static final byte ADDR_PCF8591 = 0x48;	// Adresse Hex. du CAN PCF8591

	private static final float VAl_INIT = 0.0f;	// Valeur par défaut pour le calcul des moyennes
	private static final int RETURN_ERROR = -1;	// Erreur de retour


	public Capteurs()
	{
		config = new byte[1];
		data = new byte[2];
		port = new I2CPort();
	}

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
	* Lecture sur bus I2C
	* @param cannal Cannal sur CAN PCF8591 (Temperature : cannal 1 | pH : cannal 2 | Niveau : cannal 3)
	* @return Relevé brut du capteur
	*/
	public synchronized float lecture(int cannal)
	{
		port.slaveAddress = (byte)ADDR_PCF8591;
		port.setClockDelay((byte)INTERVALLE_I2C); // 1000ms : Intervalle entre 2 lectures/écritures I2C

		try
		{
			config[0] = (byte)((byte)0x00 | (byte)(cannal));
			if (port.write(config, 0, 1) < 0)
        		{
          			System.out.println("[ERREUR] : Fail on conv command");
        		}
        		else
			{
				if (port.read(data, 0, 2) < 0)
					System.out.println("[ERREUR] : Erreur de lecture !");
			}
		}
		catch(IllegalAddressException err_)
		{
			System.out.println("[ERREUR] : Acces memoire I2C non autorise !");
		}
	if (data == null)
		return RETURN_ERROR;
	else return (float) ( (float)(data[1] & 0xFF) * (TENSION_ALIM/256) );
	}


	/**
	* A l'aide de la méthode "lecture", récupére n fois la température, effectue la conversion et la moyenne et retourne celle-ci.
	* @param nbrReleve Nombre de relevés consécutifs afin d'effectuer une moyenne
	* @param intervalleMoyenne Intervalle entre 2 relevés
	* @return Moyenne de la température selon le nombre de relévés passés en paramètres de la méthode.
	*/
	public float recupTemperature(int nbrReleve, int intervalleMoyenne)
	{
		float data, temp, moyenne = VAl_INIT;
		int i = 0;
		
		if (nbrReleve <= 0)
			nbrReleve = 1;

		for (i = 0; i < nbrReleve; i++)
		{
			pause(intervalleMoyenne);
			data = (lecture(CANNALI2C_TEMPERATURE));
			if ( (int)data != 0 )
			{
				data = data * 10;
				moyenne += data;
			}
			else nbrReleve--;
		}
		moyenne = (moyenne / (float)nbrReleve);
		return moyenne;
	}


	/**
	* A l'aide de la méthode "lecture", récupére n fois le pH, effectue la conversion et la moyenne et retourne celle-ci.
	* @param nbrReleve Nombre de relevés consécutifs afin d'effectuer une moyenne
	* @param intervalleMoyenne Intervalle entre 2 relevés
	* @return Moyenne du pH selon le nombre de relévés passés en paramètres de la méthode.
	*/
	public float recupPh(int nbrReleve, int intervalleMoyenne)
	{
		int PHMIN = 0;
		int PHMAX = 14;
	
		if (nbrReleve <= 0)
			nbrReleve = 1;
	
		float data, moyenne = VAl_INIT;
		int i = 0;

		for (i=1; i<(nbrReleve+1); i++)
		{
			pause(intervalleMoyenne);
			data = (lecture(CANNALI2C_PH));
			if ((int)data != 0 )
			{
				data = (((-1.10204f) * data) + 9.41f);
				if ((data >= PHMIN) && (data <= PHMAX))
					moyenne += data;
			}
			else nbrReleve--;
		}
		moyenne = (moyenne / (float)nbrReleve);
		return moyenne;
	}


	/**
	* A l'aide de la méthode "lecture", récupére n fois le niveau d'eau, effectue la moyenne et retourne celle-ci.
	* @param nbrReleve Nombre de relevés consécutifs afin d'effectuer une moyenne
	* @param intervalleMoyenne Intervalle entre 2 relevés
	* @return Moyenne du niveau d'eau selon le nombre de relévés passés en paramètres de la méthode.
	*/
	public float recupNiveau(int nbrReleve, int intervalleMoyenne)
	{
		float data, niveau, moyenne = VAl_INIT;
		int i = 0;
		
		if (nbrReleve <= 0)
			nbrReleve = 1;
		
		for (i=1; i<(nbrReleve+1); i++)
		{
			pause(intervalleMoyenne);
			data = (lecture(CANNALI2C_NIVEAU));
			if ( (int)data != 0 )
				moyenne += data;
			else nbrReleve--;
		}
		moyenne = (moyenne / (float)nbrReleve);
		niveau = moyenne;
		return niveau;
	}

}
