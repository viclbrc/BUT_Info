#!/bin/bash

INPUT_FILE="$1"
OUTPUT_DIR="${2:-.}" # Récupère le deuxième argument sinon utilise le répertoire courant

mkdir -p "$OUTPUT_DIR" # Crée le répertoire de sortie s'il n'existe pas

if [ ! -f "$INPUT_FILE" ];  # Vérifie si le fichier d'entrée existe
then
    echo "Fichier '$INPUT_FILE' introuvable."
    exit 1
fi

nb_sect=0 # Nombre de sections
nb_subsect=0 # Nombre de sous-sections
output_file=""
content="" # Prend le texte avant de le coller dans le fichier de sortie

while read -r line; # -r : interprète pas les \ | chaque ligne est stockée dans line. 
do
    if echo "$line" | grep -q "^TITLE="; # Si la ligne commence par TITLE=
    then
        title=$(echo "$line" | sed 's/^TITLE=//') # Enlève TITLE= et ne garde que le titre dans title
        echo "$title" > "$OUTPUT_DIR/title.txt" # Mets le titre dans title.txt
        
    elif echo "$line" | grep -q "^SECT="; # Si la ligne commence par SECT=
    then
        if [ -n "$output_file" ] && [ -n "$content" ]; # -n : si output_file et content ne sont pas vides
        then
            echo -e "$content" > "$output_file" # -e permet les \n | écrit le contenu dans le fichier.
        fi
        
        sect=$(echo "$line" | sed 's/^SECT=//') # Récupère le nom de la section sans SECT=
        nb_sect=$((nb_sect + 1)) # Augmente le nombre de sections
        nb_subsect=0 # Réinitialise le nombre de sous-sections
        output_file="$OUTPUT_DIR/sect_${nb_sect}.txt" # Nom du fichier de sortie
        content="$sect" # Met le contenu avec le nom de la section
        
    elif echo "$line" | grep -q "^SUB_SECT="; # Si la ligne commence par SUB_SECT=
    then
        if [ -n "$output_file" ] && [ -n "$content" ]; # -n : si output_file et content ne sont pas vides
        then
            echo -e "$content" > "$output_file" # -e permet les \n | écrit le contenu dans le fichier.
        fi
        
        subsect=$(echo "$line" | sed 's/^SUB_SECT=//') # Récupère le nom de la sous-section sans SUB_SECT=
        nb_subsect=$((nb_subsect + 1)) # Augmente le nombre de sous-sections
        output_file="$OUTPUT_DIR/subsect_${nb_sect}_${nb_subsect}.txt" # Nom du fichier de sortie
        content="$subsect" # Met le contenu avec le nom de la sous-section
        
    elif echo "$line" | grep -q "^TEXT="; # Si la ligne commence par TEXT=
    then
        text=$(echo "$line" | sed 's/^TEXT=//') # Récupère le texte sans TEXT=
        if [ -n "$content" ]; 
        then
            content="$content"$'\n'"$text" # Si il y a déjà du texte, saute une ligne et ajoute le nouveau texte
        else
            content="$text" # Sinon, met le contenu avec le texte
        fi
    fi

    if [ -n "$output_file" ] && [ -n "$content" ]; 
    then
        echo -e "$content" > "$output_file"
    fi

done < "$INPUT_FILE"