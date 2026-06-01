import java.util.Date;

public class Principale {
    public static void main(String[] args) {
    }
}

class Acteur {
    private String nom;
    private String prenom;
    
    public Acteur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void afficher() {
        System.out.println(prenom + " " + nom);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Acteur other = (Acteur) obj;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        return true;
    }
}

class Film {
    private String titre;
    private String nationalite;
    private int duree;
    private String resume;

    public Film(String titre, String nationalite, int duree, String resume) {
        this.titre = titre;
        this.nationalite = nationalite;
        this.duree = duree;
        this.resume = resume;
    }

    public String getTitre() {
        return titre;
    }

    public String getNationalite() {
        return nationalite;
    }

    public int getDuree() {
        return duree;
    }

    public String getResume() {
        return resume;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Film other = (Film) obj;
        if (titre == null) {
            if (other.titre != null)
                return false;
        } else if (!titre.equals(other.titre))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return titre + "\tFilm " + nationalite + "de " + duree + "mn\n"+ "Résumé : " + resume;
    }

    public void affiche() {
        System.out.println(this.toString());
    }
}

class Seance {
    private Date date;
    private String typeSeance;
    private Film monFilm;

    public Seance(Date date, String typeSeance) {
        this.date = date;
        this.typeSeance = typeSeance;
    }

    public Date getDate() {
        return date;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Seance other = (Seance) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (typeSeance == null) {
            if (other.typeSeance != null)
                return false;
        } else if (!typeSeance.equals(other.typeSeance))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Séance :\nDate ;" + date + ", typeSeance : " + typeSeance + ", monFilm : " + monFilm;
    }

    public void affiche() {
        System.out.println(this.toString());
    }

    public Seance (Date date, String typeSeance, Film monFilm) {
        this.date = date;
        this.typeSeance = typeSeance;
        this.monFilm = monFilm;
    }

    private void affecterFilm(Film f) {
        this.monFilm = f;
    }

    public void ajouterProgrammer(Film f) {
        if (f == null) {
            System.out.println("Le film ne peut pas être vide.");
            return;
        } else {
            affecterFilm(f);
        }
    }

    private void enleverFilm() {
        this.monFilm = null;
    }

    public void enleverProgrammer(Film f) {
        if (monFilm == null) {
            System.out.println("Aucun film n'est programmé pour cette séance.");
            return;
        } else if (!monFilm.equals(f)) {
            System.out.println("Le film à enlever ne correspond pas au film programmé.");
            return;
        } else {
            enleverFilm();
        }
    }
}