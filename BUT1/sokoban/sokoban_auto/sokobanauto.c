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
 * Fonctionnalités ajoutées :
 * -  avant/arrière (+ / -) entre 1 et 3.
 * - Annulation du dernier déplacement (u).
 *
 * Conventions de codage respectées.
 * 
 * Niveaux supplémentaires créés par la communauté disponibles sur : https://github.com/NatinouTresor/Sokoban-Niveaux
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
#define _MIN 1
#define _MAX 3

/* Déclaration des fonctions */
void afficher_entete(t_Plateau plateau, char fichier[], int coups);
void afficher_plateau(t_Plateau plateau, char fichier[]);
void charger_partie(t_Plateau plateau, char fichier[]);
void charger_deplacements(t_tabDeplacement t, char fichier[], int * nb);
void trouver_joueur(t_Plateau plateau, int *lig, int *col);
bool gagne(t_Plateau plateau);
char deplacer(t_Plateau plateau, int lig, int col, char direction);

int main() {
    t_Plateau plateau;
    t_tabDeplacement tabDeplacement;
    char fichier[100];
    char fichierDep[100];
    char reponse;
    int coups = 0;
    int lig;
    int col;
    
    int nbDeplacement = 0;
    char codeDeplacement;

    /* Afficher l'entête et demander le fichier */
    afficher_entete(plateau, fichier, coups);
    printf("Quel fichier ? ");
    scanf("%s", fichier);
    getchar();
    printf("\nQuel fichier de déplacements ? ");
    scanf("%s", fichierDep);
    getchar();

    /* Charger et afficher la partie */
    charger_partie(plateau, fichier);
    charger_deplacements(tabDeplacement, fichierDep, &nbDeplacement);
    afficher_entete(plateau, fichier, coups);
    afficher_plateau(plateau, fichier);

    /* Boucle de jeu */
    while (!gagne(plateau)) { // Tant que la partie n'est pas gagnée et pas abandonnée
        afficher_entete(plateau, fichier, coups);
        afficher_plateau(plateau, fichier);
        deplacer(plateau, lig, col, tabDeplacement[coups]);
        coups++;
    }

    

    /* Fin de partie */
    if (gagne(plateau) == true) {
        printf("\nLa suite de déplacements <fichier.dep> est une solution pour la partie <fichier.sok>.\n");
        printf("Elle contient %d déplacements.\n", coups);
    } else {
        printf("\nLa suite de déplacements <fichier.dep> n'est pas une solution pour la partie <fichier.sok>.\n");
        }

    return EXIT_SUCCESS;
}

/**
 * @brief Affiche l'entête du jeu
 * @param plateau Le plateau de jeu
 * @param fichier Le nom du fichier chargé
 * @param coups Le nombre de coups effectués
 * @param  Le niveau de 
 * @return void
 */
void afficher_entete(t_Plateau plateau, char fichier[], int coups) {
    system("clear");
    printf("Partie : %s\n", fichier);
    printf("Deplacements : %d\n", coups);
}

/**
 * @brief Affiche le plateau avec 
 * @param plateau Le plateau de jeu
 * @param fichier Le nom du fichier (non utilisé)
 * @param  Le niveau de  (1 à 3)
 * @return void
 */
void afficher_plateau(t_Plateau plateau, char fichier[]) {
    char c;
    for (int i = 0 ; i < TAILLE ; i++) {
        for (int j = 0 ; j < TAILLE ; j++) {
            c = plateau[i][j];
            // Change pas le caractère quand le joueur ou une caisse est sur une cible
            if (c == CHAR_CAISSE_CIBLE) {
                c = CHAR_CAISSE;
            } else if (c == CHAR_JOUEUR_CIBLE) {
                c = CHAR_JOUEUR;
            }
            printf("%c ", c);
        }
        printf("\n");
    }
}

/**
 * @brief Charge le plateau depuis un fichier
 * @param plateau Le plateau à remplir
 * @param fichier Le nom du fichier à charger
 * @return void
 */
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
            if (plateau[i][j] == CHAR_JOUEUR || plateau[i][j] == CHAR_JOUEUR_CIBLE) { // Si le joueur est trouvé sur le plateau
                *lig = i;
                *col = j;
            }
        }
    }
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

    // Déterminer la direction de chaque touche
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

    newLig = lig + dLigne; // Nouvelle position du joueur
    newCol = col + dColonne;
    cible = plateau[newLig][newCol];
    if (cible == CHAR_MUR) return '\0';

    quitterCible = (plateau[lig][col] == CHAR_JOUEUR_CIBLE);
    codeDeplacement = '\0';
    plateau[lig][col] = quitterCible ? CHAR_CIBLE : CHAR_VIDE;

    if (cible == CHAR_VIDE || cible == CHAR_CIBLE) {
        plateau[newLig][newCol] = (cible == CHAR_CIBLE) ?
                                  CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
        codeDeplacement = quitterCible ? (char)(codeSimple - 32) : codeSimple;
    } else if (cible == CHAR_CAISSE || cible == CHAR_CAISSE_CIBLE) {
        ligneCaisse = newLig + dLigne;
        colonneCaisse = newCol + dColonne;
        cibleCaisse = plateau[ligneCaisse][colonneCaisse];
        if (cibleCaisse == CHAR_VIDE || cibleCaisse == CHAR_CIBLE) {
            plateau[ligneCaisse][colonneCaisse] =
                (cibleCaisse == CHAR_CIBLE) ? CHAR_CAISSE_CIBLE : CHAR_CAISSE;
            plateau[newLig][newCol] = (cible == CHAR_CAISSE_CIBLE) ?
                                      CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
            codeDeplacement = quitterCible ? (char)(codePousse - 32) : codePousse;
        } else {
            plateau[lig][col] = quitterCible ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
            return '\0';
        }
    }

    return codeDeplacement;
}

