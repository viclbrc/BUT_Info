package sujet_2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NumTel {
    private final String valeur;
    private final String commentaire;

    public static void main(String[] args) {
        try {
            NumTel num1 = new NumTel("0123456789");
            System.out.println(num1);
            new NumTel("123456789"); // Numéro incorrect
        } catch (NumTelException e) {
            System.out.println(e.getMessage());
        }
        Abonne abonne = new Abonne();
        AnnuaireInverse annuaire = new AnnuaireInverse("Annuaire inversé");
        try {
            abonne.ajouterNumTel();
        } catch (NumTel.NumTelException e) {
            System.out.println(e.getMessage());
        }
        annuaire.inscrire(abonne);
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
        annuaire.afficher();
        abonne.afficherNums();
    }

    public NumTel(String valeur) throws NumTelException {
        this(valeur, "");
    }

    public NumTel(String valeur, String commentaire) throws NumTelException {
        if (valeur == null || valeur.length() != 10) {
            throw new NumTelException(valeur + " : Ce numéro est incorrect !");
        }
        this.valeur = valeur;
        this.commentaire = commentaire == null ? "" : commentaire.trim();
    }

    public String toString() {
        if (commentaire.isEmpty()) {
            return valeur;
        }
        return valeur + " (" + commentaire + ")";
    }

    public String getValeur() {
        return valeur;
    }

<<<<<<< HEAD
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
=======
    public String getCommentaire() {
        return commentaire;
>>>>>>> b72b842 (qualité dev sujet 2)
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
    private HashSet<AnnuaireInverse> annuaires;
    private final Scanner scanner;

    public Abonne() {
        this.scanner = new Scanner(System.in);
        this.instances_numtel = new ArrayList<>();
        this.annuaires = new HashSet<>();
        System.out.print("Entrez le nom de l'abonné: ");
        this.nom = scanner.nextLine();
        System.out.print("Entrez l'adresse de l'abonné: ");
        this.adresse = scanner.nextLine();
    }

    public String getNom() {
        return nom;
    }

    public Collection<NumTel> getNumTels() {
        return instances_numtel;
    }

    void ajouterAnnuaire(AnnuaireInverse annuaire) {
        annuaires.add(annuaire);
    }

    void retirerAnnuaire(AnnuaireInverse annuaire) {
        annuaires.remove(annuaire);
    }

    private NumTel trouverNumTel(String valeur) {
        for (NumTel numTel : instances_numtel) {
            if (numTel.getValeur().equals(valeur)) {
                return numTel;
            }
        }

        return null;
    }

    public void ajouterNumTel() throws NumTel.NumTelException {
        System.out.print("Entrez un numéro de téléphone à ajouter: ");
        String numero = scanner.nextLine().trim();
        new NumTel(numero);
        System.out.print("Entrez un commentaire: ");
        String commentaire = scanner.nextLine().trim();
        ajouterNumTel(numero, commentaire);
    }

    public void ajouterNumTel(String numero, String commentaire) throws NumTel.NumTelException {
        if (trouverNumTel(numero) != null) {
            System.out.println("Ce numéro de téléphone existe déjà pour cet abonné.");
            return;
        }

        NumTel numTel = new NumTel(numero, commentaire);
        instances_numtel.add(numTel);
        for (AnnuaireInverse annuaire : annuaires) {
            annuaire.ajouterNumero(this, numTel);
        }
        System.out.println("Numéro de téléphone ajouté avec succès.");
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
        String numero = scanner.nextLine().trim();
        supprimerNumTel(numero);
    }

    public void supprimerNumTel(String numero) throws NumTel.NumTelException {
        NumTel numTel = trouverNumTel(numero);
        if (numTel == null) {
            System.out.println("Ce numéro de téléphone n'existe pas pour cet abonné.");
            return;
        }

        instances_numtel.remove(numTel);
        for (AnnuaireInverse annuaire : annuaires) {
            annuaire.supprimerNumero(numero, this);
        }
        System.out.println("Numéro de téléphone supprimé avec succès.");
    }
}

class AnnuaireInverse {
    private final String nom;
    private final HashMap<String, Abonne> annuaire; // numéro -> abonné
    private final HashMap<Abonne, HashSet<String>> abonnesToNums; // abonné -> numéros

    public AnnuaireInverse(String nom) {
        this.nom = nom;
        this.annuaire = new HashMap<>();
        this.abonnesToNums = new HashMap<>();
    }

    public void inscrire(Abonne abo) {
        if (abo == null) {
            return;
        }
        abo.ajouterAnnuaire(this);
        HashSet<String> nums = new HashSet<>();
        for (NumTel numTel : abo.getNumTels()) {
            annuaire.put(numTel.getValeur(), abo);
            nums.add(numTel.getValeur());
        }
        // ensure an entry exists even if empty
        abonnesToNums.putIfAbsent(abo, nums);
    }

    public Abonne qui(String numero) {
        return annuaire.get(numero);
    }

    public void desinscrire(Abonne abo) {
        if (abo == null) {
            return;
        }
        abo.retirerAnnuaire(this);
        HashSet<String> nums = abonnesToNums.remove(abo);
        if (nums != null) {
            for (String numero : nums) {
                annuaire.remove(numero);
            }
        } else {
            // fallback: remove by scanning map values
            List<String> numerosASupprimer = new ArrayList<>();
            for (Map.Entry<String, Abonne> entree : annuaire.entrySet()) {
                if (entree.getValue() == abo) {
                    numerosASupprimer.add(entree.getKey());
                }
            }
            for (String numero : numerosASupprimer) {
                annuaire.remove(numero);
            }
        }
    }

    void ajouterNumero(Abonne abo, NumTel numTel) {
        if (abo == null || numTel == null) return;
        annuaire.put(numTel.getValeur(), abo);
        abonnesToNums.computeIfAbsent(abo, k -> new HashSet<>()).add(numTel.getValeur());
    }

    void supprimerNumero(String numero, Abonne abo) {
        if (numero == null) return;
        Abonne current = annuaire.get(numero);
        if (current == abo) {
            annuaire.remove(numero);
            HashSet<String> set = abonnesToNums.get(abo);
            if (set != null) {
                set.remove(numero);
                if (set.isEmpty()) {
                    abonnesToNums.remove(abo);
                }
            }
<<<<<<< HEAD
}
=======
        }
    }

    public void afficher() {
        System.out.println("Annuaire inversé : " + nom);
        for (Map.Entry<String, Abonne> entree : annuaire.entrySet()) {
            System.out.println(entree.getKey() + " -> " + entree.getValue().getNom());
        }
    }
}
>>>>>>> b72b842 (qualité dev sujet 2)
