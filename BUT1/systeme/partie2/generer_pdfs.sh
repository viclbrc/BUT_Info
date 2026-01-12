#!/bin/bash

echo "Génération des PDFs des sites touristiques"

if [ ! -f "DEPTS" ] # -f : si le fichier DEPTS n'existe pas
then
    >&2 echo "Erreur: Fichier DEPTS manquant"
    exit 1
fi

if [ ! -f "REGIONS" ] # Si le fichier REGIONS n'existe pas
then
    >&2 echo "Erreur: Fichier REGIONS manquant"
    exit 1
fi

if [ ! -f "sites_touristiques_france_v2.csv" ] # Si le fichier sites_touristiques_france_v2.csv n'existe pas
then
    if [ -f "sites_touristiques_france_v2.xlsx" ] # Si le fichier Excel sites_touristiques_france_v2.xlsx existe
    then
        echo "Conversion du fichier Excel en CSV"
        docker run --rm \
            -v "$(pwd):/workspace" \
            bigpapoo/sae103-excel2csv \
            bash -c "cd /workspace && ssconvert 'sites_touristiques_france_v2.xlsx' 'sites_touristiques_france_v2.csv'"
            # -v : lie le dossier de l'hôte avec un dossier dans le conteneur / $(pwd) : chemin du dossier courant
        if [ ! -f "sites_touristiques_france_v2.csv" ]
        then
            >&2 echo "Erreur: La conversion Excel en CSV a échoué"
            exit 1
        fi

        echo "Fichier CSV créé"
    else
        >&2 echo "Erreur: Fichiers sites manquants (sites_touristiques_france_v2.csv / sites_touristiques_france_v2.xlsx)"
        exit 1
    fi
fi

echo "Tous les fichiers sont là."

echo "Génération de sites-dept.pdf"
./generer_html_dept.php sites_touristiques_france_v2.csv DEPTS > /tmp/sites-dept.html

if [ $? -ne 0 ] # $? : code de retour de la dernière commande -> si la dernière commande a échoué (-ne : !=)
then
    >&2 echo "Erreur pendant la génération du HTML pour sites-dept.pdf"
    exit 1
fi

docker run --rm \
    -v /tmp/sites-dept.html:/workspace/input.html:ro \
    -v "$(pwd)":/workspace/output \
    bigpapoo/sae103-html2pdf \
    weasyprint /workspace/input.html /workspace/output/sites-dept.pdf
# -v : lie le dossier de l'hôte avec un dossier dans le conteneur | ro : lecture seule

if [ -f "sites-dept.pdf" ]
then
    echo "sites-dept.pdf créé"
else
    >&2 echo "Erreur pendant la création de sites-dept.pdf"
    exit 1
fi

echo "Génération de sites-visites.pdf"
./generer_html_visites.php sites_touristiques_france_v2.csv DEPTS > /tmp/sites-visites.html

if [ $? -ne 0 ]
then
    >&2 echo "Erreur pendant la génération du HTML pour sites-visites.pdf"
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
    >&2 echo "Erreur pendant la création de sites-visites.pdf"
    exit 1
fi

echo "Génération de sites-regions.pdf"
./generer_html_regions.php sites_touristiques_france_v2.csv REGIONS > /tmp/sites-regions.html

if [ $? -ne 0 ]
then
    >&2 echo "Erreur pendant la génération du HTML pour sites-regions"
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
    >&2 echo "Erreur pendant la création de sites-regions.pdf"
    exit 1
fi

echo "Génération terminée"