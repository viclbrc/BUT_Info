#!/bin/bash

echo "Redimensionnement des images"

if [ -f "./redimensionner_images.sh" ]; 
then
    bash ./redimensionner_images.sh
else
    echo "Script redimensionner_images.sh non trouvé"
fi

if [ -f "./separer_presentation.sh" ] && [ -f "./presentation_musee_louvre" ]; 
then
    bash ./separer_presentation.sh presentation_musee_louvre presentation_output
    echo "Fichiers générés dans: presentation_output/"
else
    echo "Script ou fichier d'entrée non trouvé"
fi

if [ -f "./convertir_csv_html.sh" ] && [ -f "./sites_touristiques_france_v2.csv" ]; 
then
    bash ./convertir_csv_html.sh sites_touristiques_france_v2.csv csv_output
    echo "Fichiers générés dans: csv_output/"
else
    echo "Script ou fichier d'entrée non trouvé"
fi

echo "Traitement des données terminé"
