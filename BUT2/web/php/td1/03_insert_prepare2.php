<?php
include('connect_params_perso.php');
try {
    $dbh = new PDO("$driver:host=$server;dbname=$dbname", 
            $user, $pass);
    $dbh->setAttribute(PDO::ATTR_ERRMODE, 
                        PDO::ERRMODE_EXCEPTION);                       
    $sth = $dbh->prepare(
        "INSERT INTO forum1._user(nickname, email, pass) 
        VALUES(:nickname, :email, :pass)"
    );
    $sth->bindParam(':nickname', $nickname);
    $sth->bindParam(':email', $email);
    $sth->bindParam(':pass', $pass);

    // insertion d'une ligne
    $nickname   = 'Laurent';
    $email      = "laurent@bigdata.org";
    $pass       = "tnerual";
    $sth->execute();
    // insertion d'une autre ligne avec des valeurs différentes
    $nickname   = 'Gilbert';
    $email      = "gilbert@jujitsu.org";
    $pass       = "treblig";
    $sth->execute();
    $dbh = null;
} catch (PDOException $e) {
    print "Erreur !: " . $e->getMessage() . "<br/>";
    die();
}
?>
