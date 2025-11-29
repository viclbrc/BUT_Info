/**
 * @file sokoban.c
 * @brief Jeu du Sokoban
 * @author Victor CORBEL / 1B1
 * @version 2.0
 * @date 09/11/2025
 *
 * Programme du jeu du Sokoban.
 * But : pousser toutes les caisses ($) sur les cibles (.).
 * Le joueur contrôle Sokoban (@).
 *
 * Conventions de codage respectées.
 */

#include <stdlib.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>

/* Déclaration des constantes */
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

/* Déclaration des fonctions */
void afficher_entete(t_Plateau plateau, char fichier[], int coups, int zoom);
void afficher_plateau(t_Plateau plateau, char fichier[], int zoom);
void charger_partie(t_Plateau plateau, char fichier[]);
int kbhit();
void trouver_joueur(t_Plateau plateau, int *lig, int *col);
void enregistrer_partie(t_Plateau plateau, char fichier[]);
bool gagne(t_Plateau plateau);
char deplacer(t_Plateau plateau, int lig, int col, char direction);
void annuler_dernier_deplacement(t_Plateau plateau, t_tabDeplacement tabDeplacement, int *nbDeplacement, int *coups);
void enregistrer_deplacements(t_tabDeplacement tabDeplacement, int nbDeplacement, char fichier[]);

int main() {
    t_Plateau plateau;
    t_tabDeplacement tabDeplacement;
    char fichier[100];
    bool estAbandonee = false;
    char touche = '\0';
    char reponse;
    int coups = 0;
    int lig;
    int col;
    int zoom = 1;
    
    int nbDeplacement = 0;
    char codeDeplacement;

    /* Afficher l'entête et demander le fichier */
    afficher_entete(plateau, fichier, coups, zoom);
    printf("Quel fichier ? ");
    scanf("%s", fichier);
    getchar();

    /* Charger et afficher la partie */
    charger_partie(plateau, fichier);
    afficher_entete(plateau, fichier, coups, zoom);
    afficher_plateau(plateau, fichier, zoom);

    /* Boucle de jeu */
    while (!gagne(plateau) && !estAbandonee) {
        afficher_entete(plateau, fichier, coups, zoom);
        afficher_plateau(plateau, fichier, zoom);

        /* Attendre une touche */
        while (kbhit() == 0) {
        }
        touche = getchar();

        switch (touche) {
        case 'z':
        case 's':
        case 'q':
        case 'd':
            trouver_joueur(plateau, &lig, &col);
            codeDeplacement = deplacer(plateau, lig, col, touche);
            if (codeDeplacement != '\0' &&
                nbDeplacement < MAX_DEPLACEMENTS) {
                tabDeplacement[nbDeplacement++] = codeDeplacement;
                coups++;
            }
            break;
        case '+':
            if (zoom < ZOOM_MAX) {
                zoom++;
            }
            break;
        case '-':
            if (zoom > ZOOM_MIN) {
                zoom--;
            }
            break;
        case 'u':
            annuler_dernier_deplacement(plateau, tabDeplacement,
                                        &nbDeplacement, &coups);
            break;
        case 'r':
            printf("Voulez-vous vraiment recommencer ? (o/n) : ");
            scanf("%c", &reponse);
            if (reponse == 'o') {
                charger_partie(plateau, fichier);
                coups = 0;
                nbDeplacement = 0;
                zoom = 1;
                afficher_entete(plateau, fichier, coups, zoom);
                afficher_plateau(plateau, fichier, zoom);
            }
            break;
        case 'x':
            estAbandonee = true;
            break;
        default:
            break;
        }
    }

    /* Fin de partie */
    if (gagne(plateau) == true) {
        printf("\nBravo ! Niveau terminé en %d coups.\n", coups);
        printf("Voulez-vous sauvegarder les déplacements ? (o/n) : ");
        scanf(" %c", &reponse);
        if (reponse == 'o') {
            printf("Nom du fichier de sauvegarde (.dep) : ");
            scanf("%s", fichier);
            enregistrer_deplacements(tabDeplacement,
                                     nbDeplacement, fichier);
            printf("Partie sauvegardée.\n");
        }
    } else if (estAbandonee) {
        printf("\nPartie abandonnée après %d coups.\n", coups);
        printf("Voulez-vous sauvegarder la partie ? (o/n) : ");
        scanf(" %c", &reponse);
        if (reponse == 'o') {
            printf("Nom du fichier de sauvegarde (.sok) : ");
            scanf("%s", fichier);
            enregistrer_partie(plateau, fichier);
            printf("Partie sauvegardée.\n");
        }
        printf("Voulez-vous sauvegarder les déplacements ? (o/n) : ");
        scanf(" %c", &reponse);
        if (reponse == 'o') {
            printf("Nom du fichier de sauvegarde (.dep) : ");
            scanf("%s", fichier);
            enregistrer_deplacements(tabDeplacement,
                                     nbDeplacement, fichier);
            printf("Partie sauvegardée.\n");
        }
    }

    return EXIT_SUCCESS;
}

