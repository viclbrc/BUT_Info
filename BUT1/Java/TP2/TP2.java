import java.util.*;
// public class TP2 {
    
//     public static void main(String[] args) {
        
//     }
// }

import java.util.Scanner;

class Voiture{

    public static void main(String[] args) {
        ArrayList<Voiture> listeVoitures = new ArrayList<Voiture>(10);
        Scanner entree = new Scanner(System.in);
        int ind;
        listeVoitures.add(new Voiture());
        listeVoitures.add(new Voiture());
        listeVoitures.add(new Voiture());
        System.out.println("===============================");
        listeVoitures.forEach().afficher();
        listeVoitures.remove(1);
        System.out.println("=================================");
        System.out.println("Après suppression de la 2ème voiture :");
        for (ind = 0; ind < listeVoitures.size(); ind++){
            listeVoitures.get(ind).afficher();
        }
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

// class Client{

//     private int no_client;
//     private String nom;
//     private String domicile;
//     private int i;

//     public static void main(String[] args) {
//         Client cli1 = new Client("Dupont", "10 rue de Paris");
//         Client cli2 = new Client("Durand", "5 avenue de Lyon");
//         cli1.afficher();
//         cli2.afficher();
//     }

//     public Client(String nom, String domicile){
//         this.no_client = genere_no_client();
//         this.nom = nom;
//         this.domicile = domicile;
//     }

//     public int genere_no_client(){
//         return this.no_client = i+1;
//     }

//     public void afficher(){
//         System.out.println("Numéro du client : " + this.no_client);
//         System.out.println("Nom du client : " + this.nom);
//         System.out.println("Domicile du client : " + this.domicile);
//     }
// }