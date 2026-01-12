#!/bin/bash

INPUT_FILE="$1"
OUTPUT_DIR="${2:-.}" # Récupère le deuxième argument sinon utilise le répertoire courant

if [ ! -f "$INPUT_FILE" ]; # Vérifie si le fichier d'entrée existe
then 
    echo "Fichier '$INPUT_FILE' introuvable."
    exit 1
fi

mkdir -p "$OUTPUT_DIR"

OUTPUT_FILE="$OUTPUT_DIR/sites_touristiques.txt" # Nom du fichier de sortie

sed 's/"//g' "$INPUT_FILE" | awk -F',' '
NR==1 {
    print "SITES TOURISTIQUES DE FRANCE"
    print ""
    printf "%-40s %-15s %-20s\n", $1, $2, $3
    printf "%-40s %-15s %-20s\n", "----------------------------------------", "---------------", "--------------------"
}
NR>1 {
    printf "%-40s %-15s %-20s\n", $1, $2, $3
}' > "$OUTPUT_FILE"
# sed 's/"//g' supprime les guillemets, awk définit ',' comme séparateur
# NR : numéro de ligne dans awk.
# %-40s %-15s %-20s : 1ère colonne : 40 caractères, 2ème : 15 caractères, 3ème : 20 caractères. Le tiret : alignement à gauche.
# NR>1 : toutes les lignes sauf l'en-tête.
