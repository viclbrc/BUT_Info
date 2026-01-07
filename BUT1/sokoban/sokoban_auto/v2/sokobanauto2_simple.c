/**
 * @file sokoban.c
 * @brief Jeu du Sokoban
 * @author Victor CORBEL et Louis RIOUAL / 1B1
 * @version 2.0
 * @date 09/11/2025
 *
 * Programme du jeu du Sokoban.
 * But : pousser toutes les caisses ($) sur les cibles (.).
 * Le joueur contrôle Sokoban (@).
 * 
 *
 * Conventions de codage respectées.
 * 
 */

#include <stdlib.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>
#include <ctype.h>

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

/* Déclaration des fonctions */
void afficher_entete(t_Plateau plateau, char fichier[], int coups);
void afficher_plateau(t_Plateau plateau, char fichier[]);
void charger_partie(t_Plateau plateau, char fichier[]);
void charger_deplacements(t_tabDeplacement t, char fichier[], int * nb);
void trouver_joueur(t_Plateau plateau, int *lig, int *col);
bool gagne(t_Plateau plateau);
char deplacer(t_Plateau plateau, int *lig, int *col, char direction, int *coups);
void optimiserDeplacements(t_tabDeplacement original, int nbOriginal, t_tabDeplacement optimisee, int *nbOptimisee, char fichierNiveau[]);
void enregistrerDeplacements(t_tabDeplacement deplacement, int nb, char fichier[]);
void copierPlateau(t_Plateau source, t_Plateau destination);

int main() {
    t_Plateau plateau;
    t_tabDeplacement tabDeplacement;
    t_tabDeplacement tabDeplacementOptimise;
    char fichier[100];
    char fichierDep[100];
    int coups = 0;
    int depValides = 0;
    int lig;
    int col;
    int nbDeplacement = 0;
    int nbDeplacementOptimise = 0;
    char reponse;

    /* Demander les fichiers */
    printf("Nom du fichier .sok : ");
    scanf("%s", fichier);
    printf("Nom du fichier .dep : ");
    scanf("%s", fichierDep);

    /* Charger la partie et les déplacements */
    charger_partie(plateau, fichier);
    charger_deplacements(tabDeplacement, fichierDep, &nbDeplacement);
    trouver_joueur(plateau, &lig, &col);

    /* Boucle de jeu automatique */
    while (!gagne(plateau) && depValides < nbDeplacement) {
        afficher_entete(plateau, fichier, coups);
        afficher_plateau(plateau, fichier);
        deplacer(plateau, &lig, &col, tabDeplacement[depValides], &coups);
        depValides++;
        usleep(250000); // Pause de 250 ms
    }

    afficher_entete(plateau, fichier, coups);
    afficher_plateau(plateau, fichier);

    if (gagne(plateau)) {
        printf("\nLa suite de deplacements %s est une solution pour la partie %s.\n", fichierDep, fichier);
        printf("Elle contient initialement %d caracteres.\n", nbDeplacement);
        
        /* Optimiser les déplacements */
        optimiserDeplacements(tabDeplacement, nbDeplacement, tabDeplacementOptimise, &nbDeplacementOptimise, fichier);
        
        printf("Apres optimisation elle contient %d caracteres. Souhaitez-vous l'enregistrer (O/N) ? ", nbDeplacementOptimise);
        scanf(" %c", &reponse);
        
        if (reponse == 'O' || reponse == 'o') {
            enregistrerDeplacements(tabDeplacementOptimise, nbDeplacementOptimise, fichierDep);
            printf("Fichier enregistre.\n");
        }
    } else {
        printf("\nLa suite de deplacements %s N'EST PAS une solution pour la partie %s.\n", fichierDep, fichier);
    }

    return EXIT_SUCCESS;
}

/* --- Fonctions utilitaires --- */

void afficher_entete(t_Plateau plateau, char fichier[], int coups) {
    system("clear");
    printf("Partie : %s\n", fichier);
    printf("Deplacements : %d\n", coups);
}

