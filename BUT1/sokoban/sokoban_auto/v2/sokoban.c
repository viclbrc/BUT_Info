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

#define TAILLE 12
#define CHAR_VIDE ' '
#define CHAR_MUR '#'
#define CHAR_CAISSE '$'
#define CHAR_CIBLE '.'
#define CHAR_CAISSE_CIBLE '*'
#define CHAR_JOUEUR '@'
#define CHAR_JOUEUR_CIBLE '+'
#define DEPLACEMENT_GAUCHE 'g'
#define DEPLACEMENT_DROITE 'd'
#define DEPLACEMENT_HAUT 'h'
#define DEPLACEMENT_BAS 'b'
#define DEPLACEMENT_CAISSE_GAUCHE 'G'
#define DEPLACEMENT_CAISSE_DROITE 'D'
#define DEPLACEMENT_CAISSE_HAUT 'H'
#define DEPLACEMENT_CAISSE_BAS 'B'
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
void optimiser_deplacements(t_tabDeplacement original, int nbOriginal, t_tabDeplacement optimisee, int *nbOptimisee, char fichierNiveau[]);
void enregistrer_deplacements(t_tabDeplacement deplacement, int nb, char fichier[]);
void copier_plateau(t_Plateau source, t_Plateau destination);
bool caisses_identiques(t_Plateau p1, t_Plateau p2);

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

    printf("Nom du fichier .sok : ");
    scanf("%s", fichier);
    printf("Nom du fichier .dep : ");
    scanf("%s", fichierDep);
    
    charger_partie(plateau, fichier);
    charger_deplacements(tabDeplacement, fichierDep, &nbDeplacement);
    
    optimiser_deplacements(tabDeplacement, nbDeplacement, tabDeplacementOptimise, &nbDeplacementOptimise, fichier);
    
    trouver_joueur(plateau, &lig, &col);
    
    while (!gagne(plateau) && depValides < nbDeplacementOptimise) {
        afficher_entete(plateau, fichier, coups);
        afficher_plateau(plateau, fichier);
        deplacer(plateau, &lig, &col, tabDeplacementOptimise[depValides], &coups);
        depValides++;
        usleep(250000);
    }

    afficher_entete(plateau, fichier, coups);
    afficher_plateau(plateau, fichier);

    if (gagne(plateau) || depValides >= nbDeplacementOptimise) {
        printf("\nLa suite de déplacements optimisée est une solution pour la partie %s.\n", fichier);
        printf("La suite de déplacements %s contient initialement %d caractères.\n", fichierDep, nbDeplacement);
        printf("Après optimisation elle contient %d caractères.\n", nbDeplacementOptimise);
        printf("\nSouhaitez-vous enregistrer la version optimisée (O/N) ? ");
        scanf(" %c", &reponse);
        
        if (reponse == 'O' || reponse == 'o') {
            enregistrer_deplacements(tabDeplacementOptimise, nbDeplacementOptimise, fichierDep);
            printf("Fichier enregistre.\n");
        }
    } else {
        printf("\nLa suite de déplacements optimisée n'est pas une solution pour la partie %s.\n", fichier);
    }

    return EXIT_SUCCESS;
}


void afficher_entete(t_Plateau plateau, char fichier[], int coups) {
    system("clear");
    printf("Partie : %s\n", fichier);
    printf("Déplacements : %d\n", coups);
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
    bool estMajuscule;

    (*coups)++;  // on consomme TOUJOURS une commande

    if (direction == 'g') { dColonne = -1; estMajuscule = false; }
    else if (direction == 'h') { dLigne = -1; estMajuscule = false; }
    else if (direction == 'b') { dLigne = 1; estMajuscule = false; }
    else if (direction == 'd') { dColonne = 1; estMajuscule = false; }
    else if (direction == 'H') { dLigne = -1; estMajuscule = true; }
    else if (direction == 'B') { dLigne = 1; estMajuscule = true; }
    else if (direction == 'G') { dColonne = -1; estMajuscule = true; }
    else if (direction == 'D') { dColonne = 1; estMajuscule = true; }
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
    
    // Vérifie si c'est une caisse
    bool estCaisse = (cible == CHAR_CAISSE || cible == CHAR_CAISSE_CIBLE);
    bool estVide = (cible == CHAR_VIDE || cible == CHAR_CIBLE);
    
    // Si caisse et minuscule -> pas de déplacement
    if (estCaisse && !estMajuscule) {
        (*coups)--;
        return '\0';
    }
    
    // Si vide et majuscule -> pas de déplacement
    if (estVide && estMajuscule) {
        (*coups)--;
        return '\0';
    }

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

void copier_plateau(t_Plateau source, t_Plateau destination) {
    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            destination[i][j] = source[i][j];
        }
    }
}

bool caisses_identiques(t_Plateau p1, t_Plateau p2) {
    for (int i = 0; i < TAILLE; i++) {
        for (int j = 0; j < TAILLE; j++) {
            char c1 = p1[i][j];
            char c2 = p2[i][j];
            
            bool caisse1 = (c1 == CHAR_CAISSE || c1 == CHAR_CAISSE_CIBLE);
            bool caisse2 = (c2 == CHAR_CAISSE || c2 == CHAR_CAISSE_CIBLE);
            
            if (caisse1 != caisse2) {
                return false;
            }
        }
    }
    return true;
}

void optimiser_deplacements(t_tabDeplacement original, int nbOriginal, t_tabDeplacement optimisee, int *nbOptimisee, char fichierNiveau[]) {
    t_Plateau plateau;
    t_Plateau plateauApresPoussee;
    int ligJoueur;
    int colJoueur;
    int ligApresPoussee;
    int colApresPoussee;
    int indApresPoussee;
    int compteur;
    char resultat;
    
    charger_partie(plateau, fichierNiveau);
    trouver_joueur(plateau, &ligJoueur, &colJoueur);
    
    *nbOptimisee = 0;
    indApresPoussee = -1;  // Indique qu'aucune caisse n'a été poussée récemment
    
    for (int i = 0; i < nbOriginal; i++) {
        compteur = 0;
        
        // Sauvegarde l'état avant le déplacement
        copier_plateau(plateau, plateauApresPoussee);

        // Tente le déplacement
        resultat = deplacer(plateau, &ligJoueur, &colJoueur, original[i], &compteur);
        
        // Si le déplacement est pas possible (mur), ne pas le compter
        if (resultat == '\0') {
            continue;
        }
        
        // Vérifie si une caisse a été déplacée
        if (!caisses_identiques(plateauApresPoussee, plateau)) {
            // Si une caisse a été poussée
            optimisee[(*nbOptimisee)++] = original[i];
            
            // Enregistre la position de Sokoban après cette poussée
            ligApresPoussee = ligJoueur;
            colApresPoussee = colJoueur;
            indApresPoussee = *nbOptimisee - 1;
            copier_plateau(plateau, plateauApresPoussee);
        } else {
            // Si aucune caisse n'a été poussée = déplacement
            
            // Si on est revenu à la position après la dernière poussée
            if (indApresPoussee != -1 && 
                ligJoueur == ligApresPoussee && 
                colJoueur == colApresPoussee &&
                caisses_identiques(plateauApresPoussee, plateau)) {
                
                // Supprime tous les déplacements depuis la dernière poussée
                *nbOptimisee = indApresPoussee + 1;
            } else {
                // Ajoute le déplacement
                optimisee[(*nbOptimisee)++] = original[i];
            }
        }
    }
}

void enregistrer_deplacements(t_tabDeplacement deplacement, int nb, char fichier[]) {
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