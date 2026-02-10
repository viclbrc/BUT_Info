package BUT1.Java.TP3;
import java.util.*;

public class CoursesChevaux {
    public static void main(String[] args) {
        Entraineur entraineur1 = new Entraineur("Dupont", "Jean", "1 rue A");
        Entraineur entraineur2 = new Entraineur("Dupond", "Philippe", "2 rue B");
        Entraineur entraineur3 = new Entraineur("Dupon", "Jean-Philippe", "3 rue C");

        ChevalDeCourse cheval1 = new ChevalDeCourse("Cheval 1", "Race 1", "M", 1200.0f, entraineur1);
        ChevalDeCourse cheval2 = new ChevalDeCourse("Cheval 2", "Race 2", "M", 900.0f, entraineur2);
        ChevalDeCourse cheval3 = new ChevalDeCourse("Cheval 3", "Race 3", "F", 1500.0f, entraineur3);

        Course course = new Course("Grand Prix", 5000.0f);
        course.enregistre(cheval1);
        course.enregistre(cheval2);
        course.enregistre(cheval3);
        course.enregistre(new ChevalDeCourse("Cheval 4", "Race 4", "F", 800.0f, entraineur3));

        System.out.println("=== Test chevalPresent ===");
        String absent = "Cheval pas là";
        System.out.println("Nom: " + absent + " | Attendu: false | Obtenu: " + course.chevalPresent(absent));
        System.out.println("Nom: Cheval 1 | Attendu: true | Obtenu: " + course.chevalPresent("Cheval 1"));
        System.out.println("Nom: Cheval 2 | Attendu: true | Obtenu: " + course.chevalPresent("Cheval 2"));
        System.out.println("Nom: Cheval 3 | Attendu: true | Obtenu: " + course.chevalPresent("Cheval 3"));

        System.out.println("\n=== Test rechercheCheval ===");
        System.out.println("Nom: " + absent + " | Attendu: absent | Obtenu:");
        course.rechercheCheval(absent);
        System.out.println("Nom: Cheval 1");
        course.rechercheCheval("Cheval 1");
        System.out.println("Nom: Cheval 2");
        course.rechercheCheval("Cheval 2");
        System.out.println("Nom: Cheval 3");
        course.rechercheCheval("Cheval 3");

        System.out.println("=== Test meilleureCote ===");
        ChevalDeCourse meilleur = course.meilleureCote();
        System.out.println("Attendu: Cheval 3 | Obtenu: " + (meilleur == null ? "null" : meilleur.getNom()));

        System.out.println("=== Affichage course ===");
        course.affiche();

        System.out.println("=== Affichage Jockeys et Entraineurs ===");
        Jockey jockey1 = new Jockey("Durant", "Jacques", "4 rue D", 70.0f, 3000.0f);
        Jockey jockey2 = new Jockey("Durand", "Jean", "5 rue E", 68.0f, 3200.0f);
        cheval1.attribueJockey(jockey1);
        cheval2.attribueJockey(jockey2);

        entraineur1.affiche();
        entraineur2.affiche();
        entraineur3.affiche();
        jockey1.affiche();
        jockey2.affiche();
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
    private int numero;
    private static int compteur = 0;

    public Entraineur(String nom, String prenom, String adresse) {
        super(nom, prenom, adresse);
        compteur++;
        this.numero = compteur;
    }

    public void affiche() {
        super.afficher();
        System.out.println("Numéro d'entraîneur: " + this.numero);
    }
}

class Jockey extends Personne {
    private float poids;
    private float salaire;

    public Jockey(String nom, String prenom, String adresse, float poids, float salaire) {
        super(nom, prenom, adresse);
        this.poids = poids;
        this.salaire = salaire;
    }

    public void affiche() {
        super.afficher();
        System.out.println("Poids: " + this.poids + " kg | Salaire: " + this.salaire + " euros");
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

    public float getMontantGains() {
        return this.montantGains;
    }

    public void attribueJockey(Jockey j) {
        System.out.println("Le jockey " + j.getNom() + " " + j.getPrenom() + " a été attribué au cheval " + this.getNom());
    }
}

class Course {
    private String nomEpreuve;
    private float dotation;
    private ArrayList<ChevalDeCourse> chevaux;

    public Course(String nomEpreuve, float dotation) {
        this.nomEpreuve = nomEpreuve;
        this.dotation = dotation;
        this.chevaux = new ArrayList<ChevalDeCourse>();
    }

    public void affiche() {
        System.out.println("Course: " + this.nomEpreuve + " | Dotation: " + this.dotation);
        for (ChevalDeCourse cheval : this.chevaux) {
            System.out.println("- " + cheval.getNom() + " | Gains: " + cheval.getMontantGains());
        }
    }

    public boolean chevalPresent(String nom) {
        for (ChevalDeCourse cheval : this.chevaux) {
            if (cheval.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public void enregistre(ChevalDeCourse c) {
        if (!chevalPresent(c.getNom())) {
            this.chevaux.add(c);
        }
    }

    public void rechercheCheval(String nom) {
        for (ChevalDeCourse cheval : this.chevaux) {
            if (cheval.getNom().equals(nom)) {
                cheval.affiche();
                return;
            }
        }
        System.out.println("absent");
    }

    public ChevalDeCourse meilleureCote() {
        if (this.chevaux.isEmpty()) {
            return null;
        }

        ChevalDeCourse meilleur = this.chevaux.get(0);
        for (int i = 1; i < this.chevaux.size(); i++) {
            ChevalDeCourse courant = this.chevaux.get(i);
            if (courant.getMontantGains() > meilleur.getMontantGains()) {
                meilleur = courant;
            }
        }
        return meilleur;
    }
}