/**
 * @brief Affiche l'entête du jeu
 * @param plateau Le plateau de jeu
 * @param fichier Le nom du fichier chargé
 * @param coups Le nombre de coups effectués
 * @param zoom Le niveau de zoom
 * @return void
 */
void afficher_entete(t_Plateau plateau, char fichier[], int coups, int zoom) {
    system("clear");
    printf("Partie : %s\n", fichier);
    printf("Touches : z (haut), s (bas), q (gauche), d (droite)\n");
    printf("         x (abandon), r (recommencer)\n");
    printf("+ (zoom) - (dezoom) u (undo)\n");
    printf("Deplacements : %d | Zoom : %d\n", coups, zoom);
}

/**
 * @brief Affiche le plateau avec zoom
 * @param plateau Le plateau de jeu
 * @param fichier Le nom du fichier (non utilisé)
 * @param zoom Le niveau de zoom (1 à 3)
 * @return void
 */
void afficher_plateau(t_Plateau plateau, char fichier[], int zoom) {
    char c;
    for (int i = 0; i < TAILLE; i++) {
        for (int repVerticale = 0; repVerticale < zoom; repVerticale++) {
            for (int j = 0; j < TAILLE; j++) {
                c = plateau[i][j];
                if (c == CHAR_CAISSE_CIBLE) {
                    c = CHAR_CAISSE;
                } else if (c == CHAR_JOUEUR_CIBLE) {
                    c = CHAR_JOUEUR;
                }
                for (int repHorizontale = 0; repHorizontale < zoom;
                     repHorizontale++) {
                    printf("%c", c);
                }
            }
            printf("\n");
        }
    }
}

/**
 * @brief Charge le plateau depuis un fichier
 * @param plateau Le plateau à remplir
 * @param fichier Le nom du fichier à charger
 * @return void
 */
void charger_partie(t_Plateau plateau, char fichier[]) {
    FILE *f = fopen(fichier, "r");
    if (!f) {
        printf("ERREUR SUR FICHIER");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < TAILLE; i++) {
        fread(plateau[i], sizeof(char), TAILLE, f);
        fseek(f, 1, SEEK_CUR);
    }
    fclose(f);
}

/**
 * @brief Détecte si une touche a été pressée
 * @return int : 1 si oui, 0 sinon
 */
int kbhit(void) {
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
 * @brief Trouve la position du joueur
 * @param plateau Le plateau de jeu
 * @param ligne Pointeur pour la ligne
 * @param colonne Pointeur pour la colonne
 * @return void
 */
void trouver_joueur(t_Plateau plateau, int *lig, int *col) {

    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            if (plateau[i][j] == CHAR_JOUEUR ||
                plateau[i][j] == CHAR_JOUEUR_CIBLE) {
                *lig = i;
                *col = j;
            }
        }
    }
}

/**
 * @brief Enregistre la partie dans un fichier
 * @param plateau Le plateau à sauvegarder
 * @param fichier Le nom du fichier
 * @return void
 */
void enregistrer_partie(t_Plateau plateau, char fichier[]) {
    FILE *f = fopen(fichier, "w");
    char finDeLigne = '\n';

    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            fwrite(&plateau[i][j], sizeof(char), 1, f);
        }
        fwrite(&finDeLigne, sizeof(char), 1, f);
    }
    fclose(f);
}

/**
 * @brief Enregistre les déplacements dans un fichier
 * @param tabDeplacement Le tableau des déplacements
 * @param nbDeplacement Le nombre de déplacements
 * @param fichier Le nom du fichier
 * @return void
 */
void enregistrer_deplacements(t_tabDeplacement tabDeplacement, int nbDeplacement, char fichier[]) {
    FILE *f = fopen(fichier, "w");
    fwrite(tabDeplacement, sizeof(char), nbDeplacement, f);
    fclose(f);
}

/**
 * @brief Annule le dernier déplacement
 * @param plateau Le plateau à modifier
 * @param tabDeplacement Le tableau des déplacements
 * @param nbDeplacement Pointeur du nombre de déplacements
 * @param coups Pointeur du nombre de coups
 * @return void
 */
