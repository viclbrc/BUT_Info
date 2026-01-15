import java.util.*;

public class Calculatrice {
    public static void main(String[] args) {
        Scanner choix = new Scanner(System.in);
        System.out.print(
                "C : calcul d'un cosinus\nS : calcul d'un sinus\nT : calcul d'une tangente\nQ : quitter le programme\nChoix : ");
        String reponse = choix.nextLine();
        while (reponse == null) {
            System.out.print(
                    "C : calcul d'un cosinus\nS : calcul d'un sinus\nT : calcul d'une tangente\nQ : quitter le programme\nChoix : ");
        }
        if (reponse.equals("C")) {
            System.out.print("Donnez un angle en radians : ");
            String rep = choix.nextLine();
            Double angle_rad = Double.parseDouble(rep);
            Double cosinus = Math.cos(angle_rad);
            System.out.println("Le cosinus de " + angle_rad + " radians est : " + cosinus);
        } else if (reponse.equals("S")) {
            System.out.print("Donnez un angle en radians : ");
            String rep = choix.nextLine();
            Double angle_sin = Double.parseDouble(rep);
            Double sinus = Math.sin(angle_sin);
            System.out.println("Le sinus de " + angle_sin + " radians est : " + sinus);
        } else if (reponse.equals("T")) {
            System.out.print("Donnez un angle en radians : ");
            String rep = choix.nextLine();
            Double angle_tan = Double.parseDouble(rep);
            Double tangente = Math.tan(angle_tan);
            System.out.println("La tangente de " + angle_tan + " radians est : " + tangente);
        } else if (reponse.equals("Q")) {
            System.out.println("Fin du programme.");
        } else {
            System.out.println("Choix invalide.");
        }
        choix.close();
    }
}