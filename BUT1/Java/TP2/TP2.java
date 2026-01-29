import java.lang.reflect.Array;
import java.util.*;
public class TP2 {
    
    public static void main(String[] args) {
        // Voitures
        // ArrayList<Voiture> listeVoitures = new ArrayList<Voiture>(10);
        // Scanner entree = new Scanner(System.in);
        // System.out.println("========== Voiture 1 ==========");
        // listeVoitures.add(new Voiture());
        // System.out.println("========== Voiture 2 ==========");
        // listeVoitures.add(new Voiture());
        // System.out.println("========== Voiture 3 ==========");
        // listeVoitures.add(new Voiture());
        // System.out.println("===============================");
        // listeVoitures.forEach((voiture) -> voiture.afficher());
        // listeVoitures.remove(1);
        // System.out.println("Après suppression de la 2ème voiture :");
        // listeVoitures.forEach((voiture) -> voiture.afficher());

        // Clients
        // Client cli1 = new Client("Dupont", "10 rue de Paris");
        // Client cli2 = new Client("Durand", "5 avenue de Lyon");
        // Client cli3 = new Client("Martin", "20 boulevard de Marseille");
        // System.out.println("====== Client 1 ======");
        // cli1.afficher();
        // System.out.println("====== Client 2 ======");
        // cli2.afficher();
        // System.out.println("====== Client 3 ======");
        // cli3.afficher();

        // // Dates
        // System.out.println("====== Date 1 ======");
        // Date date1 = new Date();
        // System.out.println("====== Date 2 ======");
        // Date date2 = new Date();
        // System.out.println("====== Date 1 ======");
        // date1.afficher();
        // System.out.println("====== Date 2 ======");
        // date2.afficher();
        // date1.setJour(15);
        // date1.setMois(8);
        // System.out.println("====== Date 1 modifiée ======");
        // date1.afficher();

        // Locations
        // Voiture voiture1 = new Voiture();
        // Voiture voiture2 = new Voiture();
        // Date date1 = new Date();
        // Date date2 = new Date();
        // Location loc1 = new Location(cli1, voiture1, date1);
        // Location loc2 = new Location(cli2, voiture2, date2);
        // Location loc3 = new Location(cli3, voiture1, date2);
        // System.out.println("====== Location 1 ======");
        // loc1.afficher();
        // System.out.println("====== Location 2 ======");
        // loc2.afficher();
        // System.out.println("====== Location 3 ======");
        // loc3.afficher();

        // Agence
        Agence agence = new Agence();
        agence.afficher();
        
        // Test enregistrement d'une location (Question 7)
        System.out.println("\n===== Enregistrement d'une nouvelle location =====");
        agence.enregistrerLocation();
        
        // Test enregistrement d'un retour (Question 8)
        System.out.println("\n===== Enregistrement d'un retour de location =====");
        agence.enregistrerRetour();
    }
}

class Voiture{

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

    public Voiture(String immat, String modele, int km_parcourus, float tarif_km){
        this.immat = immat;
        this.modele = modele;
        this.km_parcourus = km_parcourus;
        this.tarif_km = tarif_km;
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

    public void setImmat(String immat){
        this.immat = immat;
    }

    public void setModele(String modele){
        this.modele = modele;
    }

    public void setKmParcourus(int km_parcourus){
        this.km_parcourus = km_parcourus;
    }

    public void setTarifKm(float tarif_km){
        this.tarif_km = tarif_km;
    }

    public void afficher(){
        System.out.println("Immatriculation : " + this.immat);
        System.out.println("Modèle : " + this.modele);
        System.out.println("Kilomètres parcourus : " + this.km_parcourus);
        System.out.println("Tarif par kilomètre : " + this.tarif_km);
        System.out.println("=================================");
    }
}

class Client{

    private int no_client;
    private String nom;
    private String domicile;
    private static int compteur = 0;

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

