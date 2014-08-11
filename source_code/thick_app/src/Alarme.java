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

class choixIncorrect extends Exception
{
	public static void message()
	{
		System.out.println("saisie incorrecte");
	}
}

/**
* Cette classe permet d'allumer, d'�teindre une diode et d'�crire sur l'afficheur LCD.
* @author Fabien Marcorelles
* @version 1.2
*/
public class Alarme
{
	static InputStream is  = System.in;
	static PrintStream os = System.out;
	static PrintStream es = System.err;
	
 	private static LEDManip manip;
	private static SGF fichier;
	private static LCDManipFormat manipLCD;
	
	public Alarme()
	{
		
		try
		{
			manip = new LEDManip();
			manipLCD = new LCDManipFormat();
			
		}
		catch(com.dalsemi.system.IllegalAddressException e)
		{
			os.println(e.toString());
		}
		
		fichier = new SGF();
	}
	
	/**
	* Methode permettant d'�teindre la diode s�lectionn�e. La diode �tant un signal d'alarme on efface par la m�me occasion l'�cran LCD.
	* @param choix Diode � �teindre (ex : LED_NIVEAU)
	* @throws choixIncorrect Dans le cas o� le choix de la diode soit diff�rent des possibilit�s.
	*/
	synchronized public void eteindreDiode(int choix) throws choixIncorrect
	{
		try
		{
			if(choix > 5 || choix < 1) throw new choixIncorrect();
			switch(choix)
			{
				case 1: 
				manip.subtract(LEDManip.R3);//eteind diode
				effacerLCD();
				break;
			
				case 2:
				manip.subtract(LEDManip.R2);//eteind diode
				effacerLCD();
				break;

				case 3:
				manip.subtract(LEDManip.G2);//eteind diode
				effacerLCD();
				break;
				
				case 4:
				manip.subtract(LEDManip.G3);//eteind diode
				effacerLCD();
				break;

				case 5:
				manip.subtract(LEDManip.Y);//eteind diode
				effacerLCD();
				break;

				default:
				os.println("default");
				break;
			}
		}
		catch(com.dalsemi.system.IllegalAddressException e)
		{
			os.println(e.toString());
		}
	}
	
	
	/**
	* Methode permettant d'allumer la diode s�lectionn�e. La diode �tant un signal d'alarme on �crit par la m�me occasion un message correspondant � l'alarme sur l'�cran LCD.
	* @param choix2 Diode � allumer (ex : LED_NIVEAU).
	* @throws choixIncorrect Dans le cas o� le choix de la diode soit diff�rent des possibilit�s.
	*/
	synchronized public void allumerDiode(int choix2) throws choixIncorrect
	{
		try
		{
			if(choix2 > 5 || choix2 < 1) throw new choixIncorrect();
			switch(choix2)
			{
				case 1: 
				manip.add(LEDManip.R3);
				messageLCD("Temp�rature trop haute");
				try
				{
					fichier.ecrireAlarme("Temp�rature trop haute");
				}
				catch(java.io.IOException e)
				{
					os.println("Erreur ecriture temperature haute");
				}
				break;
			
				case 2:
				manip.add(LEDManip.R2);
				messageLCD("PH trop haut");
				try
				{
					fichier.ecrireAlarme("pH trop haut");
				}
				catch(java.io.IOException e)
				{
					os.println("Erreur ecriture ph haut");
				}
				break;
				
				case 3:
				manip.add(LEDManip.G2);
				messageLCD("PH trop bas");
				try
				{
					fichier.ecrireAlarme("pH trop bas");
				}
				catch(java.io.IOException e)
				{
					os.println("Erreur ecriture ph bas");
				}
				break;
				
				case 4:
				manip.add(LEDManip.G3);
				messageLCD("Temp�rature trop basse");
				try
				{
					fichier.ecrireAlarme("Temp�rature trop basse");
				}
				catch(java.io.IOException e)
				{
					os.println("Erreur ecriture temperature basse");
				}
				break;

				case 5:
				manip.add(LEDManip.Y);
				messageLCD("Niveau trop bas");
				try
				{
					fichier.ecrireAlarme("Niveau trop bas");
				}
				catch(java.io.IOException e)
				{
					os.println("Erreur ecriture niveau bas");
				}
				break;

				default:
				os.println("default");
				break;
			}
		}
		catch(com.dalsemi.system.IllegalAddressException e)
		{
			os.println(e.toString());
		}
	}
	
	/**
	* Methode permettant d'�crire un message sur l'�cran LCD.
	* @param message Message � afficher sur le LCD (ex : Ph trop bas)
	*/
	synchronized public void messageLCD(String message)
  	{
      		manipLCD.out.println(message);
  	}
	
	/**
	* Methode permettant d'effacer l'�cran LCD.
	*/
	synchronized public void effacerLCD()
	{
		manipLCD.clear();
	}
}
 