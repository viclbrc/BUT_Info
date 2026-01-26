import java.util.*;

public class TestStock {
    public static void main(String[] args) {
        Stock stock = new Stock(20);
        stock.afficheMenu();
    }
}

class Produit {
    private String reference;
    private int date_entree;

    public Produit(String reference, int date_entree) {
        this.reference = reference;
        this.date_entree = date_entree;
    }

    public String getReference() {
        return reference;
    }

    public int getDateEntree() {
        return date_entree;
    }

    public String toString() {
        return ("[" + this.reference + " | " + this.date_entree + "]");
    }

    public void afficher() {
        System.out.println(this.toString());
    }
}

class Pile {
    private Produit[] tab_prod;
    private int indice;

    public Pile(int max) {
        tab_prod = new Produit[max];
        indice = -1;
    }

    public boolean pileVide() {
        return indice == -1;
    }

    public boolean pilePleine() {
        return indice == tab_prod.length - 1;
    }

    public void empiler(Produit p) {
        if (!pilePleine()) {
            indice++;
            tab_prod[indice] = p;
        } else {
            System.out.println("Pile pleine !");
        }
    }

    public void depiler() {
        if (!pileVide()) {
            tab_prod[indice] = null;
            indice--;
        } else {
            System.out.println("Pile vide !");
        }
    }

    public Produit sommet() {
        if (!pileVide()) {
            return tab_prod[indice];
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Stock : [");
        for (int i = 0; i <= indice; i++) {
            sb.append(tab_prod[i].toString());
            if (i < indice) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void afficherStock() {
        if (pileVide()) {
            System.out.println("Stock vide");
        } else {
            System.out.println(this.toString());
        }
    }
}

class Stock {
    private Pile pile;
    private int dateJour;
    private Scanner scanner;

    public Stock(int taille) {
        pile = new Pile(taille);
        dateJour = 1;
        scanner = new Scanner(System.in);
    }

    public void entrer(Produit p) {
        if (!pile.pilePleine()) {
            pile.empiler(p);
            System.out.println("Produit " + p.getReference() + " ajouté au stock");
            dateJour++;
        } else {
            System.out.println("Stock plein !");
        }
    }

    public void sortir(int dateJ) {
        if (pile.pileVide()) {
            System.out.println("Stock vide !");
            return;
        }

        Produit premier = pile.sommet();
        
        // Retirer les produits périmés successivement
        while (!pile.pileVide()) {
            Produit p = pile.sommet();
            int anciennete = dateJ - p.getDateEntree();
            
            if (anciennete > 5) {
                // Produit périmé, le retirer
                pile.depiler();
            } else {
                // Produit frais trouvé, le retirer et terminer
                pile.depiler();
                System.out.println("Produit " + p.getReference() + " sorti du stock");
                return;
            }
        }
        
        // Si on arrive ici, tous les produits étaient périmés
        System.out.println("Produit " + premier.getReference() + " périmé, stock intégralement supprimé");
    }

    public void afficheMenu() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n--- MENU ---");
            System.out.println("e : entrée d'un produit dans le stock");
            System.out.println("s : sortie d'un produit du stock");
            System.out.println("i : incrémenter la date du jour");
            System.out.println("a : afficher le stock");
            System.out.println("q : quitter");
            System.out.print("Choisissez une option : ");

            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "e":
                    System.out.print("Référence du produit : ");
                    String reference = scanner.nextLine();
                    Produit produit = new Produit(reference, dateJour);
                    entrer(produit);
                    break;

                case "s":
                    sortir(dateJour);
                    break;

                case "i":
                    dateJour++;
                    System.out.println("Date du jour incrémentée à : " + dateJour);
                    break;

                case "a":
                    System.out.println("Date du jour : " + dateJour);
                    pile.afficherStock();
                    break;

                case "q":
                    continuer = false;
                    break;

                default:
                    System.out.println("Option invalide !");
            }
        }

        scanner.close();
    }
}