import java.util.Scanner;

public class Ex1{
    public static void main(String[] args) {
        String st;
        Scanner entree = new Scanner(System.in);
        System.out.println("Donnez une chaîne : ");
        st = entree.next();
        StringBuffer chaine = new StringBuffer(st);
        chaine.setCharAt(0, 'A');
        chaine.setCharAt(chaine.length() - 1, 'B');
        System.out.println("La nouvelle chaîne est : " + chaine.toString());
    }
}