    public Date(){
        Scanner entree = new Scanner(System.in);
        System.out.print("Entrez le jour : ");
        this.jour = entree.nextInt();
        System.out.print("Entrez le mois : ");
        this.mois = entree.nextInt();
        System.out.print("Entrez l'année : ");
        this.annee = entree.nextInt();
    }

    public Date(int jour, int mois, int annee){
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
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

    // Méthode pour comparer deux dates
    // Retourne : -1 si this < autre, 0 si this == autre, 1 si this > autre
    public int compareTo(Date autre){
        if(this.annee < autre.annee) return -1;
        if(this.annee > autre.annee) return 1;
        if(this.mois < autre.mois) return -1;
        if(this.mois > autre.mois) return 1;
        if(this.jour < autre.jour) return -1;
        if(this.jour > autre.jour) return 1;
        return 0;
    }

    // Retourne true si this >= autre
    public boolean estPosterieureOuEgale(Date autre){
        return this.compareTo(autre) >= 0;
    }
}

class Location{
    
    private int no_location;
    private Client client;
    private Voiture voiture;
    private Date date_debut;
    private Date date_fin;
    private int nb_km;
    private int distance;
    private static int compteur = 0;

    public Location(Client client, Voiture voiture, Date date_debut){
        this.no_location = genereNoLocation();
        this.client = client;
        this.voiture = voiture;
        this.date_debut = date_debut;
        this.date_fin = null;
        this.nb_km = voiture.getKmParcourus();
        this.distance = 0;
    }

    public Location(Client client, Voiture voiture, Date date_debut, Date date_fin, int km_fin){
        this.no_location = genereNoLocation();
        this.client = client;
        this.voiture = voiture;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.nb_km = voiture.getKmParcourus();
        this.voiture.setKmParcourus(km_fin);
    }

    public int getNoLocation(){
        return this.no_location;
    }

    public Client getClient(){
        return this.client;
    }

    public Voiture getVoiture(){
        return this.voiture;
    }

    public Date getDateDebut(){
        return this.date_debut;
    }

    public Date getDateFin(){
        return this.date_fin;
    }

    public void setDateFin(Date date_fin){
        this.date_fin = date_fin;
    }

    public int getNoKm(){
        return this.nb_km;
    }

    public int genereNoLocation(){
        compteur++;
        return compteur;
    }

    public void terminerLocation(Date date_fin, int km_fin){
        this.date_fin = date_fin;
        this.distance = km_fin - this.nb_km;
        this.voiture.setKmParcourus(km_fin);
    }

    public float calculerPrix(){
        return this.distance * this.voiture.getTarifKm();
    }

    public int getDistance(){
        return this.distance;
    }

    public void afficher(){
        System.out.println("Numéro de la location : " + this.no_location);
        System.out.println("Client : " + this.client.getNom() + " (N° " + this.client.getNoClient() + ")");
        System.out.println("Voiture : " + this.voiture.getImmat() + " - " + this.voiture.getModele());
        System.out.print("Date de début : ");
        this.date_debut.afficher();
        if(this.date_fin != null){
            System.out.print("Date de fin : ");
            this.date_fin.afficher();
            System.out.println("Distance parcourue : " + this.distance + " km");
            System.out.println("Prix total : " + calculerPrix() + " €");
        } else {
            System.out.println("Date de fin : En cours");
        }
        System.out.println("Kilomètres au début de la location : " + this.nb_km);
        System.out.println("===============================");
    }
}

class Agence{

    private String nom;
    ArrayList<Voiture> parcVoitures = new ArrayList<Voiture>(100);
    ArrayList<Client> listeClients = new ArrayList<Client>(100);
    ArrayList<Location> listeLocations = new ArrayList<Location>(100);

