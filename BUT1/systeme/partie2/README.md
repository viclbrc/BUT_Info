# SAÉ 1.03 Installation d'un poste pour le développement

## Contenu

- Fichier donné DEPTS
- Fichier donné REGIONS
- Fichier donné sites_touristiques_france_v2.xlsx
- 4 images .webp à redimmensionner
- 1 fichier presentation_musee_louvre à découper en plusieurs fichiers texte
- Fichier PARTICIPATION.md

### Scripts

- generer_html_dept.php : génère le fichier HTML pour le site des départements
- generer_html_regions.php : génère le fichier HTML pour le site des régions
- generer_html_visites.php : génère le fichier HTML pour le site des visites
- convertir_csv_html.sh : convertit le fichier CSV en un fichier texte prêt à utiliser dans un fichier HTML
- separer_presentation.sh : sépare en plusieurs fichiers le fichier presentation_musee_louvre
- redimmensionner_images.sh : redimmensionne les images

## Scripts à exécuter

**En premier :**

generer_pdfs.sh : génère les fichiers PDF (sites-dept, sites_regions et sites-visites) et convertit le fichier XLSX en fichier CSV.

**Ensuite :**

execute_tous_scripts.sh : exécute les autres scripts (redimmensionner_images.sh, separer_presentation.sh et convertir_csv_html.sh)

## Pour exécuter :

1. Être dans le bon dossier
2. Se donner les droits d'exécuter les scripts : ```chmod +x *.sh *.php```
3. Exécuter les 2 scripts **dans l'ordre** : ```./generer_pdfs.sh``` puis ```./execute_tous_scripts.sh```