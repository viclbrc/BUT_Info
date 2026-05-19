package sujet_1;
public class Ex2 {

    public static void main(String[] args) throws MonException {
            System.out.println("Moyenne = " + moyenne(args));
    }

    static int moyenne(String[] valeurs) throws MonException {
        if (valeurs.length == 0) {
            throw new MonException();
        }

        int somme = 0;
        int nbValeursValides = 0;

        for (String valeur : valeurs) {
            try {
                int note = Integer.parseInt(valeur);
                somme += note;
                nbValeursValides++;
            } catch (NumberFormatException e) {
                System.out.println("Argument ignoré (pas un entier) : " + valeur);
            }
        }

        if (nbValeursValides == 0) {
            throw new MonException("Aucune note entière valide.");
        }

        return somme / nbValeursValides;
    }
}

class MonException extends Exception {
    public MonException() {
        super("Le nombre de notes est de 0.");
        System.out.println("Erreur: " + getMessage());
    }

    public MonException(String message) {
        super(message);
        System.out.println("Erreur: " + getMessage());
    }

    @Override
    public String toString() {
        return "MonException: " + getMessage();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
