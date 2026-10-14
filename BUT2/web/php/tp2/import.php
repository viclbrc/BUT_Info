<?php
    $data = file("INSEE.csv");
    for ($i = 0; $i < count($data); $i++) {
        $data[$i] = explode(";", $data[$i]);
    }
    
    $valid_data = [];
    foreach ($data as $key => $value) {
        $fiche_valide = true;
        $erreurs_fiche = [];

        // VERIF NOM

        $nom = trim($value[0]);
        $nom = str_replace(" ", "-", $nom);

        if (strlen($nom) > 40) {
            $fiche_valide = false;
            $erreurs_fiche = "Nom trop long";
        }
        
        if (!preg_match("/^[A-Za-z]+(['-]{0,2}[A-Za-z]+)*$/", $nom)) {
            $fiche_valide = false;
            $erreurs_fiche = "Nom invalide";
        } else {
            $value[0] = strtoupper($nom);
        }

        // VERIF PRENOM

        $prenom = trim($value[1]);
        if (strlen($prenom) > 40) {
            $fiche_valide = false;
            $erreurs_fiche = "Prénom trop long";
        }
        if (!preg_match("/^[A-Za-z]+(['-]{0,2}[A-Za-z]+)*$/", $prenom)) {
            $fiche_valide = false;
            $erreurs_fiche = "Prénom invalide";
        } else {
            $value[1] = ucwords(strtolower($prenom), "-");
        }

        // VERIF EMAIL
        $email = trim($value[2]);
        if (strlen($email) > 255) {
            $fiche_valide = false;
            $erreurs_fiche = "Email trop long";
        }
        if (!preg_match("/^["))

        if ($fiche_valide) {
            $valid_data[] = $value;
        } else {
            echo "Erreur dans la fiche " . ($key + 1) . ": " . $erreurs_fiche . "<br>";
        }
    }

    echo "<pre>";
    print_r($valid_data);
    echo "</pre>";
    // file_put_contents("data.csv", $valid_data);
?>