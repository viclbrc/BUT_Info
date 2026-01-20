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
    }
}
