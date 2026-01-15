/**
* @file sokoban.c
* @brief Jeu du Sokoban - Version 2 avec optimisation
* @author Victor CORBEL et Louis RIOUAL / 1B1
* @version 2.0
* @date 09/11/2025
*
* Programme du jeu du Sokoban.
* But : pousser toutes les caisses ($) sur les cibles (.).
* Le joueur contrôle Sokoban (@).
*
* Version 2 : Suppression des séquences inutiles
*/

#include <stdlib.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>

/* Déclaration des constantes */
#define TAILLE 12
#define CHAR_VIDE ' '
#define CHAR_MUR '#'
#define CHAR_CAISSE '$'
#define CHAR_CIBLE '.'
#define CHAR_CAISSE_CIBLE '*'
#define CHAR_JOUEUR '@'
#define CHAR_JOUEUR_CIBLE '+'
#define MAX_DEPLACEMENTS 1000

typedef char t_Plateau[TAILLE][TAILLE];
typedef char t_tabDeplacement[MAX_DEPLACEMENTS];

typedef struct {
    int ligne;
    int colonne;
} t_Position;

typedef struct {
    t_Position position;
    int indexDebut;
} t_Historique;

typedef enum {
    DEPLACEMENT_IMPOSSIBLE,
    DEPLACEMENT_SIMPLE,
    DEPLACEMENT_AVEC_CAISSE
} t_TypeDeplacement;

/* Déclaration des fonctions */
void afficher_entete(char fichier[], int numDeplacement);
void afficher_plateau(t_Plateau plateau);
void charger_partie(t_Plateau plateau, char fichier[]);
void charger_deplacements(t_tabDeplacement t, char fichier[], int * nb);
void trouver_joueur(t_Plateau plateau, int *lig, int *col);
bool gagne(t_Plateau plateau);
t_TypeDeplacement deplacer(t_Plateau plateau, int *lig, int *col, char direction);
int optimiser_deplacements(t_tabDeplacement depOriginaux, int nbOriginal, 
                           t_tabDeplacement depOptimises, char fichierSok[]);
void sauvegarder_deplacements(t_tabDeplacement deplacements, int nb, char fichier[]);
bool positions_egales(t_Position p1, t_Position p2);

int main() {
    t_Plateau plateau;
    t_tabDeplacement tabDeplacement;
    t_tabDeplacement tabDeplacementOptimise;
    char fichier[100];
    char fichierDep[100];
    char fichierSauvegarde[100];
    char reponse;
    int indexDep = 0;
    int coupsJoues = 0;
    int lig;
    int col;
    int nbDeplacement = 0;
    int nbDeplacementOptimise = 0;

    /* 1. Demander le fichier .sok */
    printf("Nom du fichier .sok : ");
    scanf("%s", fichier);
    
    /* 2. Demander le fichier .dep */
    printf("Nom du fichier .dep : ");
    scanf("%s", fichierDep);

    /* Charger la partie et les déplacements */
    charger_partie(plateau, fichier);
    charger_deplacements(tabDeplacement, fichierDep, &nbDeplacement);
    trouver_joueur(plateau, &lig, &col);

    /* 3. Faire jouer la partie avec affichage */
    while (!gagne(plateau) && indexDep < nbDeplacement) {
        t_TypeDeplacement type = deplacer(plateau, &lig, &col, tabDeplacement[indexDep]);
        
        /* Afficher seulement si le déplacement a été effectué */
        if (type != DEPLACEMENT_IMPOSSIBLE) {
            coupsJoues++;
            afficher_entete(fichier, coupsJoues);
            afficher_plateau(plateau);
            usleep(250000); /* Pause de 250 ms pour suivre facilement */
        }
        
        indexDep++;
    }

    /* Affichage final */
    afficher_entete(fichier, coupsJoues);
    afficher_plateau(plateau);

    /* 4. Afficher le bilan */
    if (gagne(plateau)) {
        printf("\nLa suite <%s> est bien une solution pour le tableau <%s>.\n", 
               fichierDep, fichier);
        printf("Elle contient initialement %d caracteres.\n", nbDeplacement);
        
        /* Optimisation : suppression des déplacements non joués et séquences inutiles */
        nbDeplacementOptimise = optimiser_deplacements(tabDeplacement, nbDeplacement, 
                                                       tabDeplacementOptimise, fichier);
        
        printf("Apres optimisation elle contient %d caracteres.\n", nbDeplacementOptimise);
        
        /* Demander si l'utilisateur veut sauvegarder */
        printf("Souhaitez-vous l'enregistrer (O/N) ? ");
        scanf(" %c", &reponse);
        
        if (reponse == 'O' || reponse == 'o') {
            printf("Nom du fichier de sauvegarde : ");
            scanf("%s", fichierSauvegarde);
            sauvegarder_deplacements(tabDeplacementOptimise, nbDeplacementOptimise, fichierSauvegarde);
            printf("Suite optimisee sauvegardee dans %s\n", fichierSauvegarde);
        }
    } else {
        printf("\nLa suite de deplacements <%s> N'EST PAS une solution pour le tableau <%s>.\n", 
               fichierDep, fichier);
    }

    return EXIT_SUCCESS;
}

void afficher_entete(char fichier[], int numDeplacement) {
    system("clear");
    printf("Partie : %s\n", fichier);
    printf("Deplacement : %d\n", numDeplacement);
}

