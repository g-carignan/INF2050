

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

public class Rapport {

    public String date;
    public String heure;
    public String parc;
    public String arrondissement;
    public String description;


    public Rapport(String date,  String heure,  String parc, String arrondissement, String description) {
        this.date = date;
        this.heure = heure;
        this.parc = parc;
        this.arrondissement = arrondissement;
        this.description = description;
    }

    public static void getDate(String date)throws Exception {

        Date dateFormatee = new SimpleDateFormat("yyy-mm-dd").parse(date);

    }


    public static void main(String[] args) {

        String csvFile = "src/crimes.csv";
        String line = "";
        String cvsSplitBy = ",";
        int numLigne = 1;
        ArrayList<Rapport> rapport = new ArrayList<Rapport>();
        ArrayList<String> nbrInterventions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                if (numLigne == 1) {
                    String[] titreColonnes = line.split(cvsSplitBy);
                    System.out.println(titreColonnes[3] + ",Nombre d'interventions");
                    numLigne++;
                } else {
                    String[] intervention = line.split(cvsSplitBy);
                    rapport.add(new Rapport(intervention[0], intervention[1], intervention[2], intervention[3],intervention[4]));
                    Rapport.getDate(intervention[0]); //VALIDATION DU FORMAT DE DATE EN ISO 8601
                    nbrInterventions.add(intervention[3]);
                }

            }

            Collections.sort(nbrInterventions);
            Set<String> uniqueSet = new HashSet<String>(nbrInterventions);
            String fichierCSV = "";
            for (String i : uniqueSet) {
                fichierCSV = fichierCSV.concat(i + "," + Collections.frequency(nbrInterventions, i)+"\n");
                //System.out.println(i + "," + Collections.frequency(nbrInterventions, i));
            }
            System.out.println(fichierCSV);
            String filePath = "src/rapport.csv";
            Utf8File.saveStringIntoFile(filePath, fichierCSV);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

