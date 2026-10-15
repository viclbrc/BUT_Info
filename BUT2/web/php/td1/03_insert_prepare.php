<?php
include('connect_params_perso.php');
try {
    $dbh = new PDO("$driver:host=$server;dbname=$dbname", 
            $user, $pass);
    $dbh->setAttribute(PDO::ATTR_ERRMODE, 
                        PDO::ERRMODE_EXCEPTION);
    $sth = $dbh->prepare(
        "INSERT INTO forum1._user(nickname, email, pass) 
        VALUES(?,?,?)"
    );
    $nickname   = 'Benoît';
    $email      = "benoit@agile.org";
    $pass       = "tioneb";
    $sth->execute(array($nickname,$email,$pass));
    $dbh = null;
} catch (PDOException $e) {
    print "Erreur !: " . $e->getMessage() . "<br/>";
    die();
}
?>
