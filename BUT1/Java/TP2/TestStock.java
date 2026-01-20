import java.util.*;

public class TestStock {
    public static void main(String[] args) {
        Produit prod = new Produit(5);
        prod.afficher();
        Pile pile = new Pile(10);
        pile.pileVide();
        pile.pilePleine();
        pile.empiler(prod);
        pile.depiler();
        pile.sommet();
        pile.afficherStock();
    }
}

class Produit {
    private String reference;
    private Integer date_entree;
    private Scanner ref = new Scanner(System.in);

    public Produit(int date_entree) {
        this.date_entree = date_entree;
        System.out.println("Référence du produit : ");
        reference = this.ref.nextLine();
        ref.close();
    }

    public String toString() {
        return ("[" + this.reference + " | " + this.date_entree + "]");
    }

    public void afficher() {
        System.out.println(this.toString());
    }
}

class Pile {
    private ArrayList tab_prod;
    private static int indice;

    public Pile(int max) {
        tab_prod = new ArrayList(max);
        indice = max;
    }

    public boolean pileVide() {
        tab_prod.isEmpty();
    }

    public boolean pilePleine() {
        return this.tab_prod.size() == indice;
    }

    public void empiler(Produit p) {
        tab_prod.add(indice, p);
    }

    public void depiler() {
        tab_prod.remove(indice);
    }

    public Produit sommet() {
        return tab_prod.get(indice);
    }

    public String toString() {
        return ("Stock : " + this.tab_prod);
    }

    public void afficherStock() {
        System.out.println(this.toString());
    }
}