void annuler_dernier_deplacement(t_Plateau plateau, t_tabDeplacement tabDeplacement, int *nbDeplacement, int *coups) {
    char code = tabDeplacement[(*nbDeplacement) - 1];
    int dLigne = 0;
    int dColonne = 0;
    int lig;
    int col;
    int ancienneLigne;
    int ancienneColonne;
    int ligneCaisse;
    int colonneCaisse;
    char anciennePos;
    char enPos;

    if (*nbDeplacement <= 0) return;
    trouver_joueur(plateau, &lig, &col);

    if (code == 'g' || code == 'G') dColonne = -1;
    else if (code == 'h' || code == 'H') dLigne = -1;
    else if (code == 'b' || code == 'B') dLigne = 1;
    else if (code == 'd' || code == 'D') dColonne = 1;

    ancienneLigne = lig - dLigne;
    ancienneColonne = col - dColonne;
    anciennePos = CHAR_JOUEUR;
    if (ancienneLigne >= 0 && ancienneLigne < TAILLE && ancienneColonne >= 0 && ancienneColonne < TAILLE) {
        if (plateau[ancienneLigne][ancienneColonne] == CHAR_CIBLE) anciennePos = CHAR_JOUEUR_CIBLE;
        plateau[ancienneLigne][ancienneColonne] = anciennePos;
    }

    plateau[lig][col] = (plateau[lig][col] == CHAR_JOUEUR_CIBLE) ?
                        CHAR_CIBLE : CHAR_VIDE;

    if (code >= 'A' && code <= 'Z') { // si c'est une majuscule = si caisse poussée
        ligneCaisse = lig + dLigne;
        colonneCaisse = col + dColonne;
        if (ligneCaisse >= 0 && ligneCaisse < TAILLE && colonneCaisse >= 0 && colonneCaisse < TAILLE) {
            enPos = plateau[ligneCaisse][colonneCaisse];
            if (enPos == CHAR_CAISSE) {
                plateau[lig][col] = CHAR_CAISSE;
                plateau[ligneCaisse][colonneCaisse] = CHAR_VIDE;
            } else if (enPos == CHAR_CAISSE_CIBLE) {
                plateau[lig][col] = CHAR_CAISSE_CIBLE;
                plateau[ligneCaisse][colonneCaisse] = CHAR_CIBLE;
            }
        }
    }

    (*nbDeplacement)--;
    if (*coups > 0) (*coups)--;
}

/**
 * @brief Vérifie si la partie est gagnée
 * @param plateau Le plateau à vérifier
 * @return bool : true si gagné, false sinon
 */
bool gagne(t_Plateau plateau) {
    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            if (plateau[i][j] == CHAR_CAISSE) {
                return false;
            }
        }
    }
    return true;
}

/**
 * @brief Déplace le joueur et enregistre le code
 * @param plateau Le plateau
 * @param ligne La ligne actuelle
 * @param colonne La colonne actuelle
 * @param direction La direction (z/s/q/d)
 * @return char : code du déplacement ou '\0'
 */
char deplacer(t_Plateau plateau, int lig, int col, char direction) {
    int dLigne = 0;
    int dColonne = 0;
    int newLig;
    int newCol;
    int ligneCaisse;
    int colonneCaisse;
    bool quitterCible;
    char cible;
    char cibleCaisse;
    char codeDeplacement;
    char codeSimple = '\0';
    char codePousse = '\0';

    if (direction == 'q') {
        dColonne = -1;
        codeSimple = 'g';
        codePousse = 'G';
    } else if (direction == 'z') {
        dLigne = -1;
        codeSimple = 'h';
        codePousse = 'H';
    } else if (direction == 's') {
        dLigne = 1;
        codeSimple = 'b';
        codePousse = 'B';
    } else if (direction == 'd') {
        dColonne = 1;
        codeSimple = 'd';
        codePousse = 'D';
    } else {
        return '\0';
    }

    newLig = lig + dLigne;
    newCol = col + dColonne;
    cible = plateau[newLig][newCol];
    if (cible == CHAR_MUR) return '\0';

    quitterCible = (plateau[lig][col] == CHAR_JOUEUR_CIBLE);
    codeDeplacement = '\0';
    plateau[lig][col] = quitterCible ? CHAR_CIBLE : CHAR_VIDE;

    if (cible == CHAR_VIDE || cible == CHAR_CIBLE) {
        plateau[newLig][newCol] = (cible == CHAR_CIBLE) ?
                                  CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
        codeDeplacement = quitterCible ? (char)(codeSimple - 32) :
                                   codeSimple;
    } else if (cible == CHAR_CAISSE || cible == CHAR_CAISSE_CIBLE) {
        ligneCaisse = newLig + dLigne;
        colonneCaisse = newCol + dColonne;
        cibleCaisse = plateau[ligneCaisse][colonneCaisse];
        if (cibleCaisse == CHAR_VIDE || cibleCaisse == CHAR_CIBLE) {
            plateau[ligneCaisse][colonneCaisse] =
                (cibleCaisse == CHAR_CIBLE) ? CHAR_CAISSE_CIBLE :
                                              CHAR_CAISSE;
            plateau[newLig][newCol] = (cible == CHAR_CAISSE_CIBLE) ?
                                      CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
            codeDeplacement = quitterCible ? (char)(codePousse - 32) :
                                       codePousse;
        } else {
            plateau[lig][col] = quitterCible ?
                                CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
            return '\0';
        }
    }

    return codeDeplacement;
}
