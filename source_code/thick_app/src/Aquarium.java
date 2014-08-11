import java.lang.*;
import java.util.*;
import java.io.*;

/**
* Classe principale qui charge toutes les consignes, les événements et lance les quatres threads.
* @author Stéphane Diraimondo, Fabien Marcorelles, Ivan Turzanski
* @version 1.2
*/
public class Aquarium
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;
	
	/**
	* @see TGestionTemperature
	* @see TControleNiveau
	* @see TGestionPh
	* @see TCommander
	* @see SGF
	*/
	public static void main(String[] args)
	{
		
		Actionneurs act = new Actionneurs();
		Alarme alarme = new Alarme();
		Capteurs capteur = new Capteurs();
		SGF sgf = new SGF();

		sgf.chargementDesValeurs();
		
		TGestionTemperature GestionTemperature = new TGestionTemperature(act, alarme, capteur, sgf);
		GestionTemperature.start();
 		TControleNiveau ControleNiveau = new TControleNiveau(alarme, capteur, sgf);
 		ControleNiveau.start();
		TGestionPh GestionPh = new TGestionPh(act, alarme, capteur, sgf);
		GestionPh.start();
		TCommander Commander = new TCommander(act, sgf);
 		Commander.start();
	}
}