    public Agence(){
        this.nom = "Agence de Location";
        
        Voiture v1 = new Voiture("ZA-186-EZ", "Dacia Sandero", 15000, 0.25f);
        Voiture v2 = new Voiture("AB-123-CD", "Renault Clio 2", 30000, 0.30f);
        Voiture v3 = new Voiture("XY-987-ZW", "Audi A3", 20000, 0.28f);
        parcVoitures.add(v1);
        parcVoitures.add(v2);
        parcVoitures.add(v3);
        
        Client c1 = new Client("Durand", "12 rue des Fleurs");
        Client c2 = new Client("Martin", "34 avenue des Champs");
        Client c3 = new Client("Bernard", "56 boulevard Victor Hugo");
        listeClients.add(c1);
        listeClients.add(c2);
        listeClients.add(c3);
        
        Date d1 = new Date(15, 1, 2026);
        Date d2 = new Date(20, 1, 2026);
        Location l1 = new Location(c1, v1, d1);
        Location l2 = new Location(c2, v2, d2);
        listeLocations.add(l1);
        listeLocations.add(l2);   
    }

    public Agence(String nom){
        this.nom = nom;
    }

    public void afficher(){
        System.out.println("===== Agence : " + this.nom + " =====");
        System.out.println();
        System.out.println("===== Parc de voitures =====");
        parcVoitures.forEach((voiture) -> {voiture.afficher();});
        System.out.println("===== Liste des clients =====");
        listeClients.forEach((client) -> {client.afficher();});
        System.out.println("===== Locations en cours =====");
        listeLocations.forEach((location) -> {location.afficher();});
    }

    private Client rechercherClient(int noClient){
        for(Client c : listeClients){
            if(c.getNoClient() == noClient){
                return c;
            }
        }
        return null;
    }

    private Voiture rechercherVoiture(String immat){
        for(Voiture v : parcVoitures){
            if(v.getImmat().equals(immat)){
                return v;
            }
        }
        return null;
    }

    private boolean estDisponible(Voiture voiture){
        for(Location l : listeLocations){
            if(l.getVoiture() == voiture && l.getDateFin() == null){
                return false;
            }
        }
        return true;
    }

    private void afficherVoituresDisponibles(){
        System.out.println("===== Voitures disponibles =====");
        int count = 0;
        for(Voiture v : parcVoitures){
            if(estDisponible(v)){
                v.afficher();
                count++;
            }
        }
        if(count == 0){
            System.out.println("Aucune voiture disponible ! Location impossible.");
        }
    }

    private int countVoituresDisponibles(){
        int count = 0;
        for(Voiture v : parcVoitures){
            if(estDisponible(v)){
                count++;
            }
        }
        return count;
    }

    public void enregistrerLocation(){
        Scanner entree = new Scanner(System.in);
        
        Client client = null;
        while(client == null){
            System.out.print("Entrez le numéro du client : ");
            int noClient = entree.nextInt();
            entree.nextLine();
            
            client = rechercherClient(noClient);
            if(client == null){
                System.out.println("Erreur : Client non trouvé !");
                System.out.print("Voulez-vous créer un nouveau client ? (o/n) : ");
                String reponse = entree.nextLine();
                if(reponse.equals("o")){
                    System.out.print("Entrez le nom du nouveau client : ");
                    String nom = entree.nextLine();
                    System.out.print("Entrez le domicile : ");
                    String domicile = entree.nextLine();
                    client = new Client(nom, domicile);
                    listeClients.add(client);
                    System.out.println("Nouveau client créé avec succès !");
                }
            }
        }
        
        afficherVoituresDisponibles();
        
        if(countVoituresDisponibles() == 0){
            System.out.println("Location impossible : aucune voiture disponible !");
            return;
        }
        
        Voiture voiture = null;
        while(voiture == null || !estDisponible(voiture)){
            System.out.print("Entrez l'immatriculation de la voiture : ");
            String immat = entree.nextLine();
            
            voiture = rechercherVoiture(immat);
            if(voiture == null){
                System.out.println("Erreur : Voiture non trouvée !");
                afficherVoituresDisponibles();
            } else if(!estDisponible(voiture)){
                System.out.println("Erreur : Voiture non disponible !");
                afficherVoituresDisponibles();
                voiture = null;
            }
        }
        
        System.out.print("Entrez le jour de location : ");
        int jour = entree.nextInt();
        System.out.print("Entrez le mois de location : ");
        int mois = entree.nextInt();
        System.out.print("Entrez l'année de location : ");
        int annee = entree.nextInt();
        Date dateLocation = new Date(jour, mois, annee);
        
        Location location = new Location(client, voiture, dateLocation);
        listeLocations.add(location);
        
        System.out.println("\n===== Location enregistrée avec succès ! =====");
        location.afficher();
    }

