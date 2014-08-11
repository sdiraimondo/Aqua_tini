import java.io.* ;
import java.util.* ;
import java.util.Date;
import java.lang.Object;
import java.lang.String;
import java.lang.*;
import java.io.File;

/*
 * SGF.java
 * Created on 27 mars 2006, 14:31
 * @author Fabien Marcorelles
 */

public class SGF {

	public float TemperatureConsigneMoins;
	public float TemperatureConsignePlus;
	public float ToleranceTemperature;
	public float NiveauConsigne;
	public float PhMoins;
	public float PhPlus;
	public float TolerancePh;
	
	public int heureNeon1_Marche;
	public int minuteNeon1_Marche;
	public int heureNeon2_Marche;
	public int minuteNeon2_Marche;
	public int heureNeon1_Arret;
	public int minuteNeon1_Arret;
	public int heureNeon2_Arret;
	public int minuteNeon2_Arret;
	public int heureNourriture1_Marche;
	public int minuteNourriture1_Marche;
	public int heureNourriture2_Marche;
	public int minuteNourriture2_Marche;
	public int heureNourriture3_Marche;
	public int minuteNourriture3_Marche;
	public int heureFiltration1_Arret;
	public int minuteFiltration1_Arret;
	public int heureFiltration1_Marche;
	public int minuteFiltration1_Marche;
	public int heureFiltration2_Arret;
	public int minuteFiltration2_Arret;
	public int heureFiltration2_Marche;
	public int minuteFiltration2_Marche;
	public int heureFiltration3_Arret;
	public int minuteFiltration3_Arret;
	public int heureFiltration3_Marche;
	public int minuteFiltration3_Marche;
	public int jourEngrais_Marche;
	public int heureEngrais_Marche;
	public int minuteEngrais_Marche;
	

	private static String nomfichierConsignes = "/usr/local/jakarta-tomcat-4.1.31/webapps/aqua/WEB-INF/classes/consignes.dat";
        private static String nomfichierEvenements = "/usr/local/jakarta-tomcat-4.1.31/webapps/aqua/WEB-INF/classes/evenements.dat";
	private static String nomFichierAlarme = "alarme.log";

	private static PrintWriter fichierCreationConsigne;
	private static PrintWriter fichierAlarme;
	private static PrintWriter fichierCreationEvenements;

	//private String[][] tabEvenements = new String[40][40];
	
    	synchronized public float recupConsignes(String grandeurRecup) throws IOException
	{
        	String chaine = " ";
		String champValeur = "";
		String champGrandeur;
		int nombreLigne = 0;
		int nv = 0;
		int j = 0;
		
	        BufferedReader entreeConsignes = new BufferedReader (new FileReader(nomfichierConsignes));
	        String[][] tab = new String[20][20];
	        while (true)
        	{	
            		String ligneLue = entreeConsignes.readLine() ;
            		if (ligneLue == null) break ;
            		StringTokenizer tok = new StringTokenizer (ligneLue, "=");
            		nv = tok.countTokens();
            		for (int i = 0 ; i < nv ; i++)
            		{
                		chaine = tok.nextToken();
	        		tab[i][j] = chaine;
            		}
            		j++;
	    		nombreLigne++; //compte le nombre de lignes
        	}
		entreeConsignes.close ();
	
	        //------------------------------------------------------------------//
		// Recherche dans la colonne des grandeurs la chaine correspondante //
		//------------------------------------------------------------------//
		for(int k = 0; k < nombreLigne; k++)
		{
			champGrandeur = tab[0][k];
			if(champGrandeur.equals(grandeurRecup))
			{
				champValeur = tab[1][k];
			}
		}
		return Float.valueOf(champValeur).floatValue();
	}
	
