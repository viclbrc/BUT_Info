import java.util.InputMismatchException;
import java.util.Scanner;

public class Ex3 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            saisieCorrecte(sc);
            saisieCorrecte2(sc);
        }
    }

    public static void saisieCorrecte(Scanner sc) {
        while (true) {
            try {
                System.out.print("Saisie 1 (>10) : ");
                int nb = sc.nextInt();
                if (nb <= 10) throw new IllegalArgumentException();
                System.out.println("OK : " + nb);
                return;
            } catch (InputMismatchException e) {
                System.out.println("Entier attendu, recommencez.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Doit être > 10, recommencez.");
            }
        }
    }

    public static void saisieCorrecte2(Scanner sc) {
        while (true) {
            try {
                System.out.print("Saisie 2 (>10) : ");
                int nb = sc.nextInt();
                if (nb <= 10) throw new NombreTropPetitException("Le nombre doit être supérieur à 10.");
                System.out.println("OK : " + nb);
                return;
            } catch (InputMismatchException e) {
                System.out.println("Entier attendu, recommencez.");
                sc.nextLine();
            } catch (NombreTropPetitException e) {
                sc.nextLine();
            }
        }
    }

    static class NombreTropPetitException extends Exception {
        public NombreTropPetitException(String message) {
            super(message);
            System.out.println("Erreur: " + getMessage());
        }
    }
}
