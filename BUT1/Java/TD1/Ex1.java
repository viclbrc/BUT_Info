import java.util.*;

public class Ex1{
    public static void main(String[] args) {
        // String st;
        // Scanner entree = new Scanner(System.in);
        // System.out.println("Donnez une chaîne : ");
        // st = entree.next();
        // StringBuffer chaine = new StringBuffer(st);
        // chaine.setCharAt(0, 'A');
        // chaine.setCharAt(chaine.length() - 1, 'B');
        // System.out.println("La nouvelle chaîne est : " + chaine.toString());
        // entree.close();

        Integer i,lg;
        Scanner input = new Scanner(System.in);
        String ligne;
        System.out.println("Donnez une chaîne : ");
        ligne = input.next();
        StringBuffer hf = new StringBuffer(ligne);
        input.close();
        lg = hf.length();
        for(i=0; i<lg; i++){
            Character c;
            Character nouveau;
            int pos, nouveauCode;
            c = hf.charAt(i);
            pos = (int) c - (int) 'a';
            pos = pos + 2;
            pos = pos % 26;
            nouveauCode = pos + (int) 'a';
            nouveau = (char) nouveauCode;
            hf.setCharAt(i, nouveau);
        }
        ligne = hf.toString();
        System.out.println("La nouvelle chaîne est : " + ligne);
        input.close();
    }
}