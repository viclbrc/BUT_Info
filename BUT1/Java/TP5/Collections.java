package BUT1.Java.TP5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Collections {
    public static void main(String[] args) {
        Cours math = new Cours("Mathématiques");
        Cours physique = new Cours("Physique");
        Etudiant etudiant1 = new Etudiant("Dupont", "Jean");
        Etudiant etudiant2 = new Etudiant("Durand", "Marie");
        etudiant1.ajouterNote(math, 15);
        etudiant1.ajouterNote(physique, 12);
        etudiant2.ajouterNote(math, 18);
        etudiant2.ajouterNote(physique, 14);
        Groupe groupe = new Groupe("Groupe A");
        groupe.ajouterEtudiant(etudiant1);
        groupe.ajouterEtudiant(etudiant2);
        groupe.afficherEtudiants();
        groupe.calculerMoyenneGroupe();
        etudiant1.calculerMoyenne();
        etudiant1.meilleureNote();
        etudiant1.pireNote();
        etudiant2.calculerMoyenne();
        etudiant2.meilleureNote();
        etudiant2.pireNote();
        etudiant1.afficherNote(math);
        etudiant2.afficherNote(physique);
        groupe.premierEtDernierEtudiant();
        groupe.nombreEtudiants();
        groupe.retirerEtudiant(etudiant2);
        etudiant1.modifierNote(math, 16);
        groupe.afficherEtudiants();
        etudiant1.retirerNote(physique);
        etudiant1.afficherNotes();
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
        Iterator<Map.Entry<Cours, Float>> it = notes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Cours, Float> entry = it.next();
            if (entry.getKey().equals(cours)) {
                it.remove();
                System.out.println("La note a été retirée.");
                return;
            }
        }
        System.out.println("Le cours n'existe pas pour cet étudiant.");
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
    private HashSet<Etudiant> etudiants;

    public Groupe(String nom) {
        this.nom = nom;
        this.etudiants = new HashSet<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterEtudiant(Etudiant etudiant) {
        etudiants.add(etudiant);
    }

    public void retirerEtudiant(Etudiant etudiant) {
        Iterator<Etudiant> it = etudiants.iterator();
        while (it.hasNext()) {
            if (it.next().equals(etudiant)) {
                it.remove();
                System.out.println("L'étudiant a été retiré du groupe.");
                return;
            }
        }
        System.out.println("L'étudiant n'existe pas dans ce groupe.");
    }

    public void afficherEtudiants() {
        System.out.println("Groupe: " + nom);
        for (Etudiant etudiant : etudiants) {
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
        for (Etudiant etudiant : etudiants) {
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
        float meilleureMoyenne = -1;
        float pireMoyenne = 21;

        for (Etudiant etudiant : etudiants) {
            if (etudiant.notes.isEmpty()) {
                continue;
            }

            float somme = 0;
            for (float note : etudiant.notes.values()) {
                somme += note;
            }
            float moyenne = somme / etudiant.notes.size();

            if (premier == null || moyenne > meilleureMoyenne) {
                premier = etudiant;
                meilleureMoyenne = moyenne;
            }

            if (dernier == null || moyenne < pireMoyenne) {
                dernier = etudiant;
                pireMoyenne = moyenne;
            }
        }

        if (premier == null || dernier == null) {
            System.out.println("Aucun étudiant avec des notes dans ce groupe.");
            return;
        }

        System.out.println("Premier étudiant : " + premier);
        System.out.println("Dernier étudiant : " + dernier);
    }
}