void afficher_plateau(t_Plateau plateau, char fichier[]) {
    char c;
    for (int i = 0 ; i < TAILLE ; i++) {
        for (int j = 0 ; j < TAILLE ; j++) {
            c = plateau[i][j];
            if (c == CHAR_CAISSE_CIBLE) c = CHAR_CAISSE;
            else if (c == CHAR_JOUEUR_CIBLE) c = CHAR_JOUEUR;
            printf("%c ", c);
        }
        printf("\n");
    }
}

void charger_partie(t_Plateau plateau, char fichier[]){
    FILE * f;
    char finDeLigne;

    f = fopen(fichier, "r");
    if (f==NULL){
        printf("ERREUR SUR FICHIER");
        exit(EXIT_FAILURE);
    } else {
        for (int ligne=0 ; ligne<TAILLE ; ligne++){
            for (int colonne=0 ; colonne<TAILLE ; colonne++){
                fread(&plateau[ligne][colonne], sizeof(char), 1, f);
            }
            fread(&finDeLigne, sizeof(char), 1, f);
        }
        fclose(f);
    }
}

void charger_deplacements(t_tabDeplacement t, char fichier[], int * nb){
    FILE * f;
    char dep;
    *nb = 0;

    f = fopen(fichier, "r");
    if (f==NULL){
        printf("FICHIER NON TROUVE\n");
    } else {
        fread(&dep, sizeof(char), 1, f);
        if (feof(f)){
            printf("FICHIER VIDE\n");
        } else {
            while (!feof(f)){
                t[*nb] = dep;
                (*nb)++;
                fread(&dep, sizeof(char), 1, f);
            }
        }
    }
    fclose(f);
}

void trouver_joueur(t_Plateau plateau, int *lig, int *col) {
    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            if (plateau[i][j] == CHAR_JOUEUR || plateau[i][j] == CHAR_JOUEUR_CIBLE) {
                *lig = i;
                *col = j;
                return;
            }
        }
    }
}

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

char deplacer(t_Plateau plateau, int *lig, int *col, char direction, int *coups) {
    int dLigne = 0;
    int dColonne = 0;
    int newLig;
    int newCol;
    int ligneCaisse;
    int colonneCaisse;
    char cible;
    char cibleCaisse;
    bool quitterCible;

    (*coups)++;  // on consomme TOUJOURS une commande

    if (direction == 'g') dColonne = -1;
    else if (direction == 'h') dLigne = -1;
    else if (direction == 'b') dLigne = 1;
    else if (direction == 'd') dColonne = 1;
    else if (direction == 'H') dLigne = -1;
    else if (direction == 'B') dLigne = 1;
    else if (direction == 'G') dColonne = -1;
    else if (direction == 'D') dColonne = 1;
    else {
        (*coups)--; 
        return '\0';
    }

    newLig = *lig + dLigne;
    newCol = *col + dColonne;
    cible = plateau[newLig][newCol];

    if (cible == CHAR_MUR) {
        (*coups)--; 
        return '\0';
    }

    quitterCible = (plateau[*lig][*col] == CHAR_JOUEUR_CIBLE);
    plateau[*lig][*col] = quitterCible ? CHAR_CIBLE : CHAR_VIDE;

    if (cible == CHAR_VIDE || cible == CHAR_CIBLE) {
        plateau[newLig][newCol] = (cible == CHAR_CIBLE) ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
        *lig = newLig; 
        *col = newCol;
        return direction;
    }

    if (cible == CHAR_CAISSE || cible == CHAR_CAISSE_CIBLE) {
        ligneCaisse = newLig + dLigne;
        colonneCaisse = newCol + dColonne;
        cibleCaisse = plateau[ligneCaisse][colonneCaisse];

        if (cibleCaisse == CHAR_VIDE || cibleCaisse == CHAR_CIBLE) {
            plateau[ligneCaisse][colonneCaisse] =
                (cibleCaisse == CHAR_CIBLE) ? CHAR_CAISSE_CIBLE : CHAR_CAISSE;
            plateau[newLig][newCol] =
                (cible == CHAR_CAISSE_CIBLE) ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
            *lig = newLig; 
            *col = newCol;
            return direction;
        }
    }

    plateau[*lig][*col] = quitterCible ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
    (*coups)--;
    return '\0';
}

