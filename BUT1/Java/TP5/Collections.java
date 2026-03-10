package BUT1.Java.TP5;

import java.util.HashMap;

public class Collections {
    public static void main(String[] args) {
        
    }
}

class Cours {
    private String intitule;

    public Cours(String intitule) {
        this.intitule = intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cours cours = (Cours) o;
        return intitule.equals(cours.intitule);
    }

    public String toString() {
        return "Cours: " + intitule;
    }
}

class Etudiant {
    private int numero;
    private static int compteur = 0;
    private String nom;
    private String prenom;
    public HashMap<Cours, Float> notes;

    public Etudiant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.notes = new HashMap<>();
        compteur++;
        this.numero = compteur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNumero() {
        return numero;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return numero == etudiant.numero;
    }

    public String toString() {
        return "Etudiant: " + nom + " " + prenom + ", Numéro: " + numero;
    }

    public void ajouterNote(Cours cours, float note) {
        notes.put(cours, note);
    }

    public void modifierNote(Cours cours, float note) {
        if (notes.containsKey(cours)) {
            notes.put(cours, note);
        } else {
            System.out.println("Le cours n'existe pas pour cet étudiant.");
        }
    }

    public void retirerNote(Cours cours) {
        if (notes.containsKey(cours)) {
            notes.remove(cours);
        } else {
            System.out.println("Le cours n'existe pas pour cet étudiant.");
        }
    }

    public void afficherNotes() {
        System.out.println("Notes de " + nom + " " + prenom + ":");
        for (Cours cours : notes.keySet()) {
            System.out.println(cours.getIntitule() + ": " + notes.get(cours));
        }
    }

    public void calculerMoyenne() {
        if (notes.isEmpty()) {
            System.out.println("Aucune note pour cet étudiant.");
            return;
        }
        float somme = 0;
        for (float note : notes.values()) {
            somme += note;
        }
        float moyenne = somme / notes.size();
        System.out.println("Moyenne de " + nom + " " + prenom + ": " + moyenne);
    }

    public void meilleureNote() {
        if (notes.isEmpty()) {
            System.out.println("Aucune note pour cet étudiant.");
            return;
        }
        float meilleure = 0;
        Cours meilleurCours = null;
        for (Cours cours : notes.keySet()) {
            float note = notes.get(cours);
            if (note > meilleure) {
                meilleure = note;
                meilleurCours = cours;
            }
        }
        System.out.println("Meilleure note de " + nom + " " + prenom + ": " + meilleure + " en " + meilleurCours.getIntitule());
    }

    public void pireNote() {
        if (notes.isEmpty()) {
            System.out.println("Aucune note pour cet étudiant.");
            return;
        }
        float pire = 20;
        Cours pireCours = null;
        for (Cours cours : notes.keySet()) {
            float note = notes.get(cours);
            if (note < pire) {
                pire = note;
                pireCours = cours;
            }
        }
        System.out.println("Pire note de " + nom + " " + prenom + ": " + pire + " en " + pireCours.getIntitule());
    }

    public void afficherNote(Cours cours) {
        if (notes.containsKey(cours)) {
            System.out.println("Note de " + nom + " " + prenom + " en " + cours.getIntitule() + ": " + notes.get(cours));
        } else {
            System.out.println("Le cours n'existe pas pour cet étudiant.");
        }
    }
}

class Groupe {
    private String nom;
    private HashMap<Integer, Etudiant> etudiants;

    public Groupe(String nom) {
        this.nom = nom;
        this.etudiants = new HashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterEtudiant(Etudiant etudiant) {
        etudiants.put(etudiant.getNumero(), etudiant);
    }

    public void retirerEtudiant(Etudiant etudiant) {
        if (etudiants.containsKey(etudiant.getNumero())) {
            etudiants.remove(etudiant.getNumero());
        } else {
            System.out.println("L'étudiant n'existe pas dans ce groupe.");
        }
    }

    public void afficherEtudiants() {
        System.out.println("Groupe: " + nom);
        for (Etudiant etudiant : etudiants.values()) {
            System.out.println(etudiant);
        }
    }

    public void nombreEtudiants() {
        System.out.println("Nombre d'étudiants dans le groupe " + nom + ": " + etudiants.size());
    }

    public void calculerMoyenneGroupe() {
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant dans ce groupe.");
            return;
        }
        float somme = 0;
        int count = 0;
        for (Etudiant etudiant : etudiants.values()) {
            for (float note : etudiant.notes.values()) {
                somme += note;
                count++;
            }
        }
        float moyenne = somme / count;
        System.out.println("Moyenne du groupe " + nom + ": " + moyenne);
    }

    public void premierEtDernierEtudiant() {
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant dans ce groupe.");
            return;
        }
        Etudiant premier = null;
        Etudiant dernier = null;
        for (Etudiant etudiant : etudiants.values()) {
            if (premier == null || etudiant.getNumero() < premier.getNumero()) {
                premier = etudiant;
            }
            if (dernier == null || etudiant.getNumero() > dernier.getNumero()) {
                dernier = etudiant;
            }
        }
        System.out.println("Premier étudiant: " + premier);
        System.out.println("Dernier étudiant: " + dernier);
    }
}