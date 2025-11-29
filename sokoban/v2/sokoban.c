/**
* @file sokoban.c
* @brief Jeu du Sokoban
* @author Victor CORBEL / 1B1
* @version 2.0
* @date 09/11/2025
*
* Programme du jeu du Sokoban.
* But du jeu : pousser toutes les caisses ($) sur les cibles (.). Le joueur contrôle Sokoban (@).
*
* Conventions de codage du fichier correspondant disponible sur Moodle respectées.
*/

#include <stdlib.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>

// Déclaration des constantes
#define TAILLE 12
#define CHAR_VIDE ' '
#define CHAR_MUR '#'
#define CHAR_CAISSE '$'
#define CHAR_CIBLE '.'
#define CHAR_CAISSE_CIBLE '*'
#define CHAR_JOUEUR '@'
#define CHAR_JOUEUR_CIBLE '+'
typedef char t_Plateau[TAILLE][TAILLE];
#define MAX_DEPLACEMENTS 1000
typedef char t_tabDeplacement[MAX_DEPLACEMENTS];
#define ZOOM_MIN 1
#define ZOOM_MAX 3

// Déclaration des fonctions
void afficher_entete(t_Plateau plateau, char fichier[], int coups, int zoom);
void afficher_plateau(t_Plateau plateau, char fichier[], int zoom);
void charger_partie(t_Plateau plateau, char fichier[]);
int kbhit();
void trouver_joueur(t_Plateau plateau, int *lig, int *col);
void enregistrer_partie(t_Plateau plateau, char fichier[]);
bool gagne(t_Plateau plateau);
char deplacer(t_Plateau plateau, int lig, int col, char direction);
void annuler_dernier_deplacement(t_Plateau plateau, t_tabDeplacement t, int *nb, int *coups);
void enregistrerDeplacements(t_tabDeplacement t, int nb, char fic[]);

int main() {
    t_Plateau plateau = {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}}; // Le plateau de 12x12
    char fichier[100]; // Nom du fichier
    bool estAbandonee = false; // Partie abandonnée ou pas
    char touche = '\0'; // Touche tapée
    char reponse; // Réponse de l'utilisateur
    int coups = 0; // Nombre de coups
    int ligne; // Ligne du joueur
    int colonne; // Colonne du joueur
    int nouvelleLigne; // Nouvelle ligne du joueur
    int nouvelleColonne; // Nouvelle colonne du joueur
    int zoom = 1; // Zoom
    t_tabDeplacement tabDeplacements = {0};
    int nbDeplacements = 0;

    // Affiche l'entête et demande quel niveau
    afficher_entete(plateau, fichier, coups, zoom);
    printf("Quel fichier ? ");
    scanf("%s", fichier);
    getchar();

    // Chargement et affichage de la partie/du plateau
    charger_partie(plateau, fichier);
    afficher_entete(plateau, fichier, coups, zoom);
    afficher_plateau(plateau, fichier, zoom);

    // Lance le jeu
    while ( (gagne(plateau) == 0) && (estAbandonee == false) ) { // Tant que la partie n'est pas gagnée et pas abandonnée
        afficher_entete(plateau, fichier, coups, zoom);
        afficher_plateau(plateau, fichier, zoom);
        while (kbhit() == 0) {
        }
        touche = getchar();
        switch (touche) {
            case 'z':
            case 's':
            case 'q':
            case 'd':
                trouver_joueur(plateau, &ligne, &colonne);
                // deplacer returns a char representing the recorded move
                ;
                char mv = deplacer(plateau, ligne, colonne, touche);
                if (mv != '\0' && nbDeplacements < MAX_DEPLACEMENTS) {
                    tabDeplacements[nbDeplacements++] = mv;
                    coups++;
                }
                // Vérifier nouvelle position
                trouver_joueur(plateau, &nouvelleLigne, &nouvelleColonne);
                break;
            case '+':
                if (zoom < ZOOM_MAX) { zoom++; }
                break;
            case '-':
                if (zoom > ZOOM_MIN) { zoom--; }
                break;
            case 'u':
                annuler_dernier_deplacement(plateau, tabDeplacements,
                                            &nbDeplacements, &coups);
                break;
            case 'r':
                printf("Voulez-vous vraiment recommencer ? (o/n) : ");
                scanf("%c", &reponse);
                if (reponse == 'o') { // Si oui, partie recommencée
                    charger_partie(plateau, fichier);
                    coups = 0;
                    nbDeplacements = 0;
                    zoom = 1;
                    afficher_entete(plateau, fichier, coups, zoom);
                    afficher_plateau(plateau, fichier, zoom);
                }
                // Sinon, on continue la partie
                break;
            case 'x':
                estAbandonee = true; // Partie abandonnée
                break;
            default:
                break;
            }
        }

        if (gagne(plateau) == true) {
            printf("\nBravo ! Niveau terminé en %d coups.\n", coups);
            printf("Voulez-vous enregistrer les deplacements ? (o/n) : ");
            scanf(" %c", &reponse);
            if (reponse == 'o') {
                char nomFic[100];
                printf("Nom du fichier de deplacements (.dep) : ");
                scanf("%s", nomFic);
                enregistrerDeplacements(tabDeplacements, nbDeplacements,
                                       nomFic);
                printf("Deplacements sauvegardes.\n");
            }
        } else if (estAbandonee) {
            printf("\nPartie abandonnée après %d coups.\n", coups);
            printf("Voulez-vous sauvegarder la partie ? (o/n) : ");
            scanf(" %c", &reponse);
            if (reponse == 'o') { // Si oui, sauvegarde de la partie
                printf("Nom du fichier de sauvegarde (.sok) : ");
                scanf("%s", fichier);
                enregistrer_partie(plateau, fichier);
                printf("Partie sauvegardée.\n");
            }
            // Sinon, jeu quitté car partie non sauvegardée
            // Propose also to save moves
            printf("Voulez-vous enregistrer les deplacements ? (o/n) : ");
            scanf(" %c", &reponse);
            if (reponse == 'o') {
                char nomFicDep[100];
                printf("Nom du fichier de deplacements (.dep) : ");
                scanf("%s", nomFicDep);
                enregistrerDeplacements(tabDeplacements, nbDeplacements,
                                       nomFicDep);
                printf("Deplacements sauvegardes.\n");
            }
        }

    return EXIT_SUCCESS;
}

