<?php
    require_once('../ex2/regions.php');
    print_r($_GET);

    $page = isset($_GET['page']) ? (int)$_GET['page'] : 1;
    if ($page < 1) {
        $page = 1;
    }

    $departements_par_page = 5;
    $departements_total = count($regions);
    $nombre_de_pages = ceil($departements_total / $departements_par_page);

    $departement_debut = ($page - 1) * $departements_par_page;
    $departement_fin = min($departement_debut + $departements_par_page, $departements_total);

    $regions_page = array_slice($regions, $departement_debut, $departements_par_page);
?>

<ul>
    <?php foreach ($regions_page as $key => $value){
        echo '<li>' . $key . '</li>';
    }
    ?>
    <a href="?page=<?php echo $page - 1; ?>">Précédent</a>
    <a href="?page=<?php echo $page + 1; ?>">Suivant</a>
</ul>