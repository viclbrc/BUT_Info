package BUT1.Java.TP3;
import java.util.*;

public class Exercice1 {
    public static void main(String[] args) {
        Ouvrier ouvrier1 = new Ouvrier("Dupont", 30, 5, 40);
        Ouvrier ouvrier2 = new Ouvrier("Durand", 25, 3, 35);
        Ouvrier ouvrier3 = new Ouvrier("Dupond", 45, 20, 50);
        Gerant gerant1 = new Gerant("Durant", 50, 25, 60);
        ListeEmployes liste = new ListeEmployes();
        liste.ajouterEmploye(ouvrier1);
        liste.ajouterEmploye(ouvrier2);
        liste.ajouterEmploye(ouvrier3);
        liste.ajouterEmploye(gerant1);

        System.out.println("");
        System.out.println("====================================");
        System.out.println("");
        ouvrier1.afficher();
        ouvrier2.afficher();
        ouvrier3.afficher();
        gerant1.afficher();
        System.out.println("");
        System.out.println("====================================");
        System.out.println("");
        liste.trierParSalaire();
        System.out.println("");
        System.out.println("====================================");
        System.out.println("");
        ListeEmployes selection = liste.selectionnerParAge(20, 30);
        System.out.println("Employés âgés entre 20 et 30 ans : ");
        for (Employe e : selection.employes) {
            e.afficher();
            System.out.println();
        }
    }
}

abstract class Employe {
    private String nom;
    private int age;
    private int annees_xp;
    private int numero;
    private static int compteur = 0;

    public Employe(String nom, int age, int annees_xp) {
        this.nom = nom;
        this.age = age;
        this.annees_xp = annees_xp;
        compteur++;
        this.numero = compteur;
    }

    public String getNom() {
        return this.nom;
    }

    public int getAge() {
        return this.age;
    }

    public int getAnneesXp() {
        return this.annees_xp;
    }

    public int getNumero() {
        return this.numero;
    }

    public abstract int salaire();

    public void calculerSalaire() {
        System.out.println("Salaire de " + this.nom + ": " + salaire() + " euros");
    }

    public void afficher() {
        System.out.println("Employé " + this.numero + ": " + this.nom + ", " + this.age + " ans, " + this.annees_xp + " années d'expérience");
    }
}

class Ouvrier extends Employe {
    private int nb_h_o;

    public Ouvrier(String nom, int age, int annees_xp, int nb_h_o) {
        super(nom, age, annees_xp);
        this.nb_h_o = nb_h_o;
    }

    public int salaire() {
        return this.nb_h_o * 4 * (10 + (this.getAnneesXp() / 2));
    }

    public void afficher() {
        System.out.println("Ouvrier " + this.getNumero() + ":");
        super.afficher();
        System.out.println("Nombre d'heures travaillées: " + this.nb_h_o);
    }
}

class Gerant extends Employe {
    private int nb_h_g;

    public Gerant(String nom, int age, int annees_xp, int nb_h_g) {
        super(nom, age, annees_xp);
        this.nb_h_g = nb_h_g;
    }

    public int salaire() {
        return this.nb_h_g * 5 * (20 + (this.getAnneesXp() / 2));
    }

    public void afficher() {
        System.out.println("Gerant " + this.getNumero() + ":");
        super.afficher();
        System.out.println("Nombre d'heures travaillées: " + this.nb_h_g);
    }
}

class ListeEmployes {
    public ArrayList<Employe> employes;

    public ListeEmployes() {
        this.employes = new ArrayList<Employe>();
    }

    public void ajouterEmploye(Employe e) {
        this.employes.add(e);
    }

    public void retirerEmploye(Employe e) {
        this.employes.remove(e);
    }

    public void trierParSalaire() {
        for (int i = 0; i < this.employes.size(); i++) {
            for (int j = 0; j < this.employes.size() - 1 - i; j++) {
                Employe e1 = this.employes.get(j);
                Employe e2 = this.employes.get(j + 1);
                if (e1.salaire() > e2.salaire()) {
                    this.employes.set(j, e2);
                    this.employes.set(j + 1, e1);
                }
            }
        }

        System.out.println("Employés triés par salaire croissant : ");
        for (Employe e : this.employes) {
            e.afficher();
            e.calculerSalaire();
            System.out.println();
        }
    }

    public ListeEmployes selectionnerParAge(int min, int max) {
        ListeEmployes selection = new ListeEmployes();
        for (Employe e : this.employes) {
            int age = e.getAge();
            if (age >= min && age <= max) {
                selection.ajouterEmploye(e);
            }
        }
        return selection;
    }
}