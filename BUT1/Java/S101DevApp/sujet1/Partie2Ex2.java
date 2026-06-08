import java.util.Collection;

public class Partie2Ex2 {
    public static void main(String[] args) {
    }
}

class Aeroport {
    private String indent;
    private String nom;

    public Aeroport(String indent, String nom) {
        this.indent = indent;
        this.nom = nom;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Aeroport [indent=" + indent + ", nom=" + nom + "]";
    }

    public void afficher() {
        System.out.println("Aeroport [indent=" + indent + ", nom=" + nom + "]");
    }
}

class Avion{
    private String immat;

    public Avion(String immat) {
        this.immat = immat;
    }

    public String getImmat() {
        return immat;
    }

    public void setImmat(String immat) {
        this.immat = immat;
    }

    @Override
    public String toString() {
        return "Avion [immat=" + immat + "]";
    }

    public void afficher() {
        System.out.println("Avion [immat=" + immat + "]");
    }
}

class Personne{
    private String nom;
    private String prenom;
    private String numPasseport;

    public Personne(String nom, String prenom, String numPasseport) {
        this.nom = nom;
        this.prenom = prenom;
        this.numPasseport = numPasseport;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumPasseport() {
        return numPasseport;
    }

    public void setNumPasseport(String numPasseport) {
        this.numPasseport = numPasseport;
    }

    @Override
    public String toString() {
        return "Personne [nom=" + nom + ", prenom=" + prenom + ", numPasseport=" + numPasseport + "]";
    }

    public void afficher() {
        System.out.println("Personne [nom=" + nom + ", prenom=" + prenom + ", numPasseport=" + numPasseport + "]");
    }
}

class Vol{
    private int numVol;
    private Aeroport aeroport;
    private Avion avion;
    private Collection<Personne> passagers;
    
    public Vol(int numVol, Aeroport aeroport, Avion avion, Collection<Personne> passagers) {
        this.numVol = numVol;
        this.aeroport = aeroport;
        this.avion = avion;
        this.passagers = passagers;
    }

    public int getNumVol() {
        return numVol;
    }

    public void setNumVol(int numVol) {
        this.numVol = numVol;
    }

    public Aeroport getAeroport() {
        return aeroport;
    }

    public void setAeroport(Aeroport aeroport) {
        this.aeroport = aeroport;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Collection<Personne> getPassagers() {
        return passagers;
    }

    public void setPassagers(Collection<Personne> passagers) {
        this.passagers = passagers;
    }

    @Override
    public String toString() {
        return "Vol [numVol=" + numVol + ", aeroport=" + aeroport + ", avion=" + avion + ", passagers=" + passagers
                + "]";
    }

    public void afficher() {
        System.out.println("Vol [numVol=" + numVol + ", aeroport=" + aeroport + ", avion=" + avion + ", passagers=" + passagers
                + "]");
    }

    public void addPassager(Personne passager) {
        passagers.add(passager);
    }

    public void delPassager(Personne passager) {
        passagers.remove(passager);
    }

    public void ajouterPassager(Personne passager) {
        if (passager == null) {
            System.out.println("Le passager ne peut pas être vide.");
        } else if (passagers.contains(passager)) {
            System.out.println("Le passager est déjà dans la liste.");
        } else {
            addPassager(passager);
        }
    }

    public void supprimerPassager(Personne passager) {
        if (passager == null) {
            System.out.println("Le passager ne peut pas être vide.");
        } else if (!passagers.contains(passager)) {
            System.out.println("Le passager n'est pas dans la liste.");
        } else if (!(passagers.size() > 1)) {
            System.out.println("Le vol doit avoir au moins un passager.");
        } else {
            delPassager(passager);
        }
    }
}