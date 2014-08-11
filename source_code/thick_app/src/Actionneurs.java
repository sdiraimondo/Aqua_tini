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

/**
* Cette classe permet d'allumer ou d'éteindre un actionneur sans modifier l'état des autres actionneurs.
* @author Fabien Marcorelles
* @version 1.2
*/
public class Actionneurs
{
 	private static ProgramIOManip pio;
	static PrintStream os = System.out;
	
	public Actionneurs()
	{
		try
		{
			pio = new ProgramIOManip();
		}
		catch(com.dalsemi.system.IllegalAddressException e)
		{
			os.println(e.toString());
		}
	}

	/**
	* Change l'état de l'actionneur passé en paramétre.
	* @param choixPort Choix du port où se trouve l'actionneur (ex : PORT_A).
	* @param choixActionneur Choix de l'actionneur à modifier, (ex : ACT_CHAUFFAGE)
	* @param cmd Etat à affecter (ex : Marche ou Arrêt).
	*/
	public synchronized void declencher(char choixPort, int choixActionneur, int cmd)
	{

		int commandePrecedente;
		int commande;
			
		if(cmd == 1)
		{
			cmd = choixActionneur;
		}
		else
		{
			cmd = (byte)0x00;
		}
			
		switch(choixPort)
		{
			case 'A':
				commandePrecedente = pio.readPortA();  //lecture du port A pour récupérer l'état actuel
				commande = commandePrecedente & ~choixActionneur;
				commande = commande | (choixActionneur & cmd);
				pio.writePortA(commande); //modification de l'état
				break;
			case 'B':
				commandePrecedente = pio.readPortB();  //lecture du port B pour récupérer l'état actuel
				commande = commandePrecedente & ~choixActionneur;
				commande = commande | (choixActionneur & cmd);
				pio.writePortB(commande); //modification de l'état
				break;
			case 'C':
				commandePrecedente = pio.readPortC();  //lecture du port C pour récupérer l'état actuel
				commande = commandePrecedente & ~choixActionneur;
				commande = commande | (choixActionneur & cmd);
				pio.writePortC(commande); //modification de l'état
				break;
			default:
				os.println("Erreur");
		}
	}
}