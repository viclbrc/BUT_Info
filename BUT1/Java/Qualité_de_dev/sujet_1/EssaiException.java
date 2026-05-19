package sujet_1;
import java.util.Scanner;

public class EssaiException {
    public static void main(String[] args) {
        int a, b, res = 0;
        Scanner clavier = new Scanner(System.in);
        a = clavier.nextInt();
        b = clavier.nextInt();
        try {
            res = a / b;
            System.out.println("le résultat de " + a + " divisé par " + b + " est " + res);
        } catch (ArithmeticException e) {
            System.out.println("Alerte, c’est une division par zéro !!");
        }
            System.out.println("Fin du programme");
    }
}
