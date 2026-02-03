package BUT1.Java.TP3;
import java.util.*;

public class CoursesChevaux {
    public static void main(String[] args) {
        
    }
}

class Personne {
    private String nom;
    private String prenom;
    private String adresse;

    public Personne(String nom, String prenom, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public String toString() {
        return "Nom: " + this.nom + ", Prénom: " + this.prenom + ", Adresse: " + this.adresse;
    }

    public void afficher() {
        System.out.println(this.toString());
    }
}

class Entraineur extends Personne {
    public Entraineur(String nom, String prenom, String adresse) {
        super(nom, prenom, adresse);
    }
}

class Jockey extends Personne {
    public Jockey(String nom, String prenom, String adresse) {
        super(nom, prenom, adresse);
    }
}

class Cheval {
    private int numero;
    private static int compteur = 0;
    private String nom;
    private String race;
    private String sexe;

    public Cheval(String nom, String race, String sexe) {
        compteur++;
        this.numero = compteur;
        this.nom = nom;
        this.race = race;
        this.sexe = sexe;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getNom() {
        return this.nom;
    }

    public String getRace() {
        return this.race;
    }

    public String getSexe() {
        return this.sexe;
    }

    public String toString() {
        return "Cheval n°" + this.numero + ": " + this.nom + ", Race: " + this.race + ", Sexe: " + this.sexe;
    }

    public void afficher() {
        System.out.println(this.toString());
    }
}

class ChevalDeCourse extends Cheval {
    private float montantGains;
    private Entraineur entraineur;

    public ChevalDeCourse(String nom, String race, String sexe, float montantGains, Entraineur entraineur) {
        super(nom, race, sexe);
        this.montantGains = montantGains;
        this.entraineur = entraineur;
    }

    public void affiche(){
        super.afficher();
        System.out.println("Montant des gains: " + this.montantGains);
        System.out.println("Entraineur: " + this.entraineur.getNom() + " " + this.entraineur.getPrenom() + ", " + this.entraineur.getAdresse());
    }

    public void attribueJockey(Jockey j) {
        System.out.println("Le jockey " + j.getNom() + " " + j.getPrenom() + " a été attribué au cheval " + this.getNom());
    }
}