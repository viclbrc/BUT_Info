package BUT1.Java.TP3;
import java.util.*;

public class Exercice2 {
    public static void main(String[] args) {
        Voilier v = new Voilier("V1", 1000, "Port1", 20, 50);
        BateauAMoteur b = new BateauAMoteur("B1", 2000, "Port2", 30, 150);
        MotorYacht m = new MotorYacht("M1", 3000, "Port3", 40, 250, 8);

        ListeBateaux liste = new ListeBateaux();
        liste.ajouterBateau(v);
        liste.ajouterBateau(b);
        liste.ajouterBateau(m);
        System.out.println("\nAffichage de tous les bateaux :");
        liste.afficherBateaux();
        System.out.println("Affichage de la longueur du bateau V1 :");
        liste.afficherLongueur(v);
        System.out.println("");
        System.out.println("Affichage des bateaux du Port1 :");
        liste.afficherBateauxParPort("Port1");
    }
}

abstract class Bateau {
    private String nom;
    private float poids;
    private String port;
    private float longueur;

    public Bateau(String nom, float poids, String port, float longueur) {
        this.nom = nom;
        this.poids = poids;
        this.port = port;
        this.longueur = longueur;
    }

    public abstract void afficher();

    public abstract float calculerTaxe();

    public String getNom() {
        return nom;
    }

    public float getPoids() {
        return poids;
    }

    public String getPort() {
        return port;
    }

    public float getLongueur() {
        return longueur;
    }
}

class Voilier extends Bateau {
    private float surface_voilure;

    public Voilier(String nom, float poids, String port, float longueur, float surface_voilure) {
        super(nom, poids, port, longueur);
        this.surface_voilure = surface_voilure;
    }

    public void afficher() {
        System.out.println("Voilier :");
        System.out.println("Nom : " + getNom());
        System.out.println("Poids : " + getPoids());
        System.out.println("Port : " + getPort());
        System.out.println("Longueur : " + getLongueur());
        System.out.println("Surface de voilure : " + surface_voilure);
    }

    public float calculerTaxe() {
        return getLongueur() * 50;
    }

}

class BateauAMoteur extends Bateau {
    private float puissance_moteur;

    public BateauAMoteur(String nom, float poids, String port, float longueur, float puissance_moteur) {
        super(nom, poids, port, longueur);
        this.puissance_moteur = puissance_moteur;
    }

    public void afficher() {
        System.out.println("Bateau à moteur :");
        System.out.println("Nom : " + getNom());
        System.out.println("Poids : " + getPoids());
        System.out.println("Port : " + getPort());
        System.out.println("Longueur : " + getLongueur());
        System.out.println("Puissance du moteur : " + puissance_moteur);
    }

    public float calculerTaxe() {
        return getLongueur() * 100 + puissance_moteur * 5;
    }
}

class MotorYacht extends BateauAMoteur {
    private int nb_equipage;

    public MotorYacht(String nom, float poids, String port, float longueur, float puissance_moteur, int nb_equipage) {
        super(nom, poids, port, longueur, puissance_moteur);
        this.nb_equipage = nb_equipage;
    }

    public void afficher() {
        System.out.println("Motor-Yacht :");
        System.out.println("Nom : " + getNom());
        System.out.println("Poids : " + getPoids());
        System.out.println("Port : " + getPort());
        System.out.println("Longueur : " + getLongueur());
        System.out.println("Nombre d'équipage : " + nb_equipage);
    }

    public float calculerTaxe() {
        return super.calculerTaxe() + nb_equipage * 20;
    }
}

class ListeBateaux {
    private ArrayList<Bateau> bateaux;

    public ListeBateaux() {
        bateaux = new ArrayList<>();
    }

    public void ajouterBateau(Bateau bateau) {
        bateaux.add(bateau);
    }

    public void afficherBateaux() {
        for (Bateau bateau : bateaux) {
            bateau.afficher();
            System.out.println("Taxe: " + bateau.calculerTaxe());
            System.out.println();
        }
    }

    public void afficherLongueur(Bateau bateau) {
        System.out.println("Longueur du bateau " + bateau.getNom() + ": " + bateau.getLongueur());
    }

    public void afficherBateauxParPort(String port) {
        for (Bateau bateau : bateaux) {
            if (bateau.getPort().equals(port)) {
                bateau.afficher();
                System.out.println("Taxe: " + bateau.calculerTaxe());
                System.out.println();
            }
        }
    }
}