/**
* @brief Affiche l'entête de la partie
* @param plateau Le plateau de jeu
* @param fichier Le nom du fichier de sauvegarde
* @param coups Le nombre de coups effectués
* @return void
*/
void afficher_entete(t_Plateau plateau, char fichier[], int coups,
                     int zoom) {
    system("clear");
    printf("Partie : %s\n", fichier);
    printf("Touches : z (haut), s (bas), q (gauche), d (droite)\n");
    printf("         + (zoom), - (dezoom), u (undo), x (abandon), r (recommencer)\n");
    printf("Deplacements : %d    zoom : %d\n", coups, zoom);
}

/**
* @brief Affiche le plateau de jeu
* @param plateau Le plateau de jeu
* @param fichier Le nom du fichier de sauvegarde
* @return void
*/
void afficher_plateau(t_Plateau plateau, char fichier[], int zoom) {
    char c;
    for (int i = 0 ; i < TAILLE ; i++) {
        for (int repv = 0 ; repv < zoom ; repv++) {
            for (int j = 0 ; j < TAILLE ; j++) {
                c = plateau[i][j];
                // Change pas le caractère quand le joueur ou une caisse est sur une cible
                if (c == CHAR_CAISSE_CIBLE) {
                    c = CHAR_CAISSE;
                } else if (c == CHAR_JOUEUR_CIBLE) {
                    c = CHAR_JOUEUR;
                }
                for (int reph = 0 ; reph < zoom ; reph++) {
                    printf("%c", c);
                }
                // spacing between cells at scale 1 was a space; for scale>1 rely on char repetition
                if (zoom == 1) { printf(" "); }
            }
            printf("\n");
        }
    }
}

/**
* @brief Charge la partie à partir d'un fichier
* @param plateau Le plateau de jeu
* @param fichier Le nom du fichier de sauvegarde
* @return void
*/
void charger_partie(t_Plateau plateau, char fichier[]) {
    FILE *f = fopen(fichier, "r");
    if (!f) {
        printf("ERREUR SUR FICHIER");
        exit(EXIT_FAILURE);
    }
    for (int i = 0 ; i < TAILLE ; i++) {
        fread(plateau[i], sizeof(char), TAILLE, f);
        fseek(f, 1, SEEK_CUR);
    }
    fclose(f);
}

/**
* @brief Observe si une touche du clavier a été tapée
* @return int : 0 si aucune touche n'a été tapée, 1 sinon
*/
int kbhit() {
    struct termios oldt, newt;
    int oldf, ch;
    
    tcgetattr(STDIN_FILENO, &oldt);
    newt = oldt;
    newt.c_lflag &= ~(ICANON | ECHO);
    tcsetattr(STDIN_FILENO, TCSANOW, &newt);
    
    oldf = fcntl(STDIN_FILENO, F_GETFL, 0);
    fcntl(STDIN_FILENO, F_SETFL, oldf | O_NONBLOCK);
    
    ch = getchar();
    
    tcsetattr(STDIN_FILENO, TCSANOW, &oldt);
    fcntl(STDIN_FILENO, F_SETFL, oldf);
    
    if (ch != EOF) {
        ungetc(ch, stdin);
        return 1;
    }
    return 0;
}

