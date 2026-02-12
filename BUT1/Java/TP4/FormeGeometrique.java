package BUT1.Java.TP4;

public abstract class FormeGeometrique {

    protected double posX, posY;

	public FormeGeometrique(double x, double y){
	    posX=x; 
	    posY=y; 
	}
    
    public void deplacer(double x,double y) { 
	    posX=x; 
	    posY=y; 
    } 

    public void afficherPosition() { 
	    System.out.println("position : ("+posX+","+posY+")"); 
    }
 
    public String toString(){
	    return posX+"  "+posY ;
    }

    public void afficher(){
	    System.out.println("("+posX+", "+posY+")");
    }

    // Méthodes abstraites 
    // (non implementees ici, le seront obligatoirement dans les classes filles si elles sont instanciables)
    public abstract double surface() ; 
    public abstract double perimetre() ; 
  
} 
