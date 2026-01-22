import java.util.*;

class Domino {
    private int gauche;
    private int droite;

    public Domino(int g, int d) {
        gauche = g;
        droite = d;
    }

    int laGauche() {
        return gauche;
    }

    int laDroite() {
        return droite;
    }

    void afficher() {
        System.out.println(gauche + "|" + droite);
    }

    void inverser() {
        int tmp;
        tmp = gauche;
        gauche = droite;
        droite = tmp;
    }

    public static void main(String[] args) {
        Domino dd = new Domino(1, 2);
        dd.afficher();
        dd.inverser();
        dd.laGauche();
        dd.laDroite();
        Table tab = new Table(5);
        tab.initTable();
        tab.affTable();
    }
}

class Table {
    private Domino[] table;
    private Integer nbDominos;

    public Table(int max) {
        table = new Domino[max];
        nbDominos = 0;
    }

    void initTable() {
        Scanner entree = new Scanner(System.in);
        Domino debut;
        int g;
        int d;
        String ligne;
        g = -1;
        d = -1;
        while ((d < 0) || (d > 6)) {
            System.out.println("Donnez le chiffre de droite : ");
            ligne = entree.next();
            d = Integer.parseInt(ligne);
        }
        while ((g < 0) || (g > 6)) {
            System.out.println("Donnez le chiffre de gauche : ");
            ligne = entree.next();
            g = Integer.parseInt(ligne);
        }
        debut = new Domino(g, d);
        table[0] = debut;
        nbDominos++;
    }

    void affTable() {
        int i;
        i = 0;
        while (i < nbDominos) {
            table[i].afficher();
            i++;
        }
    }

    int recherche(int g, int d) {
        if (nbDominos == 0) {
            return 1;
        }

        int extremiteGauche = table[0].laGauche();
        if (d == extremiteGauche) {
            return -1;
        }
        if (g == extremiteGauche) {
            return -1;
        }

        int extremiteDroite = table[nbDominos - 1].laDroite();
        if (g == extremiteDroite) {
            return 1;
        }
        if (d == extremiteDroite) {
            return 1;
        }

        return 0;
    }

    void poserAgauche(int g, int d) {
        Domino nouveau = new Domino(g, d);

        if (g == table[0].laGauche()) {
            nouveau.inverser();
        }

        for (int i = nbDominos; i > 0; i--) {
            table[i] = table[i - 1];
        }

        table[0] = nouveau;
        nbDominos++;
    }

    void poserAdroite(int g, int d) {
        Domino nouveau = new Domino(g, d);

        if (d == table[nbDominos - 1].laDroite()) {
            nouveau.inverser();
        }

        table[nbDominos] = nouveau;
        nbDominos++;
    }
}

class Jeu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Table table = new Table(28);

        System.out.println("Dominos");
        table.initTable();

        System.out.println("État initial du jeu :");
        table.affTable();

        String reponse = "non";

        while (reponse.compareTo("non") != 0) {
            System.out.println("\n--- Nouveau tour ---");
            System.out.print("Valeur gauche du domino à poser : ");
            int g = sc.nextInt();
            System.out.print("Valeur droite du domino à poser : ");
            int d = sc.nextInt();

            // Recherche où poser le domino
            int position = table.recherche(g, d);

            if (position == -1) {
                System.out.println("Domino posé à gauche");
                table.poserAgauche(g, d);
            } else if (position == 1) {
                System.out.println("Domino posé à droite");
                table.poserAdroite(g, d);
            } else {
                System.out.println("IMPOSSIBLE de poser ce domino !");
            }

            table.affTable();

            sc.nextLine();
            System.out.print("Voulez-vous arrêter ? (oui/non) : ");
            reponse = sc.nextLine();
        }

        System.out.println("Fin du jeu");
        System.out.println("État final :");
        table.affTable();

        sc.close();
    }
}