	synchronized public int recupEvenements(String grandeurRecup) throws IOException
	{
        	String chaine = " ";
		String champValeur = "";
		String champGrandeur;
		int nombreLigne = 0;
		int nv = 0;
		int j = 0;
		
	        BufferedReader entreeEvenements = new BufferedReader (new FileReader(nomfichierEvenements));
	        String[][] tabEvenements = new String[25][25];
	
		
	        while (true)
        	{
            		String ligneLue = entreeEvenements.readLine() ;
            		if (ligneLue == null) break ;
            		StringTokenizer tok = new StringTokenizer (ligneLue, "=");
            		nv = tok.countTokens();
            		for (int i = 0 ; i < nv ; i++)
            		{
                		chaine = tok.nextToken();
	        		tabEvenements[i][j] = chaine;
            		}
            		j++;
	    		nombreLigne++; //compte le nombre de lignes
        	}
		entreeEvenements.close();
	
	        //------------------------------------------------------------------//
		// Recherche dans la colonne des grandeurs la chaine correspondante //
		//------------------------------------------------------------------//
		for(int k = 0; k < nombreLigne; k++)
		{
			champGrandeur = tabEvenements[0][k];
			if(champGrandeur.equals(grandeurRecup))
			{
				champValeur = tabEvenements[1][k];
			}
		}		
 		return Integer.parseInt(champValeur);
	}

	//--------------------------------------------------------------------//
	//      Ecriture des rapports d'erreur lors d'une alarme              //
	//--------------------------------------------------------------------//
	synchronized public void ecrireAlarme(String messageAlarme) throws IOException
	{
		int mois;
		int jours;
		int heures;
		int minutes;
		int annee;

		try
		{
			FileWriter fw = new FileWriter(nomFichierAlarme, true);
 		        BufferedWriter bw = new BufferedWriter(fw);
			fichierAlarme = new PrintWriter(bw);
			//fichier = new PrintWriter(bw);
		}
		catch(java.io.IOException e)
		{
			System.out.println("Erreur fichier alarme.log " + e.toString());
		}

		Date date = new Date();
		minutes = date.getMinutes();
		heures = date.getHours();
		mois = date.getMonth();
		annee = date.getYear();
		jours = date.getDay();
		
		annee = annee + 1900; //car retourne le résultat du nombre d'année écoulées à partir de 1900

		//conversion en chaine pour pouvoir rajouter le "0" devant les jours, mois, heures, minutes à un seul chiffre. On test la longeur de la chaine grâce à length()
		String min = String.valueOf(minutes);
		int longeur = min.length();
		if(longeur == 1)
		{
			min = "0"+min;
		}
		
		String hrs = String.valueOf(heures);
		longeur = hrs.length();
		if(longeur == 1)
		{
			hrs = "0"+hrs;
		}
		
		String month = String.valueOf(mois);
		longeur = month.length();
		if(longeur == 1)
		{
			month = "0"+month;
		}

		String day = String.valueOf(jours);
		longeur = day.length();
		if(longeur == 1)
		{
			day = "0"+day;
		}

		fichierAlarme.println(day+"/"+month+"/"+annee+" "+hrs+":"+min+" - "+messageAlarme);
		fichierAlarme.close();
	}
	