/**
 * @brief Recherche la position du joueur sur le plateau
 * @param plateau Le plateau de jeu
 * @param lig Pointeur pour stocker la ligne trouvée
 * @param col Pointeur pour stocker la colonne trouvée
 * @return int : 1 si le joueur est trouvé, 0 sinon
 */
void trouver_joueur(t_Plateau plateau, int *lig, int *col) {
    for (int i = 0 ; i < TAILLE ; i++) {
        for (int j = 0 ; j < TAILLE ; j++) {
            if (plateau[i][j] == CHAR_JOUEUR || plateau[i][j] == CHAR_JOUEUR_CIBLE) { // Si le joueur est trouvé sur le plateau
                *lig = i;
                *col = j;
            }
        }
    }
}

/**
* @brief Enregistre la partie en cours dans un fichier .sok
* @param plateau Le plateau de jeu
* @param fichier Le nom du fichier de sauvegarde
* @return void
*/
void enregistrer_partie(t_Plateau plateau, char fichier[]) {
    FILE * f;
    char finDeLigne='\n';

    f = fopen(fichier, "w");
    for (int ligne = 0 ; ligne < TAILLE ; ligne++) {
        for (int colonne = 0 ; colonne < TAILLE ; colonne++) {
            fwrite(&plateau[ligne][colonne], sizeof(char), 1, f);
        }
        fwrite(&finDeLigne, sizeof(char), 1, f);
    }
    fclose(f);
}

void enregistrerDeplacements(t_tabDeplacement t, int nb, char fic[]){
    FILE * f;

    f = fopen(fic, "w");
    fwrite(t,sizeof(char), nb, f);
    fclose(f);
}

void annuler_dernier_deplacement(t_Plateau plateau, t_tabDeplacement t,
                                 int *nb, int *coups) {
    if (*nb <= 0) { return; }
    char last = t[(*nb) - 1];
    int lig, col;
    // Find player
    trouver_joueur(plateau, &lig, &col);

    // For each move code, undo the move. Uppercase means a push occurred.
    switch (last) {
        case 'g': // left
            // move player right
            if (col + 1 < TAILLE) {
                // simple move back
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig][col+1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig][col+1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
            }
            break;
        case 'h': // up
            if (lig + 1 < TAILLE) {
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig+1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig+1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
            }
            break;
        case 'b': // down
            if (lig - 1 >= 0) {
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig-1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig-1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
            }
            break;
        case 'd': // right
            if (col - 1 >= 0) {
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig][col-1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig][col-1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
            }
            break;
        case 'G': // push left
            // player moved left and pushed a box; undo: move player right and move box left
            if (col + 1 < TAILLE) {
                // move player back
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig][col+1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig][col+1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
                // move box back to original position
                if (col - 1 >= 0) {
                    // current box is at col-1 after push; move it to col
                    if (plateau[lig][col-1] == CHAR_CAISSE) {
                        plateau[lig][col] = CHAR_CAISSE;
                        plateau[lig][col-1] = CHAR_VIDE;
                    } else if (plateau[lig][col-1] == CHAR_CAISSE_CIBLE) {
                        plateau[lig][col] = CHAR_CAISSE_CIBLE;
                        plateau[lig][col-1] = CHAR_CIBLE;
                    }
                }
            }
            break;
        case 'H': // push up
            if (lig + 1 < TAILLE) {
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig+1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig+1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
                if (lig - 1 >= 0) {
                    if (plateau[lig-1][col] == CHAR_CAISSE) {
                        plateau[lig][col] = CHAR_CAISSE;
                        plateau[lig-1][col] = CHAR_VIDE;
                    } else if (plateau[lig-1][col] == CHAR_CAISSE_CIBLE) {
                        plateau[lig][col] = CHAR_CAISSE_CIBLE;
                        plateau[lig-1][col] = CHAR_CIBLE;
                    }
                }
            }
            break;
        case 'B': // push down
            if (lig - 1 >= 0) {
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig-1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig-1][col] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
                if (lig + 1 < TAILLE) {
                    if (plateau[lig+1][col] == CHAR_CAISSE) {
                        plateau[lig][col] = CHAR_CAISSE;
                        plateau[lig+1][col] = CHAR_VIDE;
                    } else if (plateau[lig+1][col] == CHAR_CAISSE_CIBLE) {
                        plateau[lig][col] = CHAR_CAISSE_CIBLE;
                        plateau[lig+1][col] = CHAR_CIBLE;
                    }
                }
            }
            break;
        case 'D': // push right
            if (col - 1 >= 0) {
                if (plateau[lig][col] == CHAR_JOUEUR) {
                    plateau[lig][col-1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_VIDE;
                } else if (plateau[lig][col] == CHAR_JOUEUR_CIBLE) {
                    plateau[lig][col-1] = CHAR_JOUEUR;
                    plateau[lig][col] = CHAR_CIBLE;
                }
                if (col + 1 < TAILLE) {
                    if (plateau[lig][col+1] == CHAR_CAISSE) {
                        plateau[lig][col] = CHAR_CAISSE;
                        plateau[lig][col+1] = CHAR_VIDE;
                    } else if (plateau[lig][col+1] == CHAR_CAISSE_CIBLE) {
                        plateau[lig][col] = CHAR_CAISSE_CIBLE;
                        plateau[lig][col+1] = CHAR_CIBLE;
                    }
                }
            }
            break;
        default:
            break;
    }

    // remove last move from history
    (*nb)--;
    if (*coups > 0) { (*coups)--; }
}

