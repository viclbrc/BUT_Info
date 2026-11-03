#include <stdio.h>
#include <stdlib.h>

typedef struct{
    char nom[100];
    int age;
} tpersonne;

typedef struct Elem{
    tpersonne val;
    struct Elem * svt;
} telement;

typedef telement* tliste;

telement * rechercherIeme(tliste l, int k);

int main(){
    tliste liste;
    initialisation(&liste);
    printf("Liste initialisée.\n");

    while (1) {
        tpersonne pers;
        printf("Entrez le nom de la personne :\n");
        scanf("%s", pers.nom);
        if (strcmp(pers.nom, "-1") == 0) {
            break;
        }
        printf("Entrez l'âge de la personne :\n");
        scanf("%d", &pers.age);
        insereEnTete(&liste, pers);
        printf("Personne ajoutée à la liste.\n");
    }

    printf("=== Affichage de la liste ===\n");
    afficheListe(liste);

    printf("=== Recherche d'une personne par nom ===\n");
    char nomRecherche[100];
    printf("Entrez le nom de la personne à rechercher :\n");
    scanf("%s", nomRecherche);
    rechercherNom(liste, nomRecherche);

    printf("=== Suppression de la tête ===\n");
    supprimerTete(&liste);
    printf("=== Tête supprimée. Nouvelle liste : ===\n");
    afficheListe(liste);

    printf("=== Comptage du nombre de personnes avec le même nom ===\n");
    compterNom(liste, nomRecherche);

    printf("=== Recherche du ième élément ===\n");
    rechercherIeme(liste, 2);

    return 0;
}


void initialisation(tliste *l){
    *l = NULL;
}

void insereEnTete(tliste *l, tpersonne personne){
    telement *p;
    p = (telement*)malloc(sizeof(telement));
    p->val = personne;
    p->svt = NULL;

    if (*l == NULL){
        *l = p;
    } else {
        p->svt = *l;
        *l = p;
    }
}

void affichePersonne(tpersonne personne){
    printf("Nom: %s / Age: %d\n", personne.nom, personne.age);
}

void afficheListe(tliste l){
    telement *p = l;
    while (p != NULL){
        affichePersonne(p->val);
        p = p->svt;
    }
}

int rechercherNom(tliste l, char *nom){
    telement *p = l;
    while (p != NULL){
        if (strcmp(p->val.nom, nom) == 0){
            printf("Personne trouvée : ");
            affichePersonne(p->val);
            return 0;
        }
        p = p->svt;
    }
    printf("Personne non trouvée.\n");
    return -1;
}

void supprimerTete(tliste *l){
    if (*l != NULL){
        (*l) = (*l)->svt;
    }
}

void compterNom(tliste l, char *nom){
    telement *p = l;
    int count = 0;
    if (rechercherNom(l, nom) != -1){
        while (p != NULL){
            if (strcmp(p->val.nom, nom) == 0){
                count++;
            }
            p = p->svt;
        }
        printf("Nombre de personnes avec le nom '%s' : %d\n", nom, count);
    } else {
        printf("Aucune personne avec le nom '%s' trouvée.\n", nom);
    }
}

telement * rechercherIeme(tliste l, int k){
    int I;
    telement *courant;
    
    I = 1;
    courant = l;
    while ((I != k) && (courant != NULL)){
        courant = courant->svt;
        I++;
    }
    return courant;
}