	//----------------------------------------------------------------------------------------------------------------//
	// Chargement des consignes et des événements. Si le fichier n'existe pas alors on charge les valeurs par défauts //
	//----------------------------------------------------------------------------------------------------------------//
	synchronized public void chargementDesValeurs()
	{
		File fichierConsignes = new File(nomfichierConsignes);
		File fichierEvenements = new File(nomfichierEvenements);
		
		if(fichierConsignes.exists())
		{
			try
			{
				System.out.println("Chargement des consignes en cours ...");
				TemperatureConsigneMoins = recupConsignes("TemperatureConsigneMoins");
				TemperatureConsignePlus = recupConsignes("TemperatureConsignePlus");
				ToleranceTemperature = recupConsignes("ToleranceTemperature");
				NiveauConsigne = recupConsignes("niveauConsigne");
				PhMoins = recupConsignes("PhMoins");
				PhPlus = recupConsignes("PhPlus");
				TolerancePh = recupConsignes("TolerancePh");
				System.out.println("Chargement des consignes terminées.");
			}
			catch(java.io.IOException e)
			{
				System.out.println("Erreur recup");
				
			}
		}
		else
		{
			System.out.println("Le fichier est inexistant chargement des consignes par défaut ...");
			try
			{
				ecrireAlarme("Fichier consignes.dat absent");
			}
			catch(java.io.IOException e)
			{
				System.out.println("Erreur ecriture alarme consignes.dat absent");
			}
			TemperatureConsigneMoins=24.0f;
			TemperatureConsignePlus=26.0f;
			ToleranceTemperature=1.0f;
			NiveauConsigne=2.90f;
			PhMoins=6.5f;
			PhPlus=8.0f;
			TolerancePh=0.5f;
			System.out.println("Chargement des consignes par défaut terminées.");
			System.out.println("Création du fichier de consignes ...");
			try
			{
				FileWriter fw = new FileWriter(nomfichierConsignes, true);
				BufferedWriter bw = new BufferedWriter(fw);
				fichierCreationConsigne = new PrintWriter(bw);
			}
			catch(java.io.IOException e)
			{
				System.out.println("Erreur création fichier consignes.dat " + e.toString());
			}
			fichierCreationConsigne.println("TemperatureConsigneMoins=24.0");
			fichierCreationConsigne.println("TemperatureConsignePlus=26.0");
			fichierCreationConsigne.println("ToleranceTemperature=1.0");
			fichierCreationConsigne.println("niveauConsigne=2.90");
			fichierCreationConsigne.println("PhMoins=6.5");
			fichierCreationConsigne.println("PhPlus=8.0");
			fichierCreationConsigne.println("TolerancePh=0.5");
			if(fichierConsignes.exists())
			{
				System.out.println("Création du fichier consignes.dat terminé avec succés.");
				try
				{
					ecrireAlarme("Fichier consignes.dat recréé");
				}
				catch(java.io.IOException e)
				{
					System.out.println("Erreur ecriture alarme consignes.dat recrée");
				}
			}
			fichierCreationConsigne.close();
		}
		
		if(fichierEvenements.exists())
		{
			try
			{
				System.out.println("Chargement des événements en cours ...");
				heureNeon1_Marche = recupEvenements("heureNeon1_Marche");
				minuteNeon1_Marche = recupEvenements("minuteNeon1_Marche");
				heureNeon2_Marche = recupEvenements("heureNeon2_Marche");
				minuteNeon2_Marche = recupEvenements("minuteNeon2_Marche");
				heureNeon1_Arret = recupEvenements("heureNeon1_Arret");
				minuteNeon1_Arret = recupEvenements("minuteNeon1_Arret");
				heureNeon2_Arret= recupEvenements("heureNeon2_Arret");
				minuteNeon2_Arret= recupEvenements("minuteNeon2_Arret");
				heureNourriture1_Marche = recupEvenements("heureNourriture1_Marche");
				minuteNourriture1_Marche = recupEvenements("minuteNourriture1_Marche");
				heureNourriture2_Marche = recupEvenements("heureNourriture2_Marche");
				minuteNourriture2_Marche = recupEvenements("minuteNourriture2_Marche");
				heureNourriture3_Marche = recupEvenements("heureNourriture3_Marche");
				minuteNourriture3_Marche = recupEvenements("minuteNourriture3_Marche");
				heureFiltration1_Marche = recupEvenements("heureFiltration1_Marche");
				minuteFiltration1_Marche = recupEvenements("minuteFiltration1_Marche");
				heureFiltration2_Marche = recupEvenements("heureFiltration2_Marche");
				minuteFiltration2_Marche = recupEvenements("minuteFiltration2_Marche");
				heureFiltration3_Marche = recupEvenements("heureFiltration3_Marche");
				minuteFiltration3_Marche = recupEvenements("minuteFiltration3_Marche");
				jourEngrais_Marche = recupEvenements("jourEngrais_Marche");
				heureEngrais_Marche = recupEvenements("heureEngrais_Marche");
				minuteEngrais_Marche = recupEvenements("minuteEngrais_Marche");
				System.out.println("Chargement des événements terminés.");
			}
			catch(java.io.IOException e)
			{
				System.out.println("Erreur recup");
				
			}
		}
		else
		{
			System.out.println("Le fichier est absent chargement des événements par défaut ...");
			try
			{
				ecrireAlarme("Fichier evenements.dat absent");
			}
			catch(java.io.IOException e)
			{
				System.out.println("Erreur ecriture alarme evenements.dat absent");
			}
			heureNeon1_Marche=8;
			minuteNeon1_Marche=0;
			heureNeon2_Marche=8;
			minuteNeon2_Marche=30;
			heureNeon1_Arret=17;
			minuteNeon1_Arret=30;
			heureNeon2_Arret=18;
			minuteNeon2_Arret=0;
			heureNourriture1_Marche=9;
			minuteNourriture1_Marche=0;
			heureNourriture2_Marche=13;
			minuteNourriture2_Marche=0;
			heureNourriture3_Marche=17;
			minuteNourriture3_Marche=0;
			heureFiltration1_Marche=9;
			minuteFiltration1_Marche=30;
			heureFiltration2_Marche=13;
			minuteFiltration2_Marche=30;
			heureFiltration3_Marche=17;
			minuteFiltration3_Marche=30;
			jourEngrais_Marche=1;
			heureEngrais_Marche=12;
			minuteEngrais_Marche=0;
			System.out.println("Chargement des événements par défaut terminés.");
			System.out.println("Création du fichier evenements.dat ...");
			try
			{
				FileWriter fw = new FileWriter(nomfichierEvenements, true);
				BufferedWriter bw = new BufferedWriter(fw);
				fichierCreationEvenements = new PrintWriter(bw);
			}
			catch(java.io.IOException e)
			{
				System.out.println("Erreur création fichier evenements.dat " + e.toString());
			}
			fichierCreationEvenements.println("heureNeon1_Marche=8");
			fichierCreationEvenements.println("minuteNeon1_Marche=0");
			fichierCreationEvenements.println("heureNeon2_Marche=8");
			fichierCreationEvenements.println("minuteNeon2_Marche=30");
			fichierCreationEvenements.println("heureNeon1_Arret=17");
			fichierCreationEvenements.println("minuteNeon1_Arret=30");
			fichierCreationEvenements.println("heureNeon2_Arret=18");
			fichierCreationEvenements.println("minuteNeon2_Arret=0");
			fichierCreationEvenements.println("heureNourriture1_Marche=9");
			fichierCreationEvenements.println("minuteNourriture1_Marche=0");
			fichierCreationEvenements.println("heureNourriture2_Marche=13");
			fichierCreationEvenements.println("minuteNourriture2_Marche=0");
			fichierCreationEvenements.println("heureNourriture3_Marche=17");
			fichierCreationEvenements.println("minuteNourriture3_Marche=0");
			fichierCreationEvenements.println("heureFiltration1_Marche=9");
			fichierCreationEvenements.println("minuteFiltration1_Marche=30");
			fichierCreationEvenements.println("heureFiltration2_Marche=13");
			fichierCreationEvenements.println("minuteFiltration2_Marche=30");
			fichierCreationEvenements.println("heureFiltration3_Marche=17");
			fichierCreationEvenements.println("minuteFiltration3_Marche=30");
			fichierCreationEvenements.println("jourEngrais_Marche=1");
			fichierCreationEvenements.println("heureEngrais_Marche=12");
			fichierCreationEvenements.println("minuteEngrais_Marche=0");
			if(fichierEvenements.exists())
			{
				System.out.println("Création du fichier evenements.dat terminé avec succés.");
				try
				{
					ecrireAlarme("Fichier evenements.dat recréé");
				}
				catch(java.io.IOException e)
				{
					System.out.println("Erreur ecriture alarme evenements.dat recrée");
				}
			}
			fichierCreationEvenements.close();
		}
	}
}
 