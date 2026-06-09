import java.util.Collection;
import java.util.Date;

public class Partie2Ex3 {
    public static void main(String[] args) {
    }
}

class Recette{
    private String description;

    public Recette(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recette [description=" + description + "]";
    }

    public void afficher() {
        System.out.println("Recette [description=" + description + "]");
    }
}

class Plat{
    public String nom;
    private Recette recette;

    public Plat(String nom, Recette recette) {
        this.nom = nom;
        this.recette = recette;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    @Override
    public String toString() {
        return "Plat [nom=" + nom + ", recette=" + recette + "]";
    }

    public void afficher() {
        System.out.println("Plat [nom=" + nom + ", recette=" + recette + "]");
    }
}

class Restaurant{
    public String nom;
    public String adresse;
    private Collection<Plat> plats;

    public Restaurant(String nom, String adresse, Collection<Plat> plats) {
        this.nom = nom;
        this.adresse = adresse;
        this.plats = plats;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Collection<Plat> getPlats() {
        return plats;
    }

    public void setPlats(Collection<Plat> plats) {
        this.plats = plats;
    }

    @Override
    public String toString() {
        return "Restaurant [nom=" + nom + ", adresse=" + adresse + ", plats=" + plats + "]";
    }

    public void afficher() {
        System.out.println("Restaurant [nom=" + nom + ", adresse=" + adresse + ", plats=" + plats + "]");
    }

    public void addPlat(Plat p) {
        plats.add(p);
    }

    public void delPlat(Plat p) {
        plats.remove(p);
    }

    public void ajouterPlat(Plat p) {
        if (p == null) {
            System.out.println("Le plat est null.");
        } else if (plats.contains(p)) {
            System.out.println("Le plat est déjà dans le restaurant.");
        } else {
            addPlat(p);
        }
    }

    public void supprimerPlat(Plat p) {
        if (p == null) {
            System.out.println("Le plat est null.");
        } else if (!plats.contains(p)) {
            System.out.println("Le plat n'est pas dans le restaurant.");
        } else if (!(plats.size() > 1)) {
            System.out.println("Le restaurant doit avoir au moins un plat.");
        } else {
            delPlat(p);
        }
    }
}

class Réservation{
    private Date dateR;
    private Collection<Restaurant> restaurant;
    private Collection<Client> clients;

    public Réservation(Date dateR, Collection<Restaurant> restaurant, Collection<Client> clients) {
        this.dateR = dateR;
        this.restaurant = restaurant;
        this.clients = clients;
    }

    public Date getDateR() {
        return dateR;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    public Collection<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Collection<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Réservation [dateR=" + dateR + ", restaurant=" + restaurant + ", clients=" + clients + "]";
    }

    public void afficher() {
        System.out.println("Réservation [dateR=" + dateR + ", restaurant=" + restaurant + ", clients=" + clients + "]");
    }

    private void addRestaurant(Restaurant r) {
        restaurant.add(r);
    }

    private void delRestaurant(Restaurant r) {
        restaurant.remove(r);
    }

    public void ajouterRestaurant(Restaurant r) {
        if (r == null) {
            System.out.println("Le restaurant est null.");
        } else if (restaurant.contains(r)) {
            System.out.println("Le restaurant est déjà dans la réservation.");
        } else {
            addRestaurant(r);
        }
    }

    public void supprimerRestaurant(Restaurant r) {
        if (r == null) {
            System.out.println("Le restaurant est null.");
        } else if (!restaurant.contains(r)) {
            System.out.println("Le restaurant n'est pas dans la réservation.");
        } else if (!(restaurant.size() > 1)) {
            System.out.println("La réservation doit avoir au moins un restaurant.");
        } else {
            delRestaurant(r);
        }
    }

    private void addClient(Client c) {
        clients.add(c);
    }

    private void delClient(Client c) {
        clients.remove(c);
    }

    public void ajouterClient(Client c) {
        if (c == null) {
            System.out.println("Le client est null.");
        } else if (clients.contains(c)) {
            System.out.println("Le client est déjà dans la réservation.");
        } else {
            addClient(c);
        }
    }

    public void supprimerClient(Client c) {
        if (c == null) {
            System.out.println("Le client est null.");
        } else if (!clients.contains(c)) {
            System.out.println("Le client n'est pas dans la réservation.");
        } else if (!(clients.size() > 1)) {
            System.out.println("La réservation doit avoir au moins un client.");
        } else {
            delClient(c);
        }
    }
}

class Client{
    private String nom;
    private Collection<Réservation> reservations;

    public Client(String nom, Collection<Réservation> reservations) {
        this.nom = nom;
        this.reservations = reservations;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Réservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Réservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Client [nom=" + nom + ", reservations=" + reservations + "]";
    }

    public void afficher() {
        System.out.println("Client [nom=" + nom + ", reservations=" + reservations + "]");
    }

    private void addReservation(Réservation r) {
        reservations.add(r);
    }

    private void delReservation(Réservation r) {
        reservations.remove(r);
    }

    public void ajouterReservation(Réservation r) {
        if (r == null) {
            System.out.println("La réservation est null.");
        } else if (reservations.contains(r)) {
            System.out.println("La réservation est déjà dans la liste du client.");
        } else {
            addReservation(r);
        }
    }

    public void supprimerReservation(Réservation r) {
        if (r == null) {
            System.out.println("La réservation est null.");
        } else if (!reservations.contains(r)) {
            System.out.println("La réservation n'est pas dans la liste du client.");
        } else if (!(reservations.size() > 1)) {
            System.out.println("Le client doit avoir au moins une réservation.");
        } else {
            delReservation(r);
        }
    }
}