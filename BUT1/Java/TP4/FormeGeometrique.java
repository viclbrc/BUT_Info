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
        if (this.surface() < ((FormeGeometrique) autre).surface()) {
            return -1;
        } else if (this.surface() > ((FormeGeometrique) autre).surface()) {
            return 1;
        } else {
            return 0;
        }
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

interface Comparable{
    abstract public int compareTo(Object autre);
}

class ListeRectangles {
    private ArrayList<Rectangle> listeR;

    public ListeRectangles() {
        listeR = new ArrayList<Rectangle>();
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
        ArrayList<Rectangle> listetriee = new ArrayList<Rectangle>();
        for (Rectangle r : listeR) {
            listetriee.add(r);
        }
        listetriee.sort(null);
        listeR = listetriee;
    }

    public Rectangle obtenirIemeRectangle(int i) {
        return listeR.get(i);
    }

    public int obtenirNombreRectangles() {
        return listeR.size();
    }
}