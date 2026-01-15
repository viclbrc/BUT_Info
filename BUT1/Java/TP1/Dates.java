import java.text.SimpleDateFormat;
import java.util.*;

public class Dates {
    public static void main(String[] args) {
        Long temps = System.currentTimeMillis();
        System.out.println("Temps écoulé depuis le 1er janvier 1970 en millisecondes : " + temps);
        Calendar calendrier = Calendar.getInstance();
        System.out.println("Nous sommes le " + calendrier.get(Calendar.DAY_OF_WEEK) + "ème jour de la semaine, le " + calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH) + 1) + "/" + calendrier.get(Calendar.YEAR) + ".");
        System.out.println("Il est " + calendrier.get(Calendar.HOUR_OF_DAY) + " heures, " + calendrier.get(Calendar.MINUTE) + " minutes et " + calendrier.get(Calendar.SECOND) + " secondes.");

        String[] mois = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"};
        String[] jours = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};

        System.out.println("Nous sommes le " + jours[calendrier.get(Calendar.DAY_OF_WEEK) - 1] + " " + calendrier.get(Calendar.DAY_OF_MONTH) + " " + mois[calendrier.get(Calendar.MONTH)] + " " + calendrier.get(Calendar.YEAR) + ".");

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        System.out.println("Maintenant : " + f.format(date));
        SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        System.out.println("Maintenant 2 : " + f2.format(date));
        SimpleDateFormat f3 = new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss");
        System.out.println("Maintenant 3 : " + f3.format(date));


    }
}