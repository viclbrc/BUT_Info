#!/bin/bash

images="jules-verne.webp louvre.webp mont-blanc.webp place-stanislas.webp"
max_size=180000 # 180 Ko max
max_width=900 # 900 pixels max
max_height=620 # 620 pixels max
output="images_optimisees"

mkdir -p "$output"

for image in $images; # Pour chaque image dans images
do
    if [ ! -f "$image" ]; # Si le fichier n'existe pas
    then
        echo "$image non trouvé"
        continue
    fi
    
    echo "Traitement de $image..."

    TEMP_DIR=$(mktemp -d) # Crée un dossier temporaire
    cp "$image" "$TEMP_DIR/$image" # Copie l'image dans le dossier temporaire
    
    docker run --rm -v "$TEMP_DIR:/images" bigpapoo/imagick:latest sh -c "
        cd /images
        convert $image -resize '350x250<' -resize '900x620>' -quality 85 $image
        SIZE=\$(stat -c%s $image)
        QUALITY=85
        while [ \$SIZE -gt 180000 ] && [ \$QUALITY -gt 40 ]; do
            QUALITY=\$((QUALITY - 5))
            convert $image -resize '350x250<' -resize '900x620>' -quality \$QUALITY $image
            SIZE=\$(stat -c%s $image)
        done
    "
    # -v : lie le dossier de l'hôte avec le conteneur
    # -resize '350x250<' : agrandit si plus petit que 350x250
    # -resize '900x620>' : réduit si plus grand que 900x620
    # qualité 85 au début, réduite par 5 si plus de 180 Ko
    cp "$TEMP_DIR/$image" "$output/$image"
    rm -rf "$TEMP_DIR"
done

echo "Redimensionnement terminé"