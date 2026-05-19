package sujet_1;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ex4 {
	private final int numero;
	private final Date dateT;
	private final Collection<Integer> lesNum;

	public static void main(String[] args) {
		Ex4 tirage = new Ex4(1);
		Collection<Integer> joueur = new ArrayList<>();
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Saisie du tirage :");
			for (int i = 0; i < 6; i++) {
				tirage.ajouterNumero(sc);
			}
			System.out.println("Saisie du joueur :");
			for (int i = 0; i < 6; i++) {
				while (true) {
					try {
						System.out.print("Numéro : ");
						int n = sc.nextInt();
						if (n < 1 || n > 49 || joueur.contains(n)) {
							throw new LotoException("Numéro invalide.");
						}
						joueur.add(n);
						break;
					} catch (InputMismatchException e) {
						System.out.println("Erreur : entier attendu.");
						sc.nextLine();
					} catch (LotoException e) {
						System.out.println("Erreur: " + e.getMessage());
					}
				}
			}
			tirage.afficherNombreGagnants(joueur);
		}
	}

	public Ex4(int numero) {
		this.numero = numero;
		this.dateT = Calendar.getInstance().getTime();
		this.lesNum = new ArrayList<>();
	}

	public int getNumero() {
		return numero;
	}

	public Date getDateT() {
		return dateT;
	}

	public void ajouterNumero(Scanner sc) {
		while (true) {
			try {
				if (lesNum.size() >= 6) {
					throw new LotoException("Plus de 6 numéros.");
				}
				System.out.print("Numéro : ");
				int n = sc.nextInt();
				if (n < 1 || n > 49) {
					throw new LotoException("Le numéro doit être entre 1 et 49.");
				}
				if (lesNum.contains(n)) {
					throw new LotoException("Numéro déjà dans le tirage.");
				}
				lesNum.add(n);
				return;
			} catch (InputMismatchException e) {
				System.out.println("Erreur : entier attendu.");
				sc.nextLine();
			} catch (LotoException e) {
				System.out.println("Erreur: " + e.getMessage());
				if (lesNum.size() >= 6) {
					return;
				}
			}
		}
	}

	public void afficherNombreGagnants(Collection<Integer> nums) {
		try {
			if (nums.size() != 6 || new HashSet<>(nums).size() != 6) {
				throw new LotoException("Il faut 6 numéros différents.");
			}

			int nb = 0;
			for (int n : nums) {
				if (lesNum.contains(n)) {
					nb++;
				}
			}
			System.out.println("Nombre de numéros gagnants : " + nb);
		} catch (LotoException e) {
			System.out.println("Erreur: " + e.getMessage());
		}
	}

	static class LotoException extends Exception {
		public LotoException(String message) {
			super(message);
		}
	}
}