void copierPlateau(t_Plateau source, t_Plateau destination) {
    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            destination[i][j] = source[i][j];
        }
    }
}

void optimiserDeplacements(t_tabDeplacement original, int nbOriginal, t_tabDeplacement optimisee, int *nbOptimisee, char fichierNiveau[]) {
    t_Plateau plateau;
    t_Plateau testPlateau;
    int ligJoueur, colJoueur;
    int testLig, testCol;
    int i, j, k;
    int compteur;
    
    /* Copier les déplacements valides */
    charger_partie(plateau, fichierNiveau);
    trouver_joueur(plateau, &ligJoueur, &colJoueur);
    
    *nbOptimisee = 0;
    
    for (i = 0; i < nbOriginal; i++) {
        int dummy = 0;
        if (deplacer(plateau, &ligJoueur, &colJoueur, original[i], &dummy) != '\0') {
            optimisee[(*nbOptimisee)++] = original[i];
        }
    }
    
    /* Supprimer les séquences inutiles */
    i = 0;
    while (i < *nbOptimisee) {
        charger_partie(testPlateau, fichierNiveau);
        trouver_joueur(testPlateau, &testLig, &testCol);
        
        /* Appliquer les mouvements jusqu'à i */
        for (j = 0; j < i; j++) {
            int dummy = 0;
            deplacer(testPlateau, &testLig, &testCol, optimisee[j], &dummy);
        }
        
        /* Position de départ de la séquence */
        int startLig = testLig, startCol = testCol;
        
        /* Chercher un cycle : parcourir jusqu'à revenir au point de départ sans bouger de caisse */
        compteur = i;
        bool caisseBougee = false;
        
        while (compteur < *nbOptimisee && !caisseBougee) {
            t_Plateau avant;
            copierPlateau(testPlateau, avant);
            
            int dummy = 0;
            deplacer(testPlateau, &testLig, &testCol, optimisee[compteur], &dummy);
            
            /* Vérifier si une caisse a bougé */
            for (int ii = 0; ii < TAILLE && !caisseBougee; ii++) {
                for (int jj = 0; jj < TAILLE && !caisseBougee; jj++) {
                    char charAvant = avant[ii][jj];
                    char charApres = testPlateau[ii][jj];
                    if ((charAvant == CHAR_CAISSE || charAvant == CHAR_CAISSE_CIBLE) ||
                        (charApres == CHAR_CAISSE || charApres == CHAR_CAISSE_CIBLE)) {
                        if (charAvant != charApres) {
                            caisseBougee = true;
                        }
                    }
                }
            }
            
            compteur++;
        }
        
        /* Si on est au même endroit sans bouger de caisse, c'est une séquence inutile */
        if (!caisseBougee && testLig == startLig && testCol == startCol) {
            int longueur = compteur - i;
            for (k = i; k < *nbOptimisee - longueur; k++) {
                optimisee[k] = optimisee[k + longueur];
            }
            *nbOptimisee -= longueur;
        } else {
            i++;
        }
    }
}

void enregistrerDeplacements(t_tabDeplacement deplacement, int nb, char fichier[]) {
    FILE *f;
    int i;
    
    f = fopen(fichier, "w");
    if (f == NULL) {
        printf("Erreur lors de l'ouverture du fichier.\n");
        return;
    }
    
    for (i = 0; i < nb; i++) {
        fwrite(&deplacement[i], sizeof(char), 1, f);
    }
    
    fclose(f);
}
