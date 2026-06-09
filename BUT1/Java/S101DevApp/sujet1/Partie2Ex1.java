import java.util.Collection;

public class Partie2Ex1 {
    public static void main(String[] args) {
    }
}

class Commercial {
    private String identif;
    private String specialite;
    private Collection<ZoneGeographique> zones;

    public Commercial(String identif, String specialite, Collection<ZoneGeographique> zones) {
        this.identif = identif;
        this.specialite = specialite;
        this.zones = zones;
    }

    public String getIdentif() {
        return identif;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setIdentif(String identif) {
        this.identif = identif;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Collection<ZoneGeographique> getZones() {
        return zones;
    }

    public void setZones(Collection<ZoneGeographique> zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "Commercial [identif=" + identif + ", specialite=" + specialite + ", zones=" + zones + "]";
    }

    public void afficher() {
        System.out.println("Commercial [identif=" + identif + ", specialite=" + specialite + "]");
    }

    public void ajouterZone(ZoneGeographique zone) {
        if (zone == null) {
            System.out.println("La zone ne peut pas être vide.");
        } else if (zones.contains(zone)) {
            System.out.println("La zone est déjà dans la liste.");
        } else {
            addZone(zone);
        }
    }

    private void addZone(ZoneGeographique zone) {
        zones.add(zone);
    }

    public void supprimerZone(ZoneGeographique zone) {
        if (zone == null) {
            System.out.println("La zone ne peut pas être vide.");
        } else if (!zones.contains(zone)){
            System.out.println("La zone n'est pas dans la liste.");
        } else if (!(zones.size() > 1)) {
            System.out.println("Le commercial doit avoir au moins une zone.");
        } else {
            delZone(zone);
        }
    }

    private void delZone(ZoneGeographique zone) {
        zones.remove(zone);
    }
}

class ZoneGeographique {
    private String nom;
    private Collection<Client> clients;

    public ZoneGeographique(String nom, Collection<Client> clients) {
        this.nom = nom;
        this.clients = clients;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "ZoneGeographique [nom=" + nom + ", clients=" + clients + "]";
    }

    public void afficher() {
        System.out.println("ZoneGeographique [nom=" + nom + "]");
    }

    public void ajouterClient(Client client) {
        if (client == null) {
            System.out.println("Le client ne peut pas être vide.");
        } else if (clients.contains(client)) {
            System.out.println("Le client est déjà dans la liste.");
        } else {
            addClient(client);
        }
    }

    private void addClient(Client client) {
        clients.add(client);
    }

    public void supprimerClient(Client client) {
        if (client == null) {
            System.out.println("Le client ne peut pas être vide.");
        } else if (!clients.contains(client)) {
            System.out.println("Le client n'est pas dans la liste.");
        } else {
            delClient(client);
        }
    }

    private void delClient(Client client) {
        clients.remove(client);
    }
}

class Client {
    private int numero;
    private String nom;
    private String adresse;

    public Client(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    public Client(int numero, String nom, String adresse) {
        this.numero = numero;
        this.nom = nom;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Client [numero=" + numero + ", nom=" + nom + ", adresse=" + adresse + "]";
    }

    public void afficher() {
        System.out.println("Client [numero=" + numero + ", nom=" + nom + ", adresse=" + adresse + "]");
    }
}