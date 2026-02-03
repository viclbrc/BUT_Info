// ComptePersonne
import java.util.*;

class Personne{
	private String nom, prenom, adresse;

    public static void main(String[] args) {
        Personne p = new Personne("Rioual", "Louis", "IUT Lannion");
        p.afficher();
    }

	Personne(String nom, String prenom, String adresse) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}
	
	public String toString() {
		return nom+" "+" "+prenom+" "+adresse;
	}
	
	public void afficher() {
		System.out.println("Nom : "+nom);
		System.out.println("Prénom : "+prenom);
		System.out.println("Adresse : "+adresse);
	}
	
	public String getNom() {
		return nom+" "+prenom;
	}
}

class Compte{

	private String numero;
	private Personne titulaire;
	public double solde;
	private static Scanner entree = new Scanner(System.in);

	Compte(String i, Personne ti){
		this.numero = i;
		this.titulaire = ti;
		this.solde = 0;
	}
	
	public String toString() {
		return "Numéro de compte : "+numero+" / Titulaire : "+titulaire.getNom()+" / Solde : "+solde;
	}
	
    public void afficher() {
        System.out.println(this.toString());
    }

    public void deposer() {
        float montant;
        System.out.println("Entrez le montant à déposer : ");
        montant = entree.nextFloat();
        solde += montant;
    }

    public void retirer() {
        float retrait;
        System.out.println("Entrez le montant à retirer : ");
        retrait = entree.nextFloat();
        if (solde >= retrait && retrait > 0) {
            solde -= retrait;
        } else {
            System.out.println("Retrait refusé : solde insuffisant.");
        }
    }
}

class CompteRemunere extends Compte{

    private double taux;
    public CompteRemunere(String n, Personne p, double t){
        super(n, p);
        this.taux = t;
    }

    public String toString() {
        return super.toString() + " / Taux d'intérêts : " + taux;
    }

    public void afficher(){
        System.out.println(this.toString());
    }

    public void versementInt(){
        double interets = solde * taux;
        solde += interets;
    }
}

class Entreprise{

    private String nom;
    private Personne comptable;

    public Entreprise(String nom, Personne comptable){
        this.nom = nom;
        this.comptable = comptable;
    }

    public String toString(){
        return "Entreprise : " + nom + " / Comptable : " + comptable.getNom();
    }

    public void afficher(){
        System.out.println(this.toString());
    }
}

class CompteEntreprise extends Compte{

    private Entreprise entreprise;

    public CompteEntreprise(String n, Personne p, Entreprise e){
        super(n, p);
        this.entreprise = e;
    }

    public String toString(){
        return super.toString() + " / " + entreprise.toString();
    }

    public void afficher(){
        System.out.println(this.toString());
    }
}

class TD3{
    public static void main(String[] args) {
        Personne p1 = new Personne("Rioual", "Louis", "IUT Lannion");
        Compte c1 = new Compte("FR761234567890", p1);
        c1.deposer();
        c1.afficher();
        c1.retirer();
        c1.afficher();

        Personne p2 = new Personne("Belloeil", "Eliot", "SDF");
        CompteRemunere cr1 = new CompteRemunere("FR009876543210", p2, 0.03);
        cr1.deposer();
        cr1.afficher();
        cr1.retirer();
        cr1.afficher();
        cr1.versementInt();
        cr1.afficher();

        Personne p3 = new Personne("Durand", "Chloe", "Lannion");
        Entreprise e1 = new Entreprise("TechNord", p3);
        CompteEntreprise ce1 = new CompteEntreprise("FR001122334455", p3, e1);
        ce1.deposer();
        ce1.afficher();
    }
}