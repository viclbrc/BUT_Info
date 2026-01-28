import java.util.*;
public class TP2 {
    
    public static void main(String[] args) {
        
    }
}

class Voiture{

    public static void main(String[] args) {
        ArrayList<Voiture> listeVoitures = new ArrayList<Voiture>(10);
        Scanner entree = new Scanner(System.in);
        int ind;
        listeVoitures.add(new Voiture());
        listeVoitures.add(new Voiture());
        listeVoitures.add(new Voiture());
        System.out.println("===============================");
        listeVoitures.forEach((voiture) -> voiture.afficher());
        listeVoitures.remove(1);
        System.out.println("=================================");
        System.out.println("Après suppression de la 2ème voiture :");
        listeVoitures.forEach((voiture) -> voiture.afficher());
    }

    private String immat;
    private String modele;
    private int km_parcourus;
    private float tarif_km;

    public Voiture(){
        Scanner entree = new Scanner(System.in);
        System.out.print("Entrez l'immatriculation : ");
        this.immat = entree.nextLine();
        System.out.print("Entrez le modèle : ");
        this.modele = entree.nextLine();
        System.out.print("Entrez les kilomètres parcourus : ");
        this.km_parcourus = entree.nextInt();
        System.out.print("Entrez le tarif par kilomètre : ");
        this.tarif_km = entree.nextFloat();
    }

    public String getImmat(){
        return this.immat;
    }

    public String getModele(){
        return this.modele;
    }

    public int getKmParcourus(){
        return this.km_parcourus;
    }

    public float getTarifKm(){
        return this.tarif_km;
    }

    public void afficher(){
        System.out.println("Immatriculation : " + this.immat);
        System.out.println("Modèle : " + this.modele);
        System.out.println("Kilomètres parcourus : " + this.km_parcourus);
        System.out.println("Tarif par kilomètre : " + this.tarif_km);
    }
}

class Client{

    private int no_client;
    private String nom;
    private String domicile;
    private static int compteur = 0;  // Compteur statique pour générer automatiquement les numéros

    public static void main(String[] args) {
        Client cli1 = new Client("Dupont", "10 rue de Paris");
        Client cli2 = new Client("Durand", "5 avenue de Lyon");
        cli1.afficher();
        cli2.afficher();
    }

    public Client(String nom, String domicile){
        this.no_client = genereNoClient();
        this.nom = nom;
        this.domicile = domicile;
    }

    private int genereNoClient(){
        compteur++;
        return compteur;
    }

    public int getNoClient(){
        return this.no_client;
    }

    public String getNom(){
        return this.nom;
    }

    public String getDomicile(){
        return this.domicile;
    }

    public void afficher(){
        System.out.println("===============================");
        System.out.println("Numéro du client : " + this.no_client);
        System.out.println("Nom du client : " + this.nom);
        System.out.println("Domicile du client : " + this.domicile);
        System.out.println("===============================");
    }
}

class Date{

    private int jour;
    private int mois;
    private int annee;

    public static void main(String[] args) {
        System.out.println("Création de la première date :");
        Date date1 = new Date();
        System.out.println("\nCréation de la deuxième date :");
        Date date2 = new Date();
        
        System.out.println("\nAffichage des dates créées :");
        System.out.print("Date 1 : ");
        date1.afficher();
        System.out.print("Date 2 : ");
        date2.afficher();
        
        System.out.println("\nModification de la date 1...");
        date1.setJour(15);
        date1.setMois(8);
        date1.setAnnee(2025);
        System.out.print("Date 1 modifiée : ");
        date1.afficher();
    }

    public Date(){
        Scanner entree = new Scanner(System.in);
        System.out.print("Entrez le jour : ");
        this.jour = entree.nextInt();
        System.out.print("Entrez le mois : ");
        this.mois = entree.nextInt();
        System.out.print("Entrez l'année : ");
        this.annee = entree.nextInt();
    }

    public int getJour(){
        return this.jour;
    }

    public int getMois(){
        return this.mois;
    }

    public int getAnnee(){
        return this.annee;
    }

    public void setJour(int jour){
        this.jour = jour;
    }

    public void setMois(int mois){
        this.mois = mois;
    }

    public void setAnnee(int annee){
        this.annee = annee;
    }

    public void afficher(){
        System.out.println(this.jour + "/" + this.mois + "/" + this.annee);
    }
}