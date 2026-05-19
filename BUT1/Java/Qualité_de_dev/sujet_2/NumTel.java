package sujet_2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

public class NumTel {
    private String valeur;

    public static void main(String[] args) {
        try {
            NumTel num1 = new NumTel("0123456789");
            System.out.println(num1);
            NumTel num2 = new NumTel("123456789"); // Numéro incorrect
        } catch (NumTelException e) {
            System.out.println(e.getMessage());
        }
        Abonne abonne = new Abonne();
        try {
            abonne.ajouterNumTel();
        } catch (NumTel.NumTelException e) {
            System.out.println(e.getMessage());
        }
        abonne.afficherNums();
        try {
            abonne.ajouterNumTel();
        } catch (NumTel.NumTelException e) {
            System.out.println(e.getMessage());
        }
        try {
            abonne.supprimerNumTel();
        } catch (NumTel.NumTelException e) {
            System.out.println(e.getMessage());
        }
    }

    public NumTel(String valeur) throws NumTelException {
        if (valeur == null || valeur.length() != 10) {
            throw new NumTelException(valeur + " : Ce numéro est incorrect !");
        }
        this.valeur = valeur;
    }

    public String toString() {
        return valeur;
    }

    @Override
    public boolean equals(Object autre) {
        if (this == autre) {
            return true;
        }
        if (!(autre instanceof NumTel numTel)) {
            return false;
        }
        return Objects.equals(valeur, numTel.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    static class NumTelException extends Exception {
        public NumTelException(String message) {
            super(message);
        }
    }
}

class Abonne {
    private String nom;
    private String adresse;
    private Collection<NumTel> instances_numtel;
    private final Scanner scanner;

    public Abonne() {
        this.scanner = new Scanner(System.in);
        this.instances_numtel = new ArrayList<>();
        System.out.print("Entrez le nom de l'abonné: ");
        this.nom = scanner.nextLine();
        System.out.print("Entrez l'adresse de l'abonné: ");
        this.adresse = scanner.nextLine();
    }
    
        public void ajouterNumTel() throws NumTel.NumTelException {
            System.out.print("Entrez un numéro de téléphone à ajouter: ");
            try {
                NumTel numTel = new NumTel(scanner.next());
                if (instances_numtel.contains(numTel)) {
                    System.out.println("Ce numéro de téléphone existe déjà pour cet abonné.");
                } else {
                    instances_numtel.add(numTel);
                    System.out.println("Numéro de téléphone ajouté avec succès.");
                }
            } catch (NumTel.NumTelException e) {
                System.out.println(e.getMessage());
            }
        }

        public String toString() {
            return "Abonné: " + nom + ", Adresse: " + adresse + ", Numéros de téléphone: " + instances_numtel;
        }

        public void afficherNums() {
            System.out.println("Numéros de téléphone de l'abonné " + nom + ":");
            for (NumTel num : instances_numtel) {
                System.out.println(num);
            }
        }

        public void supprimerNumTel() throws NumTel.NumTelException {
            System.out.print("Entrez le numéro de téléphone à supprimer: ");
            try {
                NumTel num = new NumTel(scanner.next());
                if (instances_numtel.remove(num)) {
                    System.out.println("Numéro de téléphone supprimé avec succès.");
                } else {
                    System.out.println("Ce numéro de téléphone n'existe pas pour cet abonné.");
                }
            } catch (NumTel.NumTelException e) {
                System.out.println(e.getMessage());
            } 
            }
}
