#!/usr/bin/php
<?php

$depts = [];
$code_map = [1=>'1', 2=>'2', 3=>'3', 4=>'4', 5=>'5', 6=>'6', 7=>'7', 8=>'8', 9=>'9', 10=>'10',
    11=>'11', 12=>'12', 13=>'13', 14=>'14', 15=>'15', 16=>'16', 17=>'17', 18=>'18', 19=>'19', 20=>'2A', 21=>'2B',
    22=>'21', 23=>'22', 24=>'23', 25=>'24', 26=>'25', 27=>'26', 28=>'27', 29=>'28', 30=>'29', 31=>'30', 32=>'31', 33=>'32',
    34=>'33', 35=>'34', 36=>'35', 37=>'36', 38=>'37', 39=>'38', 40=>'39', 41=>'40', 42=>'41', 43=>'42', 44=>'43', 45=>'44',
    46=>'45', 47=>'46', 48=>'47', 49=>'48', 50=>'49', 51=>'50', 52=>'51', 53=>'52', 54=>'53', 55=>'54', 56=>'55', 57=>'56',
    58=>'57', 59=>'58', 60=>'59', 61=>'60', 62=>'61', 63=>'62', 64=>'63', 65=>'64', 66=>'65', 67=>'66', 68=>'67', 69=>'68',
    70=>'69', 71=>'70', 72=>'71', 73=>'72', 74=>'73', 75=>'74', 76=>'75', 77=>'77', 78=>'78', 79=>'79', 80=>'80', 81=>'81',
    82=>'82', 83=>'83', 84=>'84', 85=>'85', 86=>'86', 87=>'87', 88=>'88', 89=>'89', 90=>'90', 91=>'91', 92=>'92', 93=>'93',
    94=>'94', 95=>'95', 96=>'95'
];

if (($x = fopen($argv[2], 'r')) !== false) {
    $line_num = 1;
    while (($name = fgets($x)) !== false) {
        $name = trim($name);
        if (empty($name)) continue;
        
        if (isset($code_map[$line_num])) {
            $code = $code_map[$line_num];
            $depts[$code] = $name;
        }
        $line_num++;
    }
    fclose($x);
}

$sites = [];

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
        
        if ($site_name === 'nom' || $site_name === '' || strpos($site_name, 'Sites') !== false) continue;

        if ($dept === '' || $visiteurs == 0) continue;

        $sites[] = [
            'site' => $site_name,
            'dept' => $dept,
            'dept_name' => $depts[$dept] ?? 'Inconnu',
            'visiteurs' => $visiteurs
        ];
    }
    fclose($y);
}

usort($sites, function($a, $b) { // usort : trie un tableau en fonction de ses valeurs
    $cmp_visiteurs = $b['visiteurs'] <=> $a['visiteurs'];
    if ($cmp_visiteurs !== 0) return $cmp_visiteurs;
    
    $dept_a = $a['dept'];
    $dept_b = $b['dept'];
    
    $code_a = is_numeric($dept_a) ? (int)$dept_a : (($dept_a === '2A') ? 19.1 : 19.2); // Si le département est numérique (string qui peut être interprété comme int ou float), le convertir en int, sinon 2A = 19.1 et 2B = 19.2
    $code_b = is_numeric($dept_b) ? (int)$dept_b : (($dept_b === '2A') ? 19.1 : 19.2);
    
    return $code_a <=> $code_b;
});

echo <<<HTML
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sites les plus visités</title>
<style>
@page {
    size: A4 portrait;
    margin: 2mm;
}
body {
    font-family: Arial, sans-serif;
    font-size: 8px;
    margin: 0;
    padding: 0;
    line-height: 1.1;
}
table {
    border-collapse: collapse;
    width: 100%;
    table-layout: fixed;
}
col:nth-child(1) { width: 5%; }
col:nth-child(2) { width: 20%; }
col:nth-child(3) { width: 60%; }
col:nth-child(4) { width: 15%; }
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
h6 {
    text-align: center;
}
</style>
</head>
    <body>
        <h6>Sites et nombre de visiteurs par département</h6>
    <table>
        <colgroup>
            <col/><col/>
            <col/><col/>
    </colgroup>
    <tr><th>Code</th>  
        <th>Département</th>
        <th>Site</th>
        <th>Visiteurs annuels</th>
    </tr>
HTML;

foreach ($sites as $site) {
    echo '<tr><td>' . htmlspecialchars($site['dept']) . '</td><td>' .
         htmlspecialchars($site['dept_name']) . '</td><td>' .
         htmlspecialchars($site['site']) . '</td><td>' .
         number_format($site['visiteurs'], 0, ',', ' ') . '</td></tr>';
}

echo '</table></body></html>';