/**
* @brief Vérifie si le joueur a gagné la partie
* @param plateau Le plateau de jeu
* @param fichier Le nom du fichier de sauvegarde
* @return bool : true si l'utilisateur a gagné, false sinon
*/
bool gagne(t_Plateau plateau) {
    for (int i = 0 ; i < TAILLE ; i++) {
        for (int j = 0 ; j < TAILLE ; j++) {
            if (plateau[i][j] == CHAR_CAISSE) { // Si une caisse n'est pas sur une cible, partie pas gagnée
                return false;
            }
        }
    }
    return true; // Sinon, partie gagnée
}

/**
* @brief Déplace le joueur dans la direction de la touche tapée
* @param plateau Le plateau de jeu
* @param ligne La ligne actuelle du joueur
* @param colonne La colonne actuelle du joueur
* @param direction La direction dans laquelle s'est déplacé le joueur selon la touche tapée (z, q, s, d)
* @return void
*/
char deplacer(t_Plateau plateau, int ligne, int colonne, char direction) {
    int dLigne = 0;
    int dColonne = 0;
    int newLig;
    int newCol;
    int ligneCaisse;
    int colonneCaisse;
    char cible;
    char cibleCaisse;

    // Déterminer la direction de chaque touche
    switch (direction) {
        case 'z':
            dLigne = -1;
            break;
        case 's':
            dLigne = 1;
            break;
        case 'q':
            dColonne = -1;
            break;
        case 'd':
            dColonne = 1;
            break;
        default:
    }

    newLig = ligne + dLigne; // Nouvelle position du joueur
    newCol = colonne + dColonne;
    cible = plateau[newLig][newCol]; // Comme newLig et newCol sont valides, on peut définir la case de destination

    if (cible == CHAR_MUR) {
        return '\0';
    }

    // Will hold the move code to return
    char moveCode = '\0';

    // Déplacement du joueur sur case vide ou cible
    if (cible == CHAR_VIDE || cible == CHAR_CIBLE) {
        plateau[newLig][newCol] = (cible == CHAR_CIBLE) ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
        // set move code (no push)
        if (direction == 'q') { moveCode = 'g'; }
        else if (direction == 'z') { moveCode = 'h'; }
        else if (direction == 's') { moveCode = 'b'; }
        else if (direction == 'd') { moveCode = 'd'; }
    }

    // Déplacement du joueur quand une caisse doit être poussée
    if (cible == CHAR_CAISSE || cible == CHAR_CAISSE_CIBLE) {
        ligneCaisse = newLig + dLigne;
        colonneCaisse = newCol + dColonne;
        cibleCaisse = plateau[ligneCaisse][colonneCaisse];

        if (cibleCaisse == CHAR_VIDE || cibleCaisse == CHAR_CIBLE) {
            // Déplacer la caisse
            plateau[ligneCaisse][colonneCaisse] = (cibleCaisse == CHAR_CIBLE)
            ? CHAR_CAISSE_CIBLE : CHAR_CAISSE;
            // Déplacer le joueur sur là où était la caisse
            plateau[newLig][newCol] = (cible == CHAR_CAISSE_CIBLE)
            ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
            // set move code (push)
            if (direction == 'q') { moveCode = 'G'; }
            else if (direction == 'z') { moveCode = 'H'; }
            else if (direction == 's') { moveCode = 'B'; }
            else if (direction == 'd') { moveCode = 'D'; }
        } else {
            return '\0'; // On peut pas pousser la caisse
        }
    }
    if (plateau[ligne][colonne] == CHAR_JOUEUR_CIBLE) {
        plateau[ligne][colonne] = CHAR_CIBLE;
    } else if (plateau[ligne][colonne] == CHAR_JOUEUR) {
        plateau[ligne][colonne] = CHAR_VIDE;
    }

    return moveCode;
}