    // Méthode pour trouver une location en cours pour une voiture donnée
    private Location trouverLocationEnCours(Voiture voiture){
        for(Location l : listeLocations){
            if(l.getVoiture() == voiture && l.getDateFin() == null){
                return l;
            }
        }
        return null;
    }

    // Méthode pour vérifier si une voiture existe et est en cours de location
    private boolean estEnCoursDeLocation(String immat){
        Voiture v = rechercherVoiture(immat);
        if(v == null) return false;
        return !estDisponible(v);
    }

    // Méthode pour enregistrer le retour d'une location
    public void enregistrerRetour(){
        Scanner entree = new Scanner(System.in);
        
        // Saisir et vérifier l'immatriculation
        Voiture voiture = null;
        Location location = null;
        while(voiture == null || estDisponible(voiture)){
            System.out.print("Entrez l'immatriculation de la voiture : ");
            String immat = entree.nextLine();
            
            voiture = rechercherVoiture(immat);
            if(voiture == null){
                System.out.println("Erreur : Voiture non trouvée !");
            } else if(estDisponible(voiture)){
                System.out.println("Erreur : Cette voiture n'est pas en cours de location !");
                voiture = null;
            } else {
                location = trouverLocationEnCours(voiture);
            }
        }
        
        // Afficher les caractéristiques de la location
        System.out.println("\n===== Caractéristiques de la location =====");
        location.afficher();
        
        // Saisir et valider la date de retour
        Date dateRetour = null;
        while(dateRetour == null || !dateRetour.estPosterieureOuEgale(location.getDateDebut())){
            System.out.print("Entrez le jour de retour : ");
            int jour = entree.nextInt();
            System.out.print("Entrez le mois de retour : ");
            int mois = entree.nextInt();
            System.out.print("Entrez l'année de retour : ");
            int annee = entree.nextInt();
            dateRetour = new Date(jour, mois, annee);
            
            if(!dateRetour.estPosterieureOuEgale(location.getDateDebut())){
                System.out.println("Erreur : La date de retour doit être postérieure ou égale à la date de location !");
                dateRetour = null;
            }
        }
        
        // Saisir et valider le kilométrage de retour
        int kmRetour = 0;
        while(kmRetour <= location.getNoKm()){
            System.out.print("Entrez le kilométrage de retour : ");
            kmRetour = entree.nextInt();
            
            if(kmRetour <= location.getNoKm()){
                System.out.println("Erreur : Le kilométrage de retour (" + kmRetour + 
                                 " km) doit être supérieur au kilométrage de départ (" + 
                                 location.getNoKm() + " km) !");
            }
        }
        
        // Calculer la distance parcourue
        int distance = kmRetour - location.getNoKm();
        
        // Terminer la location (modifie date_fin, distance et km de la voiture)
        location.terminerLocation(dateRetour, kmRetour);
        
        // Afficher le résultat
        System.out.println("\n===== Retour enregistré avec succès ! =====");
        System.out.println("Distance parcourue : " + distance + " km");
        System.out.println("Prix à payer : " + location.calculerPrix() + " €");
        System.out.println("\n===== Détails de la location terminée =====");
        location.afficher();
    }
}