import java.util.*;

public class TestChaines {
    public static void main(String[] args) {
        // Integer nombre = 12345;
        // String nb = String.valueOf(nombre);
        // System.out.println(nb);
        // String nb_demande;
        // Scanner entree = new Scanner(System.in);
        // System.out.println("Donnez une chaîne de caractères uniquement composée de chiffres (int) : ");
        // nb_demande = entree.nextLine();
        // Integer nb_int = Integer.parseInt(nb_demande);
        // System.out.println(nb_int);
        // nb_int += 1;
        // System.out.println(nb_int);
        // entree.close();

        // String float_demande;
        // Scanner input = new Scanner(System.in);
        // System.out.println("Donnez une chaîne de caractères uniquement composée de chiffres (float) : ");
        // float_demande = input.nextLine();
        // Float nb_float = Float.parseFloat(float_demande);
        // System.out.println(nb_float);
        // nb_float = nb_float + 1.1f;
        // System.out.println(nb_float);
        // input.close();

        // String ville;
        // Scanner ville_input = new Scanner(System.in);
        // System.out.println("Donnez le nom d'une ville : ");
        // ville = ville_input.nextLine();
        // ville = ville.trim();
        // String ville_maj = ville.toUpperCase(); // ou ville = ville.trim().toUpperCase();
        // System.out.println(ville_maj);
        // ville_input.close();

        // String s1;
        // String s2;
        // Scanner scan_s1 = new Scanner(System.in);
        // System.out.println("Donnez la première chaîne de caractères : ");
        // s1 = scan_s1.nextLine();
        // Scanner scan_s2 = new Scanner(System.in);
        // System.out.println("Donnez la deuxième chaîne de caractères : ");
        // s2 = scan_s2.nextLine();
        // if (s1.charAt(0) == s2.charAt(0)) {
        //     System.out.println("Les deux chaînes commencent par la même lettre.");
        // } else {
        //     System.out.println("Les deux chaînes ne commencent pas par la même lettre.");
        // }
        // System.out.println(s1 == s2);
        // System.out.println(s1.equals(s2));
        // System.out.println(s1.compareTo(s2));
        // System.out.println(s1.compareToIgnoreCase(s2));
        // scan_s1.close();
        // scan_s2.close();

        String mot1;
        String mot2;
        Scanner scan_mot1 = new Scanner(System.in);
        System.out.println("Donnez le premier mot : ");
        mot1 = scan_mot1.nextLine();
        Scanner scan_mot2 = new Scanner(System.in);
        System.out.println("Donnez le deuxième mot : ");
        mot2 = scan_mot2.nextLine();
        if (mot1.contains(mot2)) {
            Integer indicedebut = mot1.indexOf(mot2);
            Integer indicefin = indicedebut + mot2.length();
            mot1 = mot1.substring(0, indicedebut) + mot1.substring(indicefin, mot1.length());
        }
        System.out.println(mot1);
        scan_mot1.close();
        scan_mot2.close();
    }
}