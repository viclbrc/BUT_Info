#!/usr/bin/php
<?php

$dept_to_region = [];

if (($regions = fopen($argv[2], 'r')) !== false) {
    while (($line = fgets($regions)) !== false) {
        $line = trim($line); // trim : supprime les espaces (par défaut) ou d'autres caractères en début et fin de string
        if (empty($line)) continue;
        
        $parts = explode('=', $line);
        if (count($parts) < 2) continue;
        
        $region_name = trim($parts[0]);
        $depts_str = trim($parts[1]);
        $depts = array_map('trim', explode(',', $depts_str)); // Applique la fonction trim au tableau entier
        
        foreach ($depts as $dept) {
            $dept_to_region[$dept] = $region_name;
        }
    }
    fclose($regions);
}

$totaux = [];

if (($y = fopen($argv[1], 'r')) !== false) {
    while (($line = fgets($y)) !== false) {
        $line = trim($line);
        if (empty($line)) continue;

        $parts = explode(';', $line);
        if (count($parts) < 3) continue;

        while (!empty($parts) && trim($parts[0]) === '') {
            array_shift($parts);
        }
        
        if (count($parts) < 3) continue;

        $site_name = trim($parts[0]);
        $dept = trim($parts[1]);
        $visiteurs = (int)$parts[2];
        
        if ($site_name === 'nom' || $site_name === '' || strpos($site_name, 'Sites') !== false) continue; // strpos cherche la position de la 1ère occurrence dans un string

        if ($dept === '' || $visiteurs == 0) continue;

        if (!isset($dept_to_region[$dept])) continue; // isset détermine si une variable est déclarée et est non nulle

        $region = $dept_to_region[$dept];
        $totaux[$region] = ($totaux[$region] ?? 0) + $visiteurs; // ?? : si $totaux[$region] existe, la prendre, sinon prendre 0
    }
    fclose($y);
}

arsort($totaux); // Trie un tableau en ordre décroissant et conserve les clés

echo <<<HTML
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Visiteurs par région</title>
<style>
@page {
    size: A4 portrait;
    margin: 2mm;
}
body {
    font-family: Arial, sans-serif;
    font-size: 9px;
    margin: 0;
    padding: 0;
    line-height: 1.1;
}
table {
    border-collapse: collapse;
    width: 100%;
    table-layout: fixed;
}
col:nth-child(1) { width: 75%; }
col:nth-child(2) { width: 25%; }
th, td {
    border: 1px solid #000;
    padding: 0.5px;
    text-align: left;
    overflow-wrap: break-word;
}
th {
    background-color: #e8f0f7;
    font-weight: bold;
}
tr {
    page-break-inside: avoid;
}
</style>
</head>
<body>
<table>
<colgroup>
<col/><col/>
</colgroup>
<tr><th>Région</th><th>Visiteurs annuels</th></tr>
HTML;

foreach ($totaux as $region => $total) {
    echo '<tr><td>' . htmlspecialchars($region) . '</td><td>' .
         number_format($total, 0, ',', ' ') . '</td></tr>'; // Formate un nombre
}

echo '</table></body></html>';
?>