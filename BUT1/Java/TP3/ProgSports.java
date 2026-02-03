package BUT1.Java.TP3;
import java.util.*;

class Sport {
    private String code;
    private String libelle;

    public Sport(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return this.code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public String toString() {
        return "Sport : " + this.code + ", nom : " + this.libelle;
    }

    public void afficher() {
        System.out.println(this.toString());
    }
}

class SportCo extends Sport {
    private int nbJoueurs;

    public SportCo(String code, String libelle, int nbJoueurs) {
        super(code, libelle);
        this.nbJoueurs = nbJoueurs;
    }

    public int getNbJoueurs() {
        return this.nbJoueurs;
    }

    public String toString() {
        return super.toString() + ", nombre de joueurs : " + this.nbJoueurs;
    }
}

class LesSports {
    private ArrayList<Sport> sports;

    public LesSports() {
        sports = new ArrayList<Sport>();
    }

    public void ajouterSport(Sport s) {
        sports.add(s);
    }

    public void afficherSports() {
        for (Sport s : sports) {
            s.afficher();
        }
    }
}

class ProgSports {
    public static void main(String[] args) {
        LesSports lesSports = new LesSports();
        Sport s1 = new Sport("GO", "Golf");
        SportCo s2 = new SportCo("BB", "Basketball", 5);
        Sport s3 = new Sport("TN", "Tennis");
        SportCo s4 = new SportCo("FB", "Football", 11);
        SportCo s5 = new SportCo("HB", "Handball", 7);
        lesSports.ajouterSport(s1);
        lesSports.ajouterSport(s2);
        lesSports.ajouterSport(s3);
        lesSports.ajouterSport(s4);
        lesSports.ajouterSport(s5);
        lesSports.afficherSports();
    }
}