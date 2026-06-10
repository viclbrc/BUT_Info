import java.util.Collection;
import java.util.HashMap;

public class Partie2Ex4 {
    public static void main(String[] args) {

    }
}

abstract class Personne {
    private String numeroSS;

    public Personne(String numeroSS) {
        this.numeroSS = numeroSS;
    }

    public String getNumeroSS() {
        return numeroSS;
    }

    public void setNumeroSS(String numeroSS) {
        this.numeroSS = numeroSS;
    }

    @Override
    public String toString() {
        return "Personne [numeroSS=" + numeroSS + "]";
    }

    public void afficher() {
        System.out.println("Personne [numeroSS=" + numeroSS + "]");
    }
}

class Universite {
    private String nom;
    private Collection<Etudiant> etudiants;
    private Collection<Enseignant> enseignants;
    private HashMap<Etudiant, String> diplome;
    private HashMap<Enseignant, Integer> contrat;

    public Universite(String nom, Collection<Etudiant> etudiants, Collection<Enseignant> enseignants, HashMap<Etudiant, String> diplome, HashMap<Enseignant, Integer> contrat) {
        this.nom = nom;
        this.etudiants = etudiants;
        this.enseignants = enseignants;
        this.diplome = diplome;
        this.contrat = contrat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Collection<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Collection<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(Collection<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    @Override
    public String toString() {
        return "Universite [nom=" + nom + "]";
    }

    public void afficher() {
        System.out.println("Universite [nom=" + nom + "]");
    }

    public void inscrireEtudiant(Etudiant etudiant, String dip) {
        if (etudiant == null || dip == null) {
            System.out.println("L'étudiant ou le diplôme ne peut pas être null.");
        } else if (etudiants.contains(etudiant)) {
            System.out.println("L'étudiant " + etudiant + " est déjà inscrit avec le diplôme " + diplome.get(etudiant) + ".");
        } else {
            addEtudiant(etudiant, dip);
        }
    }

    private void addEtudiant(Etudiant etudiant, String dip) {
        etudiants.add(etudiant);
        diplome.put(etudiant, dip);
    }

    public void supprimerEtudiant(Etudiant etudiant) {
        if (etudiant == null) {
            System.out.println("L'étudiant ne peut pas être null.");
        } else if (!etudiants.contains(etudiant)) {
            System.out.println("L'étudiant " + etudiant + " n'est pas inscrit.");
        } else if (!(diplome.size() > 1)) {
            System.out.println("Il doit rester au moins un étudiant dans l'université.");
        } else {
            delEtudiant(etudiant);
        }
    }

    private void delEtudiant(Etudiant etudiant) {
        etudiants.remove(etudiant);
        diplome.remove(etudiant);
    }

    public void inscrireEnseignant(Enseignant enseignant, Integer cont) {
        if (enseignant == null || cont == null) {
            System.out.println("L'enseignant ou le contrat ne peut pas être null.");
        } else if (enseignants.contains(enseignant)) {
            System.out.println("L'enseignant " + enseignant + " est déjà inscrit avec le contrat " + contrat.get(enseignant) + ".");
        } else {
            addEnseignant(enseignant, cont);
        }
    }

    private void addEnseignant(Enseignant enseignant, Integer cont) {
        enseignants.add(enseignant);
        contrat.put(enseignant, cont);
    }

    private void delEnseignant(Enseignant enseignant) {
        enseignants.remove(enseignant);
        contrat.remove(enseignant);
    }

    public void supprimerEnseignant(Enseignant enseignant) {
        if (enseignant == null) {
            System.out.println("L'enseignant ne peut pas être null.");
        } else if (!enseignants.contains(enseignant)) {
            System.out.println("L'enseignant " + enseignant + " n'est pas inscrit.");
        } else if (!(contrat.size() > 1)) {
            System.out.println("Il doit rester au moins un enseignant dans l'université.");
        } else {
            delEnseignant(enseignant);
        }
    }

    public void afficherEtudiants() {
        for (Etudiant etudiant : etudiants) {
            System.out.println("Nom : " + etudiant + ", diplôme : " + diplome.get(etudiant));
        }
    }

    public void afficherEnseignants() {
        for (Enseignant enseignant : enseignants) {
            System.out.println("Nom : " + enseignant + ", contrat : " + contrat.get(enseignant));
        }
    }
}

class Etudiant extends Personne {
    private Universite universite;

    public Etudiant(String numeroSS, Universite universite) {
        super(numeroSS);
        this.universite = universite;
    }

    @Override
    public String toString() {
        return "Etudiant [numeroSS=" + getNumeroSS() + ", universite=" + universite + "]";
    }

    @Override
    public void afficher() {
        System.out.println("Etudiant [numeroSS=" + getNumeroSS() + ", universite=" + universite + "]");
    }
}

class Enseignant extends Personne {
    private Universite universite;

    public Enseignant(String numeroSS, Universite universite) {
        super(numeroSS);
        this.universite = universite;
    }

    @Override
    public String toString() {
        return "Enseignant [numeroSS=" + getNumeroSS() + ", universite=" + universite + "]";
    }

    @Override
    public void afficher() {
        System.out.println("Enseignant [numeroSS=" + getNumeroSS() + ", universite=" + universite + "]");
    }
}