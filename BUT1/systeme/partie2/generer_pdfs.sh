#!/bin/bash

# SAÉ 1.03 - Phase B
# Script principal pour générer les 3 PDFs

echo "Génération des PDFs des sites touristiques"

# Vérification des fichiers nécessaires
if [ ! -f "DEPTS" ] # -f : si le fichier DEPTS existe
then
    >&2 echo "Erreur: Fichier DEPTS manquant"
    exit 1
fi

if [ ! -f "REGIONS" ]
then
    >&2 echo "Erreur: Fichier REGIONS manquant"
    exit 1
fi

# On a pas réussi en xlsx donc vérifier si le fichier CSV existe, sinon demander la conversion
if [ ! -f "sites.csv" ]
then
    if [ -f "sites_touristiques_france v2.xlsx" ]
    then
        >&2 echo "Erreur: Le fichier sites.csv n'existe pas"
        >&2 echo "Veuillez convertir le fichier Excel en CSV:"
        exit 1
    else
        >&2 echo "Erreur: Fichiers sites manquants (sites.csv/.xlsx)"
        exit 1
    fi
fi

echo "Tous les fichiers sont présents."

echo "Étape 1 : Génération de sites-dept.pdf"
./generer_html_dept.php sites.csv DEPTS > /tmp/sites-dept.html

if [ $? -ne 0 ] # $? : code de retour de la dernière commande -> si la dernière commande a échoué (-ne : !=)
then
    >&2 echo "Erreur lors de la génération du HTML pour sites-dept"
    exit 1
fi

docker run --rm \
    -v /tmp/sites-dept.html:/workspace/input.html:ro \
    -v "$(pwd)":/workspace/output \
    bigpapoo/sae103-html2pdf \
    weasyprint /workspace/input.html /workspace/output/sites-dept.pdf

if [ -f "sites-dept.pdf" ]
then
    echo "sites-dept.pdf créé"
else
    >&2 echo "Erreur lors de la création de sites-dept.pdf"
    exit 1
fi

echo "Étape 2 : Génération de sites-visites.pdf"
./generer_html_visites.php sites.csv DEPTS > /tmp/sites-visites.html

if [ $? -ne 0 ]
then
    >&2 echo "Erreur lors de la génération du HTML pour sites-visites"
    exit 1
fi

docker run --rm \
    -v /tmp/sites-visites.html:/workspace/input.html:ro \
    -v "$(pwd)":/workspace/output \
    bigpapoo/sae103-html2pdf \
    weasyprint /workspace/input.html /workspace/output/sites-visites.pdf

if [ -f "sites-visites.pdf" ]
then
    echo "sites-visites.pdf créé"
else
    >&2 echo "Erreur lors de la création de sites-visites.pdf"
    exit 1
fi

echo "Étape 3 : Génération de sites-regions.pdf"
./generer_html_regions.php sites.csv REGIONS > /tmp/sites-regions.html

if [ $? -ne 0 ]
then
    >&2 echo "Erreur lors de la génération du HTML pour sites-regions"
    exit 1
fi

docker run --rm \
    -v /tmp/sites-regions.html:/workspace/input.html:ro \
    -v "$(pwd)":/workspace/output \
    bigpapoo/sae103-html2pdf \
    weasyprint /workspace/input.html /workspace/output/sites-regions.pdf

if [ -f "sites-regions.pdf" ]
then
    echo "sites-regions.pdf créé"
else
    >&2 echo "Erreur lors de la création de sites-regions.pdf"
    exit 1
fi

echo "Génération terminée"
echo "Fichiers créés:"
[ -f "sites-dept.pdf" ] && echo "sites-dept.pdf"
[ -f "sites-visites.pdf" ] && echo "sites-visites.pdf"
[ -f "sites-regions.pdf" ] && echo "sites-regions.pdf"