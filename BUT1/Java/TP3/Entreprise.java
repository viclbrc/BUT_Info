package BUT1.Java.TP3;
import java.util.*;

public class Entreprise {
    public static void main(String[] args) {
        
    }
}

abstract class Employe {
    protected String nom;
    protected int age;
    protected int annees_xp;
    protected int numero;
    protected static int compteur = 0;

    protected Employe(String nom, int age, int annees_xp) {
        this.nom = nom;
        this.age = age;
        this.annees_xp = annees_xp;
        compteur++;
        this.numero = compteur;
    }
}

class Ouvrier extends Employe {
    private int nb_h_o;

    public Ouvrier(String nom, int age, int annees_xp, int nb_h_o) {
        super(nom, age, annees_xp);
        this.nb_h_o = nb_h_o;
    }

    public void calculerSalaire() {
        int salaire = this.nb_h_o * 4 * (10 + (this.annees_xp / 2));
        System.out.println("Salaire de l'ouvrier " + this.nom + ": " + salaire + " euros");
    }
}

class Gerant extends Employe {
    private int nb_h_g;

    public Gerant(String nom, int age, int annees_xp, int nb_h_g) {
        super(nom, age, annees_xp);
        this.nb_h_g = nb_h_g;
    }

    public void calculerSalaire() {
        int salaire = this.nb_h_g * 5 * (20 + (this.annees_xp/2));
        System.out.println("Salaire du gérant " + this.nom + ": " + salaire + " euros");
    }
}