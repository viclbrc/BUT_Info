import java.util.Scanner;

public class Bonjour {
    public static void main(String[] args) {
        System.out.println("Bonjour");
        String st;
        Scanner entree = new Scanner(System.in);
        System.out.println("Donnez une chaîne : ");
        st = entree.next();
        System.out.println("Vous avez donné la chaîne : " + st + ".");
        
    }
}