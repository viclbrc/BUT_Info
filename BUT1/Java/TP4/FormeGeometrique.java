package BUT1.Java.TP4;

import java.util.*;

public abstract class FormeGeometrique {

    protected double posX, posY;

    public static void main(String[] args) {
        Rectangle r1 = new Rectangle(0, 0, 2, 3);
        Rectangle r2 = new Rectangle(1, 1, 4, 5);
        Cercle c1 = new Cercle(2, 2, 3);

        r1.afficher();
        r2.afficher();
        c1.afficher();

        System.out.println("Surface de r1 : " + r1.surface());
        System.out.println("Périmètre de r1 : " + r1.perimetre());

        System.out.println("Surface de c1 : " + c1.surface());
        System.out.println("Périmètre de c1 : " + c1.perimetre());

        r1.dessiner("rouge");
        c1.dessiner("bleu");

        ListeRectangles liste = new ListeRectangles();
        liste.ajouter(r2);
        liste.ajouter(r1);
        System.out.println("Avant tri :");
        liste.afficher();
        liste.trier();
        System.out.println("Après tri :");
        liste.afficher();

        System.out.println("\n=== Recherche du plus grand rectangle ===");
        Rectangle[] rectangles = new Rectangle[4];
        rectangles[0] = new Rectangle(0, 0, 3, 4);
        rectangles[1] = new Rectangle(1, 1, 5, 2);
        rectangles[2] = new Rectangle(2, 2, 6, 3);
        rectangles[3] = new Rectangle(3, 3, 2, 5);

        Rectangle plusGrand = rectangles[0];
        for (int i = 1; i < rectangles.length; i++) {
            if (rectangles[i].surface() > plusGrand.surface()) {
                plusGrand = rectangles[i];
            }
        }

        System.out.println("Le plus grand rectangle a une surface de : " + plusGrand.surface());
        plusGrand.dessiner("jaune");
    }

	public FormeGeometrique(double x, double y){
	    posX=x; 
	    posY=y; 
	}
    
    public void deplacer(double x,double y) { 
	    posX=x; 
	    posY=y; 
    } 

    public void afficherPosition() { 
	    System.out.println("position : ("+posX+", "+posY+")"); 
    }
 
    public String toString(){
	    return posX+"  "+posY;
    }

    public void afficher(){
	    System.out.println("("+posX+", "+posY+")");
    }

    // Méthodes abstraites 
    // (non implementees ici, le seront obligatoirement dans les classes filles si elles sont instanciables)
    public abstract double surface() ; 
    public abstract double perimetre() ; 
} 

class Rectangle extends FormeGeometrique implements Dessinable, Comparable {
    private float largeur;
    private float hauteur;

    public Rectangle(double x, double y, float l, float h) {
        super(x, y);
        largeur = l;
        hauteur = h;
    }

    public double surface() {
        return largeur * hauteur;
    }

    public double perimetre() {
        return 2 * (largeur + hauteur);
    }

    public void dessiner(String couleur){
        System.out.println("Dessin d'un " + this.getClass() + " de couleur " + couleur);
    }

    public int compareTo(Object autre) {
        Rectangle rectangleAutre = (Rectangle) autre;
        if (this.surface() > rectangleAutre.surface()) {
            return 1;
        } else if (this.surface() == rectangleAutre.surface()) {
            return 0;
        }
        return -1;
    }
}

class Cercle extends FormeGeometrique implements Dessinable {
    private float rayon;

    public Cercle(double x, double y, float r) {
        super(x, y);
        rayon = r;
    }

    public double surface() {
        return Math.PI * rayon * rayon;
    }

    public double perimetre() {
        return 2 * Math.PI * rayon;
    }

    public void dessiner(String couleur){
        System.out.println("Dessin d'un " + this.getClass() + " de couleur " + couleur);
    }
}

interface Dessinable {
    abstract public void dessiner(String couleur);
}

class ListeRectangles {
    private ArrayList<Rectangle> listeR;

    public ListeRectangles() {
        listeR = new ArrayList<Rectangle>();
    }

    public ListeRectangles(int taille) {
        listeR = new ArrayList<Rectangle>(taille);
    }

    public void ajouter(Rectangle r) {
        listeR.add(r);
    }

    public void afficher() {
        for (Rectangle r : listeR) {
            r.afficher();
        }
    }

    public void trier() {
        Rectangle[] tableau = new Rectangle[listeR.size()];
        for (int i = 0; i < listeR.size(); i++) {
            tableau[i] = listeR.get(i);
        }
        Arrays.sort(tableau);
        for (int i = 0; i < tableau.length; i++) {
            listeR.set(i, tableau[i]);
        }
    }

    public Rectangle obtenirIemeRectangle(int i) {
        return listeR.get(i);
    }

    public int obtenirNombreRectangles() {
        return listeR.size();
    }
}