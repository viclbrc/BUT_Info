package sujet_1;
public class OubliStupide { 
    public static void main(String[] args) { 
        try {
            int[] tab = null;  
            tab = new int[5]; 
            System.out.println(tab[2]); 
        } catch (Exception e) { 
            System.out.println("Erreur de création du tableau"); 
        }
    } 
}