void afficher_plateau(t_Plateau plateau) {
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
        exit(EXIT_FAILURE);
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

t_TypeDeplacement deplacer(t_Plateau plateau, int *lig, int *col, char direction) {
    int dLigne = 0;
    int dColonne = 0;
    int newLig;
    int newCol;
    int ligneCaisse;
    int colonneCaisse;
    char cible;
    char cibleCaisse;
    bool quitterCible;

    if (direction == 'g') dColonne = -1;
    else if (direction == 'h') dLigne = -1;
    else if (direction == 'b') dLigne = 1;
    else if (direction == 'd') dColonne = 1;
    else if (direction == 'H') dLigne = -1;
    else if (direction == 'B') dLigne = 1;
    else if (direction == 'G') dColonne = -1;
    else if (direction == 'D') dColonne = 1;
    else {
        return DEPLACEMENT_IMPOSSIBLE;
    }

    newLig = *lig + dLigne;
    newCol = *col + dColonne;
    cible = plateau[newLig][newCol];

    if (cible == CHAR_MUR) {
        return DEPLACEMENT_IMPOSSIBLE;
    }

    quitterCible = (plateau[*lig][*col] == CHAR_JOUEUR_CIBLE);
    plateau[*lig][*col] = quitterCible ? CHAR_CIBLE : CHAR_VIDE;

    if (cible == CHAR_VIDE || cible == CHAR_CIBLE) {
        plateau[newLig][newCol] = (cible == CHAR_CIBLE) ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
        *lig = newLig; 
        *col = newCol;
        return DEPLACEMENT_SIMPLE;
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
            return DEPLACEMENT_AVEC_CAISSE;
        }
    }

    plateau[*lig][*col] = quitterCible ? CHAR_JOUEUR_CIBLE : CHAR_JOUEUR;
    return DEPLACEMENT_IMPOSSIBLE;
}

bool positions_egales(t_Position p1, t_Position p2) {
    return (p1.ligne == p2.ligne && p1.colonne == p2.colonne);
}

int optimiser_deplacements(t_tabDeplacement depOriginaux, int nbOriginal, 
                           t_tabDeplacement depOptimises, char fichierSok[]) {
    t_Plateau plateau;
    t_Position historique[MAX_DEPLACEMENTS];
    int nbHistorique = 0;
    int lig, col;
    int nbOptimise = 0;
    bool aSupprimer[MAX_DEPLACEMENTS];
    t_Position positionActuelle;
    
    /* Initialiser le tableau de suppression */
    for (int i = 0; i < nbOriginal; i++) {
        aSupprimer[i] = false;
    }
    
    /* Charger le plateau initial */
    charger_partie(plateau, fichierSok);
    trouver_joueur(plateau, &lig, &col);
    
    /* Enregistrer la position initiale */
    historique[nbHistorique].ligne = lig;
    historique[nbHistorique].colonne = col;
    nbHistorique++;
    
    /* Passe unique : marquer les déplacements non joués et détecter les séquences inutiles */
    for (int i = 0; i < nbOriginal; i++) {
        t_TypeDeplacement type = deplacer(plateau, &lig, &col, depOriginaux[i]);
        
        if (type == DEPLACEMENT_IMPOSSIBLE) {
            /* Déplacement non joué -> à supprimer */
            aSupprimer[i] = true;
        } else if (type == DEPLACEMENT_AVEC_CAISSE) {
            /* Caisse déplacée -> réinitialiser l'historique */
            nbHistorique = 0;
            historique[nbHistorique].ligne = lig;
            historique[nbHistorique].colonne = col;
            nbHistorique++;
        } else if (type == DEPLACEMENT_SIMPLE) {
            /* Position actuelle de Sokoban */
            positionActuelle.ligne = lig;
            positionActuelle.colonne = col;
            
            /* Vérifier si on revient à une position déjà visitée depuis le dernier déplacement de caisse */
            for (int j = 0; j < nbHistorique; j++) {
                if (positions_egales(historique[j], positionActuelle)) {
                    /* Séquence inutile détectée ! */
                    /* On doit supprimer tous les déplacements depuis cette position jusqu'à maintenant */
                    /* Trouver l'index du déplacement qui nous a amené à historique[j] */
                    int compteur = 0;
                    int indexRetour = -1;
                    
                    /* Recompter pour trouver quel déplacement correspond à cette position */
                    for (int k = 0; k <= i; k++) {
                        if (!aSupprimer[k]) {
                            if (compteur == j) {
                                indexRetour = k;
                            }
                            compteur++;
                        }
                    }
                    
                    /* Marquer tous les déplacements de la boucle (sauf celui qui nous a amené à la position) */
                    if (indexRetour >= 0) {
                        for (int k = indexRetour + 1; k <= i; k++) {
                            if (!aSupprimer[k]) {
                                aSupprimer[k] = true;
                            }
                        }
                    }
                    
                    /* Réinitialiser l'historique à partir de cette position */
                    nbHistorique = j + 1;
                    break;
                }
            }
            
            /* Si pas de retour détecté, ajouter la position à l'historique */
            if (nbHistorique > 0 && !positions_egales(historique[nbHistorique - 1], positionActuelle)) {
                if (nbHistorique < MAX_DEPLACEMENTS) {
                    historique[nbHistorique] = positionActuelle;
                    nbHistorique++;
                }
            }
        }
    }
    
    /* Construction du tableau optimisé */
    for (int i = 0; i < nbOriginal; i++) {
        if (!aSupprimer[i]) {
            depOptimises[nbOptimise] = depOriginaux[i];
            nbOptimise++;
        }
    }
    
    return nbOptimise;
}

void sauvegarder_deplacements(t_tabDeplacement deplacements, int nb, char fichier[]) {
    FILE *f;
    
    f = fopen(fichier, "w");
    if (f == NULL) {
        printf("ERREUR : Impossible de creer le fichier %s\n", fichier);
        return;
    }
    
    for (int i = 0; i < nb; i++) {
        fwrite(&deplacements[i], sizeof(char), 1, f);
    }
    
    